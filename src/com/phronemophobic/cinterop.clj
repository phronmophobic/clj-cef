(ns com.phronemophobic.cinterop
  "Utilities for interop with c."
  (:require [net.n01se.clojure-jna :as jna])
  (:import com.sun.jna.Pointer
           com.sun.jna.Memory
           com.sun.jna.ptr.FloatByReference
           com.sun.jna.ptr.IntByReference
           com.sun.jna.IntegerType
           java.util.concurrent.Executors))

(def ^:no-doc main-class-loader @clojure.lang.Compiler/LOADER)
(def ^:no-doc void Void/TYPE)

(declare cljcef)


(def ^:no-doc cef
  (try
    (com.sun.jna.NativeLibrary/getInstance "/tmp/com.phronemophobic.cef/libcef.so")
    (catch java.lang.UnsatisfiedLinkError e
      ;;(throw e)
      nil
      )))

(def ^:no-doc cljcef
  (try
    (com.sun.jna.NativeLibrary/getInstance "/tmp/com.phronemophobic.cef/libcljcef.so")
    (catch java.lang.UnsatisfiedLinkError e
      ;; (throw e)
      nil
      )))




(defmacro ^:no-doc defc
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



(def ^:no-doc
  objlib (try
           (com.sun.jna.NativeLibrary/getInstance "CoreFoundation")
           (catch UnsatisfiedLinkError e
             nil)))

(def ^:no-doc
  main-queue (when objlib
               (.getGlobalVariableAddress ^com.sun.jna.NativeLibrary objlib "_dispatch_main_q")))

(def ^:no-doc
  dispatch_sync (when objlib
                  (.getFunction ^com.sun.jna.NativeLibrary objlib "dispatch_sync_f")))
(def ^:no-doc
  dispatch_async (when objlib
                   (.getFunction ^com.sun.jna.NativeLibrary objlib "dispatch_async_f")))

(defonce ^:no-doc
  callbacks (atom []))

(deftype ^:no-doc DispatchCallback [f]
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

(def dispatch-executor (delay (Executors/newSingleThreadExecutor)))

(defn dispatch-async
  "Run `f` on the main thread. Will return immediately."
  [f]
  (if (and main-queue dispatch_sync)
    (let [callback (DispatchCallback. f)
          args (to-array [main-queue nil callback])]
      (.invoke ^com.sun.jna.Function dispatch_async void args)
      ;; please don't garbage collect me :D
      (identity args)
      nil)
    (.submit @dispatch-executor f)))

(defn dispatch-sync
  "Run `f` on the main thread. Waits for `f` to complete before returning."
  [f]
  (if (and main-queue dispatch_sync)
    (let [callback (DispatchCallback. f)
          args (to-array [main-queue nil callback])]
      (.invoke ^com.sun.jna.Function dispatch_sync void args)
      ;; please don't garbage collect me :D
      (identity args)
      nil)
    (.get (.submit @dispatch-executor f))))


(defonce ^:no-doc not-garbage
  (atom []))

(defn ^:no-doc preserve!
  "Store this value so it's not garbage collected"
  [x]
  (swap! not-garbage conj x)
  x)
