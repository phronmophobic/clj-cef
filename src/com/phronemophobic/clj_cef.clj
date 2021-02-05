(ns com.phronemophobic.clj-cef
  (:require [net.n01se.clojure-jna :as jna])
  (:import com.sun.jna.Pointer
           com.sun.jna.Memory
           com.sun.jna.ptr.FloatByReference
           com.sun.jna.ptr.IntByReference
           com.sun.jna.IntegerType
           java.awt.image.BufferedImage
           java.awt.image.DataBuffer
           java.awt.image.DataBufferInt
           java.awt.image.Raster
           javax.imageio.ImageIO
           java.awt.image.ColorModel
           java.awt.Point)
  )

(def void Void/TYPE)
(def main-class-loader @clojure.lang.Compiler/LOADER)


(def cljcef
  (try
    (com.sun.jna.NativeLibrary/getInstance "cljcef")
    (catch java.lang.UnsatisfiedLinkError e
      nil)))

(defmacro defc
  ([fn-name lib ret]
   `(defc ~fn-name ~lib ~ret []))
  ([fn-name lib ret args]
   (let [cfn-sym (with-meta (gensym "cfn") {:tag 'com.sun.jna.Function})]
     `(if ~lib
        (let [~cfn-sym (.getFunction ~(with-meta lib {:tag 'com.sun.jna.NativeLibrary})
                                     ~(name fn-name))]
          (defn- ~fn-name [~@args]
            (.invoke ~cfn-sym
                     ~ret (to-array [~@args]))))
        (defn- ~fn-name [~@args]
          (throw (Exception. (str ~(name fn-name) " not loaded."))))))))

(defc start cljcef Integer/TYPE [render_handler])



(def objlib (try
              (com.sun.jna.NativeLibrary/getInstance "CoreFoundation")
              (catch UnsatisfiedLinkError e
                nil)))

(def main-queue (when objlib
                  (.getGlobalVariableAddress ^com.sun.jna.NativeLibrary objlib "_dispatch_main_q")))

(def dispatch_sync (when objlib
                     (.getFunction ^com.sun.jna.NativeLibrary objlib "dispatch_sync_f")))

(defonce callbacks (atom []))

(deftype DispatchCallback [f]
  com.sun.jna.CallbackProxy
  (getParameterTypes [_]
    (into-array Class  [Pointer]))
  (getReturnType [_]
    void)
  (callback ^void [_ args]
    (.setContextClassLoader (Thread/currentThread) main-class-loader)

    (import 'com.sun.jna.Native)
    ;; https://java-native-access.github.io/jna/4.2.1/com/sun/jna/Native.html#detach-boolean-
    ;; for some other info search https://java-native-access.github.io/jna/4.2.1/ for CallbackThreadInitializer

    ;; turning off detach here might give a performance benefit,
    ;; but more importantly, it prevents jna from spamming stdout
    ;; with "JNA: could not detach thread"
    (com.sun.jna.Native/detach false)
    (f)
    ;; need turn detach back on so that
    ;; we don't prevent the jvm exiting
    ;; now that we're done
    (com.sun.jna.Native/detach true)))

(defn- dispatch-sync [f]
  (if (and main-queue dispatch_sync)
    (let [callback (DispatchCallback. f)
          args (to-array [main-queue nil callback])]
      (.invoke ^com.sun.jna.Function dispatch_sync void args)
      ;; please don't garbage collect me :D
      (identity args)
      nil)
    (f)))

(comment
  (dispatch-sync
   (fn []
     (start)))
  ,,,)


(defn foo
  "I don't do a whole lot."
  [x]
  (prn x "Hello, World!"))

(defn save-to-image!
  [f bi]
  (with-open [os (clojure.java.io/output-stream f)]
    (ImageIO/write ^BufferedImage bi "png" os)))

#_(defn rects->img [type rects buffer width height]
  ;; (prn (-> (BufferedImage. width height BufferedImage/TYPE_4BYTE_ABGR)
  ;;          (.getRaster))
  ;;      )
  (prn type rects (count buffer) width height)
  (let [
        ;; db (DataBufferInt. buffer (alength buffer))
        
        ;; raster (Raster/createPackedRaster db width height 32 nil)
        
        bi (BufferedImage. width height BufferedImage/TYPE_INT_ARGB_PRE)
        raster (.getRaster bi)
        _ (.setPixels raster (int 0) (int 0) width height  buffer)]
    bi))



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

(deftype RenderHandlerCallback []
  com.sun.jna.CallbackProxy
  (getParameterTypes [_]
    (into-array Class  [Integer/TYPE Pointer Pointer Integer/TYPE Integer/TYPE]))
  (getReturnType [_]
    void)
  (callback ^void [_ args]
    (.setContextClassLoader (Thread/currentThread) main-class-loader)

    
    (try
      (let [[type rects buffer width height] args
            buffer (.getIntArray buffer 0 (* width height))]
        (save-to-image! (str "browser_" (swap! n inc) ".png")
                        (rects->img type rects buffer width height)))
      (catch Exception e
        (println e)))

    (println "got args " args (take-last 2 args))

    nil))

(def render-handler (->RenderHandlerCallback))

(defn -main [& args]
  
  (start render-handler)
  (println "return from start"))

(defc cef_app_t_create cljcef Pointer [])
(defc cef_main_args_t_create cljcef Pointer [])
;; (defc cef_execute_process cljcef Integer/TYPE [main-args app ])
(defc cef_settings_t_create cljcef Pointer [])

(defc cef_settings_t_set_framework_dir_path cljcef void [settings path])


(defc cef_settings_t_set_browser_subprocess_path cljcef void [settings path])
(defc cef_settings_t_set_main_bundle_path cljcef void [settings path])
(defc cef_settings_t_set_no_sandbox cljcef void [settings b])

(defc _cef_load_library cljcef Integer/TYPE [String])
(defc _cef_initialize cljcef Integer/TYPE [args settings application sandbox-info])

(defc cef_string_utf8_to_utf16  cljcef Integer/TYPE [s slen cstr])

(defc cef_browser_settings_t_create cljcef Pointer [])
(defc cef_browser_settings_t_set_accept_language_list cljcef void [settings s])
(defc cef_browser_settings_t_set_cursive_font_family cljcef void [settings s])
(defc cef_browser_settings_t_set_default_encoding cljcef void [settings s])
(defc cef_browser_settings_t_set_fantasy_font_family cljcef void [settings s])
(defc cef_browser_settings_t_set_fixed_font_family cljcef void [settings s])
(defc cef_browser_settings_t_set_sans_serif_font_family cljcef void [settings s])
(defc cef_browser_settings_t_set_serif_font_family cljcef void [settings s])
(defc cef_browser_settings_t_set_standard_font_family cljcef void [settings s])
(defc cef_browser_settings_t_set_default_fixed_font_size cljcef void [settings s])
(defc cef_browser_settings_t_set_default_font_size cljcef void [settings i])
(defc cef_browser_settings_t_set_minimum_font_size cljcef void [settings i])
(defc cef_browser_settings_t_set_minimum_logical_font_size cljcef void [settings i])
(defc cef_browser_settings_t_set_windowless_frame_rate cljcef void [settings i])

(defc cef_run_message_loop cljcef void [])
(defc cef_shutdown cljcef void [])
(defc cef_browser_host_create_browser cljcef void [window-info client url browser-settings extra-info request-context])

(defc cef_browser_host_create_browser cljcef void [window-info client url browser-settings extra-info request-context])

        
(defc cef_string_t_create cljcef Pointer [])
(defc cef_window_info_t_create cljcef Pointer [])
(defc cef_client_t_create cljcef Pointer [])

(defonce not-garbage (atom []))


(defn f->callback [[parameter-types] return-type f]
  (reify
    com.sun.jna.CallbackProxy
    (getParameterTypes [_]
      (into-array Class  parameter-types [Pointer]))
    (getReturnType [_]
      return-type)
    (callback ^void [_ args]
      (.setContextClassLoader (Thread/currentThread) main-class-loader)
      (try
        (apply f args)
        (catch Exception e
          (println e))))))

(defc cef_render_handler_t_set_get_view_rect cljcef void [render-handler get-view-rect])
(defn make-render-handler [{:keys [get-view-rect on-paint]}]
  (let [render-handler (cef_render_handler_t_create)]
    (when get-view-rect
      (swap! not-garbage conj get-view-rect)
      (cef_render_handler_t_set_get_view_rect
       render-handler
       (f->callback
        [Pointer Pointer Pointer Integer/TYPE Integer/TYPE Pointer Pointer]
        Integer/TYPE
        (fn [self browser rect]
          (let [[w h] (get-view-rect)]
            
            )
          1))))

    (when on-paint
      (swap! not-garbage conj on-paint)
      (cef_render_handler_t_set_get_view_rect render-handler get-view-rect))))

(defc cef_client_t_set_get_render_handler cljcef void [app handler])
(deftype GetRenderHandlerCallback [f]
  com.sun.jna.CallbackProxy
  (getParameterTypes [_]
    (into-array Class  [Pointer]))
  (getReturnType [_]
    void)
  (callback ^void [_ args]
    (.setContextClassLoader (Thread/currentThread) main-class-loader)

    
    ))
(defn set-render-handler [client handler]
  )

(defonce load-cef (_cef_load_library "/Volumes/My Passport for Mac/backup/cef/cef_binary_88.1.6+g4fe33a1+chromium-88.0.4324.96_macosx64/Release/Chromium Embedded Framework.framework/Chromium Embedded Framework"))
(defn -run []

  ;; cef_app_t app = {};
  ;; initialize_cef_app(&app);
  (let [ ;;_ (_cef_load_library "/Volumes/My Passport for Mac/backup/cef/cef_binary_88.1.6+g4fe33a1+chromium-88.0.4324.96_macosx64/Release/Chromium Embedded Framework.framework/Chromium Embedded Framework")

        app (cef_app_t_create)


        main-args (cef_main_args_t_create)
        ;;         cef_main_args_t main_args = {};
        ;; main_args.argc = argc;
        ;; main_args.argv = argv;

        


        settings (doto (cef_settings_t_create)
                   (cef_settings_t_set_framework_dir_path "/Volumes/My Passport for Mac/backup/cef/cef_binary_88.1.6+g4fe33a1+chromium-88.0.4324.96_macosx64/Release/Chromium Embedded Framework.framework/")
                   (cef_settings_t_set_browser_subprocess_path "/Users/adrian/workspace/clj-cef/csource/ceftest Helper")
                   (cef_settings_t_set_main_bundle_path "/Users/adrian/workspace/clj-cef/")
                   ;; (cef_settings_t_set_no_sandbox true)
                   (cef_settings_t_set_windowless_rendering_enabled true)
                   )


        code (_cef_initialize main-args, settings, app, nil)
        _ (println "init " code)
        


        
        ;; char url[] = "https://www.google.com/ncr";
        ;; cef_string_t cef_url = {};
        ;; cef_string_utf8_to_utf16(url, strlen(url), &cef_url);
        url "https://www.google.com/ncr"
        curl (cef_string_t_create)
    
        _ (cef_string_utf8_to_utf16 url (count url) curl)
        ;; // Browser settings. It is mandatory to set the
        ;; // "size" member.
        ;; cef_browser_settings_t browser_settings = {};
        ;; browser_settings.size = sizeof(cef_browser_settings_t);
        browser-settings (cef_browser_settings_t_create)
        
    
        ;; // Client handler and its callbacks
        ;; cef_client_t client = {};
        ;; initialize_cef_client(&client);
        client (cef_client_t_create)

        ;; cef_window_info_t window_info = {};
        window-info (cef_window_info_t_create)

        ;; // Create browser asynchronously. There is also a
        ;; // synchronous version of this function available.
        ;; printf("cef_browser_host_create_browser\n");
        ;; cef_browser_host_create_browser(&window_info, &client, &cef_url,
        ;;                                 &browser_settings, NULL);
        _ (cef_browser_host_create_browser window-info client curl browser-settings nil nil)

        ;; // Message loop. There is also cef_do_message_loop_work()
        ;; // that allow for integrating with existing message loops.
        ;; printf("cef_run_message_loop\n");
        ;; cef_run_message_loop();
        _ (cef_run_message_loop)


        ]
    (cef_shutdown)
    )
  
  )
(defn run []
  (dispatch-sync -run))

(barf)



(def settings (cef_settings_t_create))
(cef_settings_t_set_framework_dir_path_test settings "/Volumes/My Passport for Mac/backup/cef/cef_binary_88.1.6+g4fe33a1+chromium-88.0.4324.96_macosx64/Release/Chromium Embedded Framework.framework/")

(def s "/Volumes/My Passport for Mac/backup/cef/cef_binary_88.1.6+g4fe33a1+chromium-88.0.4324.96_macosx64/Release/Chromium Embedded Framework.framework/")

(cef_settings_t_set_framework_dir_path settings s)

(defn load-library
  ([])
  ([framework-path]))

(defn cef_initialize [])


(defn cef_run_message_loop [])


(defn cef_shutdown [])

(defn cef_quit_message_loop [])



;; callbacks

;; BrowserProcessHandler
onContextInitialized

;; CefRenderHandler
;; virtual void GetViewRect(CefRefPtr<CefBrowser> browser, CefRect& rect) OVERRIDE;
;;     virtual void OnPaint(CefRefPtr<CefBrowser> browser,
                         ;; PaintElementType type,
                         ;; const RectList& dirtyRects,
                         ;; const void* buffer,
                         ;; int width,
;; int height) OVERRIDE;

    ;; virtual void OnImeCompositionRangeChanged(CefRefPtr<CefBrowser> browser,
    ;;                                           const CefRange& selected_range,
    ;;                                           const RectList& character_bounds) OVERRIDE {}


  ;; // CefDisplayHandler methods:
  ;; virtual void OnTitleChange(CefRefPtr<CefBrowser> browser,
  ;;                            const CefString& title) OVERRIDE;

  ;; // CefLifeSpanHandler methods:
  ;; virtual void OnAfterCreated(CefRefPtr<CefBrowser> browser) OVERRIDE;
  ;; virtual bool DoClose(CefRefPtr<CefBrowser> browser) OVERRIDE;
;; virtual void OnBeforeClose(CefRefPtr<CefBrowser> browser) OVERRIDE;


  ;; // CefLoadHandler methods:
  ;; virtual void OnLoadError(CefRefPtr<CefBrowser> browser,
  ;;                          CefRefPtr<CefFrame> frame,
  ;;                          ErrorCode errorCode,
  ;;                          const CefString& errorText,
;;                          const CefString& failedUrl) OVERRIDE;




;;; all handlers
public CefClient,
public CefContextMenuHandler,
public CefDisplayHandler,
public CefDownloadHandler,
public CefDragHandler,
public CefFocusHandler,
public CefKeyboardHandler,
public CefLifeSpanHandler,
public CefLoadHandler,
public CefRequestHandler,
public CefResourceRequestHandler 

CEF_EXPORT int cef_browser_host_create_browser(
    const cef_window_info_t* windowInfo,
    struct _cef_client_t* client,
    const cef_string_t* url,
    const struct _cef_browser_settings_t* settings,
    struct _cef_dictionary_value_t* extra_info,
                                               struct _cef_request_context_t* request_context);


;; for every struct
;; a way to set function pointers
;; write getters/setters
;; make/destroy
