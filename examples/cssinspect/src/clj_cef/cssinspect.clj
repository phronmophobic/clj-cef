(ns clj-cef.cssinspect
  (:require [com.phronemophobic.cef :as cef
             :refer [print-doc
                     cef-string]]
            [clojure.data.json :as json]
            [clojure.core.async :as async
             :refer [chan go >! <! put!]]
            [clojure.java.io :as io]
            
            [com.phronemophobic.cinterop
             :refer [dispatch-sync
                     dispatch-async]]
            
            [clj-cef.interval-map :as im]
            
            )
  (:import
   java.awt.image.BufferedImage
   java.awt.image.DataBuffer
   java.awt.image.DataBufferInt
   java.awt.image.Raster
   javax.imageio.ImageIO
   java.awt.image.ColorModel
   java.awt.Point
   [com.phronemophobic.cljcef
    CljCefLib
    SizeT]
   [java.net URLEncoder]
   [com.sun.jna
    Pointer
    Memory
    ])
  (:gen-class))

(defn load-cef-classes []
  ;; import classes for auto complete
  (eval '(com.phronemophobic.gen2/import-cef-classes)))


(let [listen (chan 10)
      init-process (async/go
                     (let [ret-chan (<! listen)
                           inited (chan)]
                       (dispatch-async
                        (fn []
                          (cef/prepare-environment!)
                          (let [app (cef/map->app
                                     {:get-browser-process-handler
                                      (fn [app]
                                        (cef/map->browser-process-handler
                                         {:on-context-initialized
                                          (fn [bph]
                                            (async/put! inited true))}))})]
                            (cef/cef-initialize app))))
                       (<! inited)
                       (>! ret-chan true)
                       (loop []
                         (>! (<! listen) true)
                         (recur))))]
  (defn init-cef-chan
    "Returns a channel that closes when cef has finished initializing. Will close immediatly if already initialized."
    []
    (let [ch (chan)]
      (async/put! listen ch)
      ch)))


(defn make-browser
  "Returns a channel that will receive a new CefBrowser."
  ([]
   (make-browser {}))
  ([{:keys [width height] :as opts}]
   (let [width (get opts :width 800)
         height (get opts :height 800)
         browser-ch (async/promise-chan)]
     (go
       (<! (init-cef-chan))
       (dispatch-async
        (fn []
          (let [browser-settings (cef/map->browser-settings)
                client (cef/map->client
                        {:get-life-span-handler
                         (fn [client]
                           (cef/map->life-span-handler
                            {:on-after-created
                             (fn [this b]
                               (cef/cef-quit-message-loop)
                               (async/put! browser-ch b))}))
                         :get-render-handler
                         (fn [client]
                           (cef/map->render-handler
                            {:get-view-rect
                             (fn [handler browser rect]
                               (set! (.width rect) width)
                               (set! (.height rect) height))
                             :on-paint
                             (fn [handler browser type n rects buffer width height])}))})
                window-info (cef/map->window-info
                             {:windowless-rendering-enabled 1})]
            (cef/cef-browser-host-create-browser window-info client nil browser-settings nil nil)
            (cef/cef-run-message-loop)))))
     browser-ch)))


(defn add-dev-tools-message-observer
  "Adds a new observer to browser that will put dev tools events and return values onto ch."
  [browser ch]
  (.addDevToolsMessageObserver (.getHost browser)
                               (cef/map->dev-tools-message-observer
                                {:on-dev-tools-message
                                 (fn [this browser msg size]
                                   (let [bs (.getByteArray msg 0 (.intValue size))
                                         s (String. bs "utf-8")
                                         js (json/read-str s)]
                                     (async/put! ch js))

                                   ;; must return 1
                                   1)})))

(defn send-dev-tools-chan
  "Returns a channel that will be read and the values will be sent as dev tools messages to browser.

  Each message will have a unique monotonic message id. To get the message id put [msg id-chan] onto the dev tool chan to receive the message id used."
  [browser]
  (let [ch (chan)
        message-ids (atom 0)]
    (go
      (loop []
        (when-let [msg (<! ch)]
          (let [[msg id-chan] (if (vector? msg)
                                   msg
                                   [msg nil])
                message-id (swap! message-ids inc)
                js (json/write-str (assoc msg :id message-id))
                mem (doto (Memory. (inc (.length js)))
                      (.setString 0 js "utf-8"))
                size (SizeT. (dec (.size mem)))]
            (when id-chan
              (>! id-chan message-id))
            (.sendDevToolsMessage (.getHost browser)
                                  mem
                                  size)
            
            ;; don't garbage collect
            (identity [mem size])
            (recur)))))
    ch))

(defn send-rpc
  "Sends a dev tools message and return a channel that will recieve the result."
  [send-chan message-mult msg]
  (go
    (let [message-ch (chan)
          
          id-chan (chan)
          _ (>! send-chan [msg id-chan])
          message-id (<! id-chan)]
      (async/tap message-mult message-ch)
      (loop []
        (let [msg (<! message-ch)]
          (if (= message-id (get msg "id"))
            (do 
              (async/untap message-mult message-ch)
              msg)
            (recur)))))))

(defn get-css-coverage
  "Return a channel with the css coverage of url."
  [browser url]
  (go
    (try
      (let [dev-tools-message-ch (chan 10)
           _ (add-dev-tools-message-observer browser dev-tools-message-ch)
            dev-tools-mult (async/mult dev-tools-message-ch)

            load-ch (async/promise-chan)

            send-ch (send-dev-tools-chan browser)

            css-ch (chan)
            get-css (chan)]

        (let [ch (chan)]
          (dispatch-async
           (fn []
             (cef/merge->client (-> browser
                                    (.getHost)
                                    (.getClient))
                                {:get-load-handler
                                 (fn [this]
                                   (cef/map->load-handler 
                                    {:on-load-end
                                     (fn [this browser frame i]
                                       (async/put! load-ch true))}))})
             (async/close! ch)))
          (<! ch))

        (go
          (let [message-ch (chan)]
            (async/tap dev-tools-mult message-ch)
            (loop [sheet-params {}]
              (async/alt!
                message-ch ([msg]
                            
                            (if (= (get msg "method") "CSS.styleSheetAdded")
                              (let [params (get-in msg ["params" "header"])]
                                (recur (assoc sheet-params (get params "styleSheetId") params)))
                              (recur sheet-params)))
                get-css ([ret-ch]
                         (>! ret-ch sheet-params))))
            (async/untap dev-tools-mult message-ch)))

        (loop [{:keys [sheet-params]} {:sheet-params {}}]
          (>! send-ch {"method" "DOM.enable"})
          (>! send-ch {"method" "CSS.enable"})
          (>! send-ch {"method" "CSS.startRuleUsageTracking"})
          
          (println "loading url:" url)
          (.loadUrl (.getMainFrame browser) url)
          

          (dispatch-async
           cef/cef-run-message-loop)

          (println "waiting for page to load...")
          (<! load-ch)
          (println "loading complete.")
          (<! (async/timeout 500))
          
          (let [response (<! (send-rpc send-ch dev-tools-mult
                                       {"method" "CSS.takeCoverageDelta"}))
                
                coverage (get-in response ["result" "coverage"])
                _ (println "calculating coverage...")
                coverage-stats (reduce
                                (fn [usages {:strs [startOffset endOffset used styleSheetId]
                                             :as rule-usage}]
                                  (assert used (str "Unexpected used value: " used))
                                  (update usages styleSheetId
                                          (fn [im]
                                            (let [im (or im im/empty-interval-map)]
                                              (im/iassoc im startOffset endOffset true)))))
                                {}
                                coverage)]

            

            (let [ret-ch (chan)
                  _ (>! get-css ret-ch)
                  sheet-params (<! ret-ch)
                  sheet-params
                  (loop [sheet-params sheet-params
                         sheet-ids (seq (keys sheet-params))]
                    (if sheet-ids
                      (let [sheet-id (first sheet-ids)
                            response (<! (send-rpc send-ch dev-tools-mult
                                                   {"method" "CSS.getStyleSheetText"
                                                    "params" {"styleSheetId" sheet-id}}))
                            
                            text (get-in response ["result" "text"] )]
                        (recur
                         (assoc-in sheet-params
                                   [sheet-id "text"] text)
                         (next sheet-ids) ))
                      sheet-params))

                  sheet-params
                  (reduce
                   (fn [sheet-params [sheet-id coverage]]
                     (assoc-in sheet-params [sheet-id "coverage"] coverage))
                   sheet-params
                   coverage-stats)]
              
              (cef/post-task-to-main
               cef/cef-quit-message-loop)
              sheet-params))))
      (catch Exception e
        (println e)))))

(defn print-css-coverage-unused
  ([url]
   (print-css-coverage-unused url {}))
  ([url browser-opts]
   (let [browser (async/<!! (make-browser browser-opts))]
     (doseq [[sheet-id stats] (async/<!! (get-css-coverage browser url))]
       (println "-----------------------------")
       (println "source:" (get stats "sourceURL"))
       (let [text (get stats "text")]
         (doseq [[[from to] vals] (get stats "coverage")
                 :let [covered? (get vals true)]
                 :when (not covered?)
                 :let [start (or from 0)
                       end (or to (count text))
                       s (subs text start end)]]
           (println s)))))))

(defn print-css-coverage-stats
  ([url]
   (print-css-coverage-stats url {}))
  ([url browser-opts]
   (let [browser (async/<!! (make-browser browser-opts))]
     (doseq [[sheet-id stats] (async/<!! (get-css-coverage browser url))]
       
       (let [text (get stats "text")
             [_ covered]
             (reduce
              (fn [[total covered] [[from to] vals]]
                
                (let [covered? (get vals true)
                      start (or from 0)
                      end (or to (count text))
                      span (- end start)
                      new-total (+ total span)
                      new-covered (+ covered
                                     (if covered?
                                       span
                                       0))]
                  [new-total new-covered]))
              [0 0]
              (get stats "coverage"))
             total (count text)]
         
         (println "-----------------------------")
         (let [pct (* 100.0
                      (if (zero? total)
                        0.0
                        (/ covered total)))]
           (println "source:" (get stats "sourceURL"))
           (println "bytes covered:" covered)
           (println "bytes unused:" (- total covered))
           (println "bytes total:" total)
           (println "percent covered:" (format "%.2f%%" pct) )
           (println "percent unused:" (format "%.2f%%" (- 100.0 pct)))))))))



(comment
  (async/<!!
   (print-style-sheet-coverage
    "http://clojure.org")))

(comment
  (go
    (let [browser (<! make-browser)
          send-chan (send-dev-tools-chan browser)
          observer (<! (make-message-observer
                        {:on-dev-tools-message
                         (fn [this browser msg size]
                           (let [bs (.getByteArray msg 0 (.intValue size))
                                 s (String. bs "utf-8")
                                 js (json/read-str s)
                                 id (get js "id")]
                             
                             ;;(clojure.pprint/pprint js)
                             (when (= (get js "method") "CSS.styleSheetAdded")
                               (let [params (get-in js ["params" "header"])]
                                 (swap! sheet-params
                                        assoc
                                        (get params "styleSheetId") params)))

                             (when-let [p (get @callbacks id)]
                               (deliver p (get js "result")))
                             (when-let [coverage (get-in js ["result" "coverage"])]
                               (println "calculating coverage")
                               (reset!
                                css-coverage
                                (reduce
                                 (fn [usages {:strs [startOffset endOffset used styleSheetId]
                                              :as rule-usage}]
                                   (assert used (str "Unexpected used value: " used))
                                   (update usages styleSheetId
                                           (fn [im]
                                             (let [im (or im im/empty-interval-map)]
                                               (im/iassoc im startOffset endOffset true)))))
                                 {}
                                 coverage))))
                           
                           1)}))
          ]
      
      

      ))

  ,)

;; (def my-browser (make-browser))
(comment
  (def my-browser (make-browser))
  (defonce css-coverage (atom nil))
  (defonce css-sheets (atom {}))
  (defonce callbacks (atom {}))
  (defonce sheet-params (atom {}))


  (cef/post-task-to-main
   (fn []
     (defonce observer
       (cef/map->dev-tools-message-observer))


     (cef/merge->dev-tools-message-observer
      observer
      {:on-dev-tools-message
       (fn [this browser msg size]
         (let [bs (.getByteArray msg 0 (.intValue size))
               s (String. bs "utf-8")
               js (json/read-str s)
               id (get js "id")]
           ;;(clojure.pprint/pprint js)
           (when (= (get js "method") "CSS.styleSheetAdded")
             (let [params (get-in js ["params" "header"])]
               (swap! sheet-params
                      assoc
                      (get params "styleSheetId") params)))

           (when-let [p (get @callbacks id)]
             (deliver p (get js "result")))
           (when-let [coverage (get-in js ["result" "coverage"])]
             (println "calculating coverage")
             (reset!
              css-coverage
              (reduce
               (fn [usages {:strs [startOffset endOffset used styleSheetId]
                            :as rule-usage}]
                 (assert used (str "Unexpected used value: " used))
                 (update usages styleSheetId
                         (fn [im]
                           (let [im (or im im/empty-interval-map)]
                             (im/iassoc im startOffset endOffset true)))))
               {}
               coverage))))
         
         1)})

     (defonce add-observer (add-message-observer my-browser))))



  (def mids (atom 100))


  (defn send-message [m]
    (let [js (json/write-str (assoc m :id (swap! mids inc)))
          mem (doto (Memory. (inc (.length js)))
                (.setString 0 js "utf-8"))
          ]
      (cef/post-task-to-main
       (fn []
         (prn
          (.sendDevToolsMessage (.getHost my-browser)
                                mem
                                (SizeT. (dec (.size mem)))))))
      (identity mem)
      nil))

  
  (defn send-rpc [m]
    (let [id (swap! mids inc)
          js (json/write-str (assoc m :id id))
          mem (doto (Memory. (inc (.length js)))
                (.setString 0 js "utf-8"))
          p (promise)]
      (swap! callbacks assoc id p)
      (cef/post-task-to-main
       (fn []
         (.sendDevToolsMessage (.getHost my-browser)
                               mem
                               (SizeT. (dec (.size mem))))))
      (identity mem)
      p))

  
  (send-message {"method" "DOM.enable"})
  (send-message {"method" "CSS.enable"})
  (send-message {"method" "CSS.startRuleUsageTracking"})

  (send-message {"method" "Performance.enable"})
  

  (.loadUrl (.getMainFrame my-browser) "https://clojure.org")
  (dispatch-async
   (fn []
     (cef/cef-run-message-loop)))

  (send-message {"method" "CSS.takeCoverageDelta"})

  (cef/post-task-to-main
   (fn []
     (cef/cef-quit-message-loop)))

  ;; print unused css
  (doseq [sheet-id (keys @css-coverage)]
    (println "-----------------------------")
    (println "sheet id " sheet-id " " (get-in @sheet-params
                                              [sheet-id "sourceURL"]))
    (let [text (get @(send-rpc {"method" "CSS.getStyleSheetText"
                                "params" {"styleSheetId" sheet-id}})
                    "text")]
      (doseq [[[from to] vals] (get @css-coverage sheet-id)
              :let [covered? (get vals true)]
              :when (not covered?)
              :let [start (or from 0)
                    end (or to (count text))
                    s (subs text start end)]]
        (println s))))

  ;; print coverage stats
  (doseq [sheet-id (keys @css-coverage)]
    
    (let [text (get @(send-rpc {"method" "CSS.getStyleSheetText"
                                "params" {"styleSheetId" sheet-id}})
                    "text")
          [total covered]
          (reduce
           (fn [[total covered] [[from to] vals]]
             
             (let [covered? (get vals true)
                   start (or from 0)
                   end (or to (count text))
                   span (- end start)
                   new-total (+ total span)
                   new-covered (+ covered
                                  (if covered?
                                    span
                                    0))]
               [new-total new-covered]))
           [0 0]
           (get @css-coverage sheet-id))]
      
      (println "-----------------------------")
      (println "sheet id " sheet-id " " (get-in @sheet-params
                                                [sheet-id "sourceURL"])
               " "
               covered
               total
               (count text)
               (format "%.2f" (* 100.0
                                 (if (zero? total)
                                   1.0
                                   (/ covered total)))))
      ))

  (send-message {"method" "Performance.getMetrics"})

  (send-message {"method" "Browser.getHistograms"})

  (def node-id (get @(send-rpc {"method" "DOM.querySelector"
                                "params" {"nodeId" (get-in @(send-rpc {"method" "DOM.getDocument"})
                                                           ["root" "nodeId"])
                                          "selector" "#wf-form-Search-Form"}})
                    "nodeId"))
  

  @(send-rpc {"method" "DOM.collectClassNamesFromSubtree"
              "params" {"nodeId" node-id}})
  
  




  
  ,)


(defn print-stats [{:keys [url
                           browser]}]
  (print-css-coverage-stats url browser))

(defn print-unused [{:keys [url
                           browser]}]
  (print-css-coverage-unused url browser))


(defn -main [{:keys [print-stats
                     print-unused
                     url
                     browser-size]}]
  )

#_(defn -main [{:keys [file url size out]
              :or {size [400 400]
                   out "output.png"} :as m}]
  (prn m)
  (when-let [img (if file
                   (file-to-image file size)
                   (url-to-image url size))]
    (save-to-image! out img)))


