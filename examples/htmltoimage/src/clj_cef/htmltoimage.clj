(ns clj-cef.htmltoimage
  (:require [com.phronemophobic.cef :as cef
             :refer [print-doc
                     cef-string]]
            [clojure.java.io :as io]
            
            [com.phronemophobic.cinterop
             :refer [dispatch-sync
                     dispatch-async]])
  (:import
   java.awt.image.BufferedImage
   java.awt.image.DataBuffer
   java.awt.image.DataBufferInt
   java.awt.image.Raster
   javax.imageio.ImageIO
   java.awt.image.ColorModel
   java.awt.Point
   [java.net URLEncoder])
  (:gen-class))

(comment
  (do
    (require '[com.phronemophobic.gen2 :as gen2])
    ;; import classes for auto complete
    (gen2/import-cef-classes))
  ,)


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
                           false ;; (.isAlphaPremultiplied default-cm)
                           nil)]
    bi))

(def hello "data:text/html,%3Ch1%3EHello%2C%20World!%3C%2Fh1%3E")

(defn test-url [s]
  (str "data:text/html," (URLEncoder/encode s "utf-8")))






(defn make-browser []
  )

(defonce initialized
  (delay
    (let [context-initialized (promise)]
      (dispatch-sync
       (fn []
         (cef/prepare-environment!)
         (let [



               bph
               (cef/map->browser-process-handler
                {:on-context-initialized
                 (fn [bph]
                   #_(cef/cef-browser-host-create-browser window-info client url browser-settings nil nil)
                   (deliver context-initialized true))
                 :on-schedule-message-pump-work
                 (fn [this delay])})


               app (cef/map->app
                    {:get-browser-process-handler
                     (fn [app]
                       bph)})]
           (cef/cef-initialize app))))
      @context-initialized)))


;; (defn )

(defn url-to-image
  ([url]
   (url-to-image url [400 400]))
  ([url [width height]]

   @initialized
   (let [browser-settings (cef/map->browser-settings)
         image (promise)]
     (dispatch-async
      (fn []
        (let [latest-paint (atom nil)
              browser (atom nil)
              load-ended (atom false)
              lsh (cef/map->life-span-handler
                   {:on-after-created
                    (fn [this b]
                      (.loadUrl (.getMainFrame b)
                                url)
                      (reset! browser b))
                    :on-before-close
                    (fn [this b]
                      (reset! browser nil))})
              
              load-handler (cef/map->load-handler
                            {:on-loading-state-change
                             (fn [this browser is-loading can-go-back? can-go-forward?])
                             :on-load-end
                             (fn [this browser frame i]
                               (reset! load-ended true))})


              client (cef/map->client
                      {:get-life-span-handler
                       (fn [client]
                         lsh)
                       :get-load-handler
                       (fn [client]
                         load-handler)
                       :get-render-handler
                       (fn [client]
                         (cef/map->render-handler
                          {
                           :get-view-rect
                           (fn [handler browser rect]
                             (set! (.width rect) width)
                             (set! (.height rect) height))
                           :on-paint
                           (fn [handler browser type n rects buffer width height]
                             (reset! latest-paint
                                     (rects->img type rects (.getIntArray buffer 0 (* width height)) width height))
                             
                             
                             )}))})

              window-info (cef/map->window-info
                           {:windowless-rendering-enabled 1})]
          (cef/cef-browser-host-create-browser window-info client nil browser-settings nil nil)
          ;; (cef/cef-run-message-loop)
          (doseq [i (range 15)
                  :while (not @load-ended)]
            (cef/cef-do-message-loop-work)
            (Thread/sleep 500))
          (cef/cef-do-message-loop-work)
          
          (when-let [browser @browser]
            (.closeBrowser (.getHost browser) 1))
          
          (deliver image @latest-paint)
          )))
     @image)))

(comment
  (when-let [img (url-to-image "data:text/html,%3Cb%3EHello%2C%20World!%3C%2Fb%3E"
                               [800 800])]
    (save-to-image! "browser2.png" img))

  ,)

(defn file-to-image [f [width height :as size]]
  (url-to-image (str (io/as-url (io/as-file f))) size))



(defn -main [{:keys [file url size out]
              :or {size [400 400]
                   out "output.png"} :as m}]
  (prn m)
  (when-let [img (if file
                   (file-to-image file size)
                   (url-to-image url size))]
    (println "writing to" out)
    (save-to-image! out img)))
