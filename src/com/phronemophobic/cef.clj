(ns com.phronemophobic.cef
  (:require [com.phronemophobic.gen2 :as gen2]
            [com.phronemophobic.cinterop :as cinterop
             :refer [defc
                     preserve!]])
  (:import com.sun.jna.WString))

(gen2/import-cef-classes)
(gen2/gen-wrappers)

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

(defn cef-browser-host-create-browser [window-info client url browser-settings extra-info request-context]
  (cef_browser_host_create_browser window-info client (cef-string url) browser-settings extra-info request-context))

(defc cef_shutdown void [])
(defn cef-shutdown []
  (cef_shutdown))




