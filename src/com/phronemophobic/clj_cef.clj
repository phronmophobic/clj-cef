(ns com.phronemophobic.clj-cef
  (:require [net.n01se.clojure-jna :as jna]
            [com.phronemophobic.gen2 :as gen2]
            [com.phronemophobic.cef :as cef
             :refer [print-doc
                     cef-string]]
            [com.phronemophobic.cinterop :refer
             [dispatch-sync]])
  (:import
   java.awt.image.BufferedImage
   java.awt.image.DataBuffer
   java.awt.image.DataBufferInt
   java.awt.image.Raster
   javax.imageio.ImageIO
   java.awt.image.ColorModel
   java.awt.Point)
  
  )

(gen2/import-cef-classes)

(defn save-to-image!
  [f bi]
  (with-open [os (clojure.java.io/output-stream f)]
    (ImageIO/write ^BufferedImage bi "png" os)))

(def band-masks (int-array [0xFF0000, 0xFF00, 0xFF, 0xFF000000]))
(def default-cm (ColorModel/getRGBdefault))
(defn rects->img [type rects buffer width height]
  (let [
        db (DataBufferInt. buffer (alength buffer))
        raster (Raster/createPackedRaster db width height width band-masks nil)
        bi (BufferedImage. default-cm
                           raster
                           (.isAlphaPremultiplied default-cm)
                           nil)]
    bi))


(def n (atom 0) )


(defn -run2 []
  (cef/cef-load-library)
  (let [

        url "https://github.com/phronmophobic/membrane"
        browser-settings (cef/map->browser-settings)

        client (cef/map->client
                {:get-render-handler
                 (fn [client]
                   (cef/map->render-handler
                    {:get-view-rect
                     (fn [handler browser rect]
                       (set! (.width rect) 800)
                       (set! (.height rect) 700))
                     :on-paint
                     (fn [handler browser type n rects buffer width height]
)})
                   )})

        ;; cef_window_info_t window_info = {};
        window-info (cef/map->window-info
                     {:windowless-rendering-enabled 1})

        bph (cef/map->browser-process-handler
             {:on-context-initialized
              (fn [bph]

                (cef/cef-browser-host-create-browser window-info client url browser-settings nil nil))})

        app (cef/map->app
             {:get-browser-process-handler
              (fn [app]
                bph)})

        main-args (cef/map->main-args)

        settings
        (cef/map->settings
         {:framework-dir-path "/Users/adrian/workspace/clj-cef/csource/Contents/Frameworks/Chromium Embedded Framework.framework"
          :browser-subprocess-path "/Users/adrian/workspace/clj-cef/csource/ceftest Helper"
          :main-bundle-path "/Users/adrian/workspace/clj-cef/"
          ;; :no-sandbox 1
          :windowless-rendering-enabled 1})

        code (cef/cef-initialize main-args, settings, app, nil)

        ]
    (cef/cef-run-message-loop)
    (println "shutting down")
    (cef/cef-shutdown)
    )

  )




(defn example []
  (cef/cef-load-library)

  (def url "https://github.com/phronmophobic/membrane")
  (def browser-settings (cef/map->browser-settings))

  (defonce browser (atom nil))
  (def lsh (cef/map->life-span-handler
            {:on-after-created
             (fn [this b]
               (reset! browser b))}))

  (def client (cef/map->client
               {:get-life-span-handler
                (fn [client]
                  lsh)
                :get-render-handler
                (fn [client]
                  (cef/map->render-handler
                   {

                    :get-view-rect
                    (fn [handler browser rect]
                      (set! (.width rect) 800)
                      (set! (.height rect) 700))
                    :on-paint
                    (fn [handler browser type n rects buffer width height]
                      (println "painting...")
                      (save-to-image! "browser.png"
                                      (rects->img type rects (.getIntArray buffer 0 (* width height)) width height)))})
                  )}))

        ;; cef_window_info_t window_info = {};
  (def window-info (cef/map->window-info
                    {:windowless-rendering-enabled 1}))




  (def bph
    (cef/map->browser-process-handler
     {:on-context-initialized
      (fn [bph]
        (println "initialized")
        (cef/cef-browser-host-create-browser window-info client url browser-settings nil nil))
      :on-schedule-message-pump-work
      (fn [this delay]
        (println "pump delay " delay))
      }))

  (defonce rph
    (cef/map->render-process-handler
     {:on-browser-created
      (fn [rph,b,dict]
        (println "got browser" b)
        (reset! browser b)

        )
      }))

  (defonce app
    (cef/map->app
     {:get-browser-process-handler
      (fn [app]
        bph)}))

  (println (cef/cef-initialize (cef/map->main-args)
                               (cef/map->settings
                                {:framework-dir-path "/Users/adrian/workspace/clj-cef/csource/Contents/Frameworks/Chromium Embedded Framework.framework"
                                 :browser-subprocess-path "/Users/adrian/workspace/clj-cef/csource/ceftest Helper"
                                 :main-bundle-path "/Users/adrian/workspace/clj-cef/"
                                 :external-message-pump 1
                                 :windowless-rendering-enabled 1})
                               app
                               nil))
  #_(cef/cef-do-message-loop-work)
  #_(cef/cef-run-message-loop)



  ,)

(defn clicks []
  (dotimes [i 100]
    (let [pos {:x (rand-int 500)
               :y (rand-int 500)}]
      (println "click " pos)
      #_(.sendMouseMoveEvent (.getHost @browser)
                           (cef/map->mouse-event
                            pos)
                           0)
      (.sendMouseClickEvent (.getHost @browser)
                              (cef/map->mouse-event
                               pos)
                              0
                              (rand-nth [0 1])
                              1))
    (dispatch-sync work)
    (while (or (not @browser)
               (pos? (.isLoading @browser)))
      
      (dispatch-sync work)
      (Thread/sleep 500))))

(defn load-page [url]
  (when-let [b @browser]
    (let [f (.get_main_frame (.get_main_frame @browser) @browser)]
      (.load_url (.load_url f) f (.getPointer (cef/cef-string url))))))

(defn work []
  (cef/cef-do-message-loop-work))


(defn run [& args]
  (example)
  (cef/cef-run-message-loop)
  #_(dispatch-sync -run2))

