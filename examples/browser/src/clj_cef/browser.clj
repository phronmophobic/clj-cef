(ns clj-cef.browser
  (:require [membrane.ui :as ui]
            [membrane.skija :as skija]
            [clojure.java.io :as io]
            [membrane.basic-components :as basic]
            [com.phronemophobic.gen2 :as gen2]
            [membrane.component :refer
             [defui defeffect make-app]]
            [com.phronemophobic.cef :as cef
             :refer [print-doc
                     cef-string]]
            [com.phronemophobic.cinterop
             :refer [dispatch-sync
                     dispatch-async]])
  (:import
   com.sun.jna.Pointer
   java.nio.ByteBuffer
   [org.jetbrains.skija BackendRenderTarget Canvas ColorSpace DirectContext FramebufferFormat Paint Rect RRect Surface SurfaceColorFormat SurfaceOrigin FontMgr FontStyle Font Path PaintMode Data Image Bitmap ImageInfo ColorType ColorAlphaType ColorSpace EncodedImageFormat]
   [org.lwjgl.glfw Callbacks GLFW GLFWErrorCallback
    GLFWNativeCocoa
    GLFWMouseButtonCallback
    GLFWKeyCallback
    GLFWCursorPosCallback
    GLFWScrollCallback
    GLFWFramebufferSizeCallback
    GLFWWindowRefreshCallback
    GLFWDropCallback
    GLFWCharCallback]
   [com.phronemophobic.cljcef
    CefRect]
   
   [org.lwjgl.opengl GL GL11]
   [org.lwjgl.system MemoryUtil])
  (:gen-class))

(gen2/import-cef-classes)



(def int->bytes @#'skija/int->bytes)

(def initialized (atom false))

(def browser-state (atom {}))

(defn draw-rect [surface buffer width height rx ry rwidth rheight]
  (let [bitmap (Bitmap.)
        image-info (ImageInfo. rwidth rheight ColorType/BGRA_8888 ColorAlphaType/UNPREMUL nil)
        row-bytes (* width 4)
        offset (+ (* 4 rx) (* row-bytes ry))
        end-offset (+ (* 4 (+ rx rwidth))
                      (* row-bytes (dec (+ ry rheight))))
        len (- end-offset offset)
        max-size (* width height 4)
        bits (.getByteArray buffer
                            offset
                            len)]
    (.installPixels bitmap image-info bits row-bytes)
    (.writePixels surface bitmap rx ry)))

(def browser-width 800)
(def browser-height 800)
(def browser-image-info (ImageInfo. browser-width browser-height ColorType/BGRA_8888 ColorAlphaType/UNPREMUL nil))
(def browser-surface (Surface/makeRaster browser-image-info))

(defrecord Browser [browser browser-id focused?]
  ui/IOrigin
  (-origin [_]
    [0 0])


  ui/IMouseMove
  (-mouse-move [elem pos]
    (when browser
      (.sendMouseMoveEvent (.getHost browser) 
                           (cef/map->mouse-event
                            {:x (first pos)
                             :y (second pos)})
                            0)
      ))
  
  ui/IMouseEvent
  (-mouse-event [elem pos button mouse-down? mods]
    (when browser
      (.sendMouseClickEvent (.getHost browser)
                            (cef/map->mouse-event
                             {:x (first pos)
                              :y (second pos)})
                            button
                            (if mouse-down?
                              0
                              1)
                            1)))

  ui/IScroll
  (-scroll [elem delta mpos]
    (when browser
      (.sendMouseWheelEvent (.getHost browser)
                            (cef/map->mouse-event
                             {:x (first mpos)
                              :y (second mpos)})
                            (first delta)
                            (second delta))))

  ui/IHasKeyPress
  (has-key-press [this]
    focused?)
  ui/IKeyPress
  (-key-press [elem k]
    (when (and browser focused?)
      (let [c (if (keyword? k)
                (if (= k :enter)
                  \return
                  (char (get skija/keycodes k)))
                (.charAt k 0))]
       (.sendKeyEvent (.getHost browser)
                      (cef/map->key-event
                       {:type 3
                        :modifiers 0
                        :character c
                        :unmodified-character c})))))

  ui/IHasKeyEvent
  (has-key-event [this]
    focused?)
  ui/IKeyEvent
  (-key-event [elem key code action mods]
    (when (and focused? browser)
      (when (#{:press :release :repeat}
             action)
        (.sendKeyEvent (.getHost browser)
                       (cef/map->key-event
                        {:type (case action
                                 :press 1
                                 :release 1
                                 :repeat 1)
                         :modifiers mods
                         :native-key-code code
                         :character (char key)
                         :unmodified-character (char key)}))))
    )
  

  ui/IBounds
  (-bounds [this]
    [browser-width browser-height])

  skija/IDraw
  (draw [this]
    (.draw browser-surface skija/*canvas* 0 0 (skija/map->paint skija/*paint*))))

(defui browser-view [{:keys [browser browser-id
                             ^:membrane.component/contextual focus]}]
  (let [focused? (= focus
                    browser)]
    (ui/wrap-on
     :mouse-down
     (fn [handler pos]
       (if (not focused?)
         [[:set $focus browser]]
         (handler pos)))
    (Browser. browser browser-id focused?))))

(defn skija-draw [paint-type nrects rects buffer width height]

  (when (zero? paint-type)
    (let [rect-array (.toArray rects (.intValue nrects))]
      (doseq [cef-rect rect-array]
        (draw-rect browser-surface buffer width height
                   (.x cef-rect)
                   (.y cef-rect)
                   (.width cef-rect)
                   (.height cef-rect))))
    (when (pos? (.intValue nrects))
      (swap! browser-state
             update-in [:browser-info :browser-id] (fnil inc 0)))

    #_(let [
            image (.makeImageSnapshot browser-surface)]
        (with-open [is (-> image
                           (.encodeToData EncodedImageFormat/JPEG)
                           (.getBytes)
                           (io/input-stream))]
          (io/copy is
                   (io/as-file (str "browser.png")))))))

(defn start-browser []
  (cef/prepare-environment!)

  (def url "https://www.google.com")
  (def browser-settings (cef/map->browser-settings))


  (def lsh (cef/map->life-span-handler
            {:on-after-created
             (fn [this browser]
               (swap! browser-state
                      assoc
                      :browser-info {:browser-id 0
                                     :browser browser}))
             :on-before-close
             (fn [this b]
               (swap! browser-state dissoc :browser-info)
               (cef/cef-quit-message-loop))}))

  (defonce load-handler (cef/map->load-handler))
  (cef/merge->load-handler
   load-handler
   {:on-loading-state-change
    (fn [this browser is-loading can-go-back? can-go-forward?]
      
      )
    :on-load-end
    (fn [this browser frame i]
      )})


  (defonce client (cef/map->client))
  (cef/merge->client
   client
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
          (set! (.width rect) browser-width)
          (set! (.height rect) browser-height))
        :on-paint
        (fn [handler browser paint-type nrects rects buffer width height]
          (skija-draw paint-type nrects rects buffer width height))})
      )})

  (def window-info (cef/map->window-info
                    {:windowless-rendering-enabled 1}))

  (def bph
    (cef/map->browser-process-handler
     {:on-context-initialized
      (fn [bph]
        (cef/cef-browser-host-create-browser window-info client url browser-settings nil nil)
        (reset! initialized true))
      :on-schedule-message-pump-work
      (fn [this delay])}))


  (defonce app (cef/map->app))
  (cef/merge->app
   app
   {:get-browser-process-handler
    (fn [app]
      bph)})

  (cef/cef-initialize app)

  ,)


(defn work []
  (cef/cef-do-message-loop-work))

(defn run* [view-fn]
  (.set (GLFWErrorCallback/createPrint System/err))
  (GLFW/glfwInit)
  (GLFW/glfwWindowHint GLFW/GLFW_VISIBLE GLFW/GLFW_FALSE)
  (GLFW/glfwWindowHint GLFW/GLFW_RESIZABLE GLFW/GLFW_TRUE)
  (let [width 1000
        height 1000
        window (GLFW/glfwCreateWindow width height "Skija LWJGL Demo" MemoryUtil/NULL MemoryUtil/NULL)]
    (GLFW/glfwMakeContextCurrent window)
    (GLFW/glfwSwapInterval 1)
    (GLFW/glfwShowWindow window)  
    (GL/createCapabilities)
    (let [context (DirectContext/makeGL)
          fb-id   (GL11/glGetInteger 0x8CA6)
          [scale-x scale-y] (skija/display-scale window)
          target  (BackendRenderTarget/makeGL (* scale-x width) (* scale-y height) 0 8 fb-id FramebufferFormat/GR_GL_RGBA8)
          surface (Surface/makeFromBackendRenderTarget context target SurfaceOrigin/BOTTOM_LEFT SurfaceColorFormat/RGBA_8888 (ColorSpace/getSRGB))
          canvas  (.getCanvas surface)
          view-atom (atom nil)
          last-draw (atom nil)
          mouse-position (atom [0 0])]
      (.scale canvas scale-x scale-y)

      (start-browser)

      (binding [skija/*canvas* canvas
                skija/*paint* {}
                skija/*font-cache* (atom {})
                skija/*draw-cache* (atom {})
                skija/*image-cache* (atom {})
                ]
        (try
          (letfn [(repaint! []
                    (assert (identical? canvas skija/*canvas*))
                    
                    (let [layer (.save canvas)

                          view (view-fn)
                          last-view @view-atom]
                      (when (not= view last-view)
                        (reset! view-atom view)
                        (.clear canvas (skija/color 0xFFFFFFFF))
                        (skija/draw view)
                        (.restoreToCount canvas layer)
                        (.flush context)
                        (GLFW/glfwSwapBuffers window)))
                    
                    )
                  (on-mouse-button [window button action mods]
                    (assert (identical? canvas skija/*canvas*))
                    (try
                      (ui/mouse-event @view-atom @mouse-position button (= 1 action) mods)
                      (catch Exception e
                        (println e))))
                  (on-scroll [window offset-x offset-y]
                    (ui/scroll @view-atom [(* 2 offset-x) (* 2 offset-y)] @mouse-position)
                    (repaint!))
                  (on-framebuffer-size [window width height]
                    ;; should be reshaping here
                    )
                  (on-window-refresh [window]
                    (repaint!))

                  (on-drop [window paths]
                    (try
                      (ui/drop @view-atom (vec paths) @mouse-position)
                      (catch Exception e
                        (println e))))
                  (on-cursor-pos [window x y]
                    (try
                      (doall (ui/mouse-move @view-atom [x y]))
                      (doall (ui/mouse-move-global @view-atom [x y]))
                      (catch Exception e
                        (println e)))


                    (reset! mouse-position [(double x)
                                            (double y)])

                    (repaint!))

                  (on-key [window key scancode action mods]
                    (let [action (get skija/key-action-map action :unknown)
                          ui @view-atom]
                      (ui/key-event ui key scancode action mods)
                      (cond

                        ;; paste
                        (and (= key 86)
                             (= action :press)
                             (= mods 8))
                        (let [nodes (->> (tree-seq (fn [n]
                                                     true)
                                                   ui/children
                                                   ui)
                                         (filter #(satisfies? ui/IClipboardPaste %)))]
                          (when-let [s (GLFW/glfwGetClipboardString window)]
                            (doseq [node nodes]
                              (ui/-clipboard-paste node s))))

                        ;; cut
                        (and (= key 88)
                             (= action :press)
                             (= mods 8))
                        (let [node (->> (tree-seq (fn [n]
                                                    true)
                                                  ui/children
                                                  ui)
                                        (filter #(satisfies? ui/IClipboardCut %))
                                        ;; maybe should be last?
                                        first)]
                          (when-let [s (ui/-clipboard-cut node)]
                            #_(GLFW/glfwSetClipboardString window s )))

                        ;; copy
                        (and (= key 67)
                             (= action :press)
                             (= mods 8))
                        (ui/clipboard-copy ui)

                        ;; special keys
                        (or (= :press action)
                            (= :repeat action))
                        (let [k (get skija/keymap key)]
                          (when (keyword? k)
                            (try
                              (ui/key-press ui k)
                              (catch Exception e
                                (println e)))

                            ))
                        ))

                    (repaint!))

                  (on-char [window codepoint]
                    (let [k (String. ^bytes (int->bytes codepoint) "utf-32")
                          ui @view-atom]
                      (try
                        (ui/key-press ui k)
                        (catch Exception e
                          (println e))))

                    (repaint!))
                  ]
            (GLFW/glfwSetMouseButtonCallback window
                                             (skija/mouse-button-callback
                                              on-mouse-button))
            (GLFW/glfwSetScrollCallback window
                                        (skija/scroll-callback on-scroll))
            (GLFW/glfwSetFramebufferSizeCallback window
                                                 (skija/framebuffer-size-callback
                                                  on-framebuffer-size))
            (GLFW/glfwSetWindowRefreshCallback window
                                               (skija/window-refresh-callback
                                                on-window-refresh))
            (GLFW/glfwSetDropCallback window
                                      (skija/drop-callback on-drop))
            (GLFW/glfwSetCursorPosCallback window
                                           (skija/cursor-pos-callback on-cursor-pos))
            (GLFW/glfwSetKeyCallback window
                                     (skija/key-callback on-key))
            (GLFW/glfwSetCharCallback window
                                      (skija/char-callback on-char))
            (loop []
              
              (when (not (GLFW/glfwWindowShouldClose window))
                (repaint!)
                (when @initialized
                  (cef/cef-do-message-loop-work))
                (GLFW/glfwPollEvents)
                (recur))))
          (catch Exception e
            (prn "crash in event loop" e))))

      (Callbacks/glfwFreeCallbacks window)
      (GLFW/glfwHideWindow window)
      (GLFW/glfwDestroyWindow window)
      (GLFW/glfwPollEvents)
      (GLFW/glfwTerminate)
      ;; (GLFW/glfwSetErrorCallback nil)

      )))


(defn run [view-fn]
  (dispatch-sync #(run* view-fn)))


(defeffect ::load-url [browser url]
  (.loadUrl (.getMainFrame browser) url))

(defeffect ::browser-back [browser]
  (when (.canGoBack browser)
    (.goBack browser)))

(defeffect ::browser-forward [browser]
  (when (.canGoForward browser)
    (.goForward browser)))

(defui browser-ui [ {:keys [browser-info url
                            ^:membrane.component/contextual focus]}]
  (ui/vertical-layout
   (ui/horizontal-layout
    (basic/button {:text "<"
                   :on-click
                   (fn []
                     [[::browser-back (get browser-info :browser)]])} 
                  )
    (basic/button {:text ">"
                   :on-click
                   (fn []
                     [[::browser-forward (get browser-info :browser)]])} 
                  )
    (basic/button {:text "load"
                   :on-click
                   (fn []
                     [[::load-url (get browser-info :browser) url]])} 
                  )

    (ui/wrap-on
     :key-press
     (fn [handler s]
       (if (and (= focus $url)
                (= s :enter))
         [[::load-url (get browser-info :browser) url]]
         (handler s)))
     (basic/textarea {:text url
                      :focus focus})))
   (browser-view {:browser (get browser-info :browser)
                  :browser-id (get browser-info :browser-id)
                  :focus focus})))

(defn -main [& args]
  (run (make-app #'browser-ui browser-state)))


