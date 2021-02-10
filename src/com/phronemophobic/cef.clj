(ns com.phronemophobic.cef
  (:require [com.phronemophobic.gen2 :as gen2]
            [com.phronemophobic.cinterop :as cinterop
             :refer [defc
                     preserve!]])
  (:import com.sun.jna.WString))

(gen2/import-cef-classes)
(gen2/gen-wrappers)
(gen2/gen-docs)

(def void Void/TYPE)

(defc swizzle void)
(defonce did-swizzle (swizzle))

(defc cef_string_wide_to_utf16 Integer/TYPE [wstr len cef-string])

(defn cef-string [s]
  (let [cef-str (preserve! (CefStringUtf16.))
        wstr (preserve! (WString. s))
        _ (cef_string_wide_to_utf16 wstr (.length wstr) cef-str)]
    cef-str))

(defc cef_execute_process Integer/TYPE [main-args app ])
(defn cef-execute-process [main-args app]
  (cef_execute_process main-args app))

(defc _cef_load_library Integer/TYPE [String])


(defn cef-load-library []
  (defonce load-cef (_cef_load_library
                   "/Users/adrian/workspace/clj-cef/csource/Contents/Frameworks/Chromium Embedded Framework.framework/Chromium Embedded Framework"))
  )

(defc _cef_initialize Integer/TYPE [args settings application sandbox-info])

(defn cef-initialize
  ([main-args settings app]
   (cef-initialize main-args settings app nil))
  ([main-args settings app sandbox-info]
   (_cef_initialize main-args settings app sandbox-info)))

(defc cef_run_message_loop void [])
(defn cef-run-message-loop []
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
  (cef_browser_host_create_browser window-info client (when url (cef-string url)) browser-settings extra-info request-context))



(defc cef_shutdown void [])
(defn cef-shutdown []
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
