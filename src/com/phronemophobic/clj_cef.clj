(ns com.phronemophobic.clj-cef
  (:require [com.phronemophobic.cef :as cef
             :refer [print-doc
                     cef-string]]
            [com.phronemophobic.cinterop
             :as cinterop
             :refer [dispatch-sync
                     defc
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

(def main-class-loader @clojure.lang.Compiler/LOADER)

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
  (str "data:text/html," (URLEncoder/encode s "utf-8"))
  
  )
(defonce messages (atom []))
(defn log [s]
  (println s)
  (swap! messages conj s)
  nil)

(defn example []
  (cef/download-and-prepare-environment!)

  (def url "https://github.com/phronmophobic/membrane")
  (def browser-settings (cef/map->browser-settings
                         ;; disable
                         ;;{:webgl 2}
                         ))

  (defonce browser (atom nil))
  (def lsh (cef/map->life-span-handler
            {:on-after-created
             (fn [this b]
               ;; (.setContextClassLoader (Thread/currentThread) main-class-loader)
               (println "craeted brwoser!")
               (reset! browser b))
             :on-before-close
             (fn [this b]
               ;; (.setContextClassLoader (Thread/currentThread) main-class-loader)
               (reset! browser nil)
               (cef/cef-quit-message-loop))}))

  ;; (defonce load-handler (cef/map->load-handler))
  ;; (cef/merge->load-handler
  ;;  load-handler
  ;;  {:on-loading-state-change
  ;;   (fn [this browser is-loading can-go-back? can-go-forward?]
  ;;     (.setContextClassLoader (Thread/currentThread) main-class-loader))
  ;;   :on-load-end
  ;;   (fn [this browser frame i]
  ;;     (.setContextClassLoader (Thread/currentThread) main-class-loader))})

  (defonce client (cef/map->client
                   {:get-life-span-handler
                    (fn [client]
                      ;; (.setContextClassLoader (Thread/currentThread) main-class-loader)
                      (println "getting life span handler")
                      lsh)
                    ;; :get-load-handler
                    ;; (fn [client]
                    ;;   load-handler)
                    :get-render-handler
                    (fn [client]
                      (println "getting render handler")
                      (cef/map->render-handler
                       {

                        :get-view-rect
                        (fn [handler browser rect]
                          ;; (.setContextClassLoader (Thread/currentThread) main-class-loader)
                          (println "getting view rect")
                          (set! (.width rect) 200)
                          (set! (.height rect) 200))
                        :on-paint
                        (fn [handler browser type n rects buffer width height]
                          ;; (.setContextClassLoader (Thread/currentThread) main-class-loader)
                          (println "painting")
                          ;; (.setContextClassLoader (Thread/currentThread) main-class-loader)
                          (save-to-image! "browser.png"
                                          (rects->img type rects (.getIntArray buffer 0 (* width height)) width height)))})
                      )}))
  

  (def window-info (cef/map->window-info
                    {:windowless-rendering-enabled 1}))

  (def bph
    (cef/map->browser-process-handler
     {:on-context-initialized
      (fn [bph]
        ;; (.setContextClassLoader (Thread/currentThread) main-class-loader)
        (println "creating browser: " url)
        (cef/cef-browser-host-create-browser window-info client url browser-settings nil nil)
        nil)
      ;; :on-schedule-message-pump-work
      ;; (fn [this delay])
      }))



  (defonce app (cef/map->app
                {:get-browser-process-handler
                 (fn [app]
                   bph)}))

  (cef/cef-initialize app)

  ,)






(defn -main [& args]
  (println "starting")
  (dispatch-sync example)
  
  (dotimes [i 100]
    (println "pumping")
    (dispatch-sync cef/cef-do-message-loop-work)
    (Thread/sleep 500))
  #_(dispatch-async cef/cef-run-message-loop)
  ;; (println "waiting 15 seconds")
  (Thread/sleep 30e3)
  (println "closing browser")
  (.closeBrowser (.getHost @browser) 1))
