(ns com.phronemophobic.cef
  (:require [com.phronemophobic.gen2 :as gen2]
            [clojure.java.io :as io]
            [com.phronemophobic.cinterop :as cinterop
             :refer [defc
                     preserve!
                     dispatch-sync
                     dispatch-async]]
            [com.phronemophobic.fs :as fs])
  (:import com.sun.jna.WString
           java.nio.file.Files
           java.nio.file.attribute.FileAttribute))

(gen2/import-cef-classes)
(gen2/gen-wrappers)
(gen2/gen-docs)

(defonce prepared-environment (atom false))

(def void Void/TYPE)

(defc change_bundle_path void [bundle-path])

(defc cef_string_wide_to_utf16 Integer/TYPE [wstr len cef-string])

(defn cef-string [s]
  (let [cef-str (preserve! (CefStringUtf16.))
        wstr (preserve! (WString. s))
        _ (cef_string_wide_to_utf16 wstr (.length wstr) cef-str)]
    cef-str))

(defc cef_execute_process Integer/TYPE [main-args app ])
#_(defn cef-execute-process [main-args app]
  (cef_execute_process main-args app))

(defc _cef_load_library Integer/TYPE [String])

(defc _cef_initialize Integer/TYPE [args settings application sandbox-info])


(defc cef_run_message_loop void [])
(defn cef-run-message-loop []
  (assert @prepared-environment "Did you call prepare-environment! yet?")
  (cef_run_message_loop))

(defc cef_browser_host_create_browser void [window-info client url browser-settings extra-info request-context])

(defn cef-browser-host-create-browser
  "Create a new browser window using the window parameters specified by
  |windowInfo|. All values will be copied internally and the actual window will
  be created on the UI thread. If |request_context| is NULL the global request
  context will be used. This function can be called on any browser process
  thread and will not block. The optional |extra_info| parameter provides an
  opportunity to specify extra information specific to the created browser that
  will be passed to cef_render_process_handler_t::on_browser_created() in the
  render process."
  [window-info client url browser-settings extra-info request-context]
  (assert @prepared-environment "Did you call prepare-environment! yet?")
  (cef_browser_host_create_browser window-info client (when url (cef-string url)) browser-settings extra-info request-context))



(defc cef_shutdown void [])
(defn cef-shutdown []
  (assert @prepared-environment "Did you call prepare-environment! yet?")
  (cef_shutdown))





;; // This function should be called from the application entry point function to
;; // execute a secondary process. It can be used to run secondary processes from
;; // the browser client executable (default behavior) or from a separate
;; // executable specified by the CefSettings.browser_subprocess_path value. If
;; // called for the browser process (identified by no "type" command-line value)
;; // it will return immediately with a value of -1. If called for a recognized
;; // secondary process it will block until the process should exit and then return
;; // the process exit code. The |application| parameter may be NULL. The
;; // |windows_sandbox_info| parameter is only used on Windows and may be NULL (see
;; // cef_sandbox_win.h for details).
;; ///
;; CEF_EXPORT int cef_execute_process(const struct _cef_main_args_t* args,
;;                                    cef_app_t* application,
;;                                    void* windows_sandbox_info);

;; ///
;; // This function should be called on the main application thread to initialize
;; // the CEF browser process. The |application| parameter may be NULL. A return
;; // value of true (1) indicates that it succeeded and false (0) indicates that it
;; // failed. The |windows_sandbox_info| parameter is only used on Windows and may
;; // be NULL (see cef_sandbox_win.h for details).
;; ///
;; CEF_EXPORT int cef_initialize(const struct _cef_main_args_t* args,
;;                               const struct _cef_settings_t* settings,
;;                               cef_app_t* application,
;;                               void* windows_sandbox_info);

;; ///
;; // This function should be called on the main application thread to shut down
;; // the CEF browser process before the application exits.
;; ///
;; CEF_EXPORT void cef_shutdown();

;; ///
;; // Perform a single iteration of CEF message loop processing. This function is
;; // provided for cases where the CEF message loop must be integrated into an
;; // existing application message loop. Use of this function is not recommended
;; // for most users; use either the cef_run_message_loop() function or
;; // CefSettings.multi_threaded_message_loop if possible. When using this function
;; // care must be taken to balance performance against excessive CPU usage. It is
;; // recommended to enable the CefSettings.external_message_pump option when using
;; // this function so that
;; // cef_browser_process_handler_t::on_schedule_message_pump_work() callbacks can
;; // facilitate the scheduling process. This function should only be called on the
;; // main application thread and only if cef_initialize() is called with a
;; // CefSettings.multi_threaded_message_loop value of false (0). This function
;; // will not block.
;; ///
;; CEF_EXPORT void cef_do_message_loop_work();

(defc cef_do_message_loop_work void [])
(defn cef-do-message-loop-work []
  (assert @prepared-environment "Did you call prepare-environment! yet?")
  (cef_do_message_loop_work))

;; ///
;; // Run the CEF message loop. Use this function instead of an application-
;; // provided message loop to get the best balance between performance and CPU
;; // usage. This function should only be called on the main application thread and
;; // only if cef_initialize() is called with a
;; // CefSettings.multi_threaded_message_loop value of false (0). This function
;; // will block until a quit message is received by the system.
;; ///
;; CEF_EXPORT void cef_run_message_loop();

;; ///
;; // Quit the CEF message loop that was started by calling cef_run_message_loop().
;; // This function should only be called on the main application thread and only
;; // if cef_run_message_loop() was used.
;; ///
;; CEF_EXPORT void cef_quit_message_loop();

(defc cef_quit_message_loop void [])
(defn cef-quit-message-loop []
  (assert @prepared-environment "Did you call prepare-environment! yet?")
  (cef_quit_message_loop))

;; ///
;; // Set to true (1) before calling Windows APIs like TrackPopupMenu that enter a
;; // modal message loop. Set to false (0) after exiting the modal message loop.
;; ///
;; CEF_EXPORT void cef_set_osmodal_loop(int osModalLoop);

;; ///
;; // Call during process startup to enable High-DPI support on Windows 7 or newer.
;; // Older versions of Windows should be left DPI-unaware because they do not
;; // support DirectWrite and GDI fonts are kerned very badly.
;; ///
;; CEF_EXPORT void cef_enable_highdpi_support();

(def default-target-dir (io/file "/tmp" "com.phronemophobic.cef"))
(defn extract-helper
  ([]
   (extract-helper (doto default-target-dir
                     (.mkdir))))
  ([target-dir]
   (let [source (io/resource "darwin/ceflib Helper")
         target-path (io/file target-dir "ceflib Helper")]
     (when-not (.exists target-path)
       (with-open [in (io/input-stream source)
                  out (io/output-stream target-path)]
         (io/copy in out))
       (.setExecutable target-path true true)
       (.setExecutable target-path true false)))
   nil))

(defn extract-framework
  ([]
   (extract-framework (doto default-target-dir
                        (.mkdir))))
  ([target-dir]
   (let [url (clojure.java.io/as-url "https://cef-builds.spotifycdn.com/cef_binary_88.2.4%2Bgf3c4ca9%2Bchromium-88.0.4324.150_macosx64_minimal.tar.bz2")
         target-download (io/file target-dir "cef.tar.bz2")
         framework-path (io/file target-dir
                                 "cef_binary_88.2.4+gf3c4ca9+chromium-88.0.4324.150_macosx64_minimal"
                                 "Release"
                                 "Chromium Embedded Framework.framework")
         link-path (io/file target-dir "Chromium Embedded Framework.framework")]
     (when-not (.exists link-path)
       (when-not (.exists target-download)
         ;; (println "downloading")
         (with-open [url-stream (io/input-stream url)]
           (io/copy url-stream target-download)))

       ;; (println "extracting...")
       (when-not (.exists framework-path)
         (fs/untar-bz2 target-download target-dir))

       ;; Files.createSymbolicLink(newLink, target);
       (when-not (.exists link-path)
         (Files/createSymbolicLink (.toPath link-path)
                                   (.toPath framework-path)
                                   (into-array FileAttribute []))))

     (when (.exists target-download)
       (.delete target-download))

     nil)))


(defn prepare-environment!
  ([]
   (prepare-environment! (doto default-target-dir
                           (.mkdir))))
  ([target-dir]
   (when-not @prepared-environment
     (extract-helper target-dir)
     (extract-framework target-dir)

     (_cef_load_library
      (.getAbsolutePath
       (io/file target-dir
                "Chromium Embedded Framework.framework"
                "Chromium Embedded Framework")
       ))

     (change_bundle_path (.getAbsolutePath target-dir))
     (reset! prepared-environment true)
     nil)))

(defn cef-initialize
  ([app]
   (assert (.exists (io/file default-target-dir "Chromium Embedded Framework.framework")))
   (cef-initialize (map->main-args)
                   (map->settings
                    {:framework-dir-path (.getAbsolutePath (io/file default-target-dir "Chromium Embedded Framework.framework"))
                     :browser-subprocess-path (.getAbsolutePath (io/file default-target-dir "ceflib Helper"))
                     :main-bundle-path (.getAbsolutePath default-target-dir)
                     ;;:external-message-pump 1
                     ;; :no-sandbox 1
                     :windowless-rendering-enabled 1})
                   app
                   nil))
  ([main-args settings app sandbox-info]
   (assert @prepared-environment "Did you call prepare-environment! yet?")
   (_cef_initialize main-args settings app sandbox-info)))
