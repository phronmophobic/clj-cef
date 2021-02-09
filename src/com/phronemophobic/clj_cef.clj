(ns com.phronemophobic.clj-cef
  (:require [net.n01se.clojure-jna :as jna]
            [com.phronemophobic.cef :as cef]
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
                       (println "setting rect" rect)
                       (set! (.width rect) 800)
                       (set! (.height rect) 700))
                     :on-paint
                     (fn [handler browser type n rects buffer width height]
                       (println "paintin " width height))})
                   )})

        ;; cef_window_info_t window_info = {};
        window-info (cef/map->window-info
                     {:windowless-rendering-enabled 1})

        bph (cef/map->browser-process-handler
             {:on-context-initialized
              (fn [bph]
                (println "initialized")
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
          :no-sandbox 1
          :windowless-rendering-enabled 1})
        

        code (cef/cef-initialize main-args, settings, app, nil)
        _ (println "init " code)

        ]
    (cef/cef-run-message-loop)
    (println "shutting down")
    (cef/cef-shutdown)
    )
  
  )

(defn run [& args]

  (dispatch-sync -run2))

