(ns com.phronemophobic.cinterop
  "Utilities for interop with c."
  (:require [net.n01se.clojure-jna :as jna])
  (:import com.sun.jna.Pointer
           com.sun.jna.Memory
           com.sun.jna.Platform
           com.sun.jna.ptr.FloatByReference
           com.sun.jna.ptr.IntByReference
           com.sun.jna.IntegerType
           java.util.concurrent.Executors))

(def ^:no-doc main-class-loader @clojure.lang.Compiler/LOADER)
(def ^:no-doc void Void/TYPE)

(declare cljcef)


(def ^:no-doc cljcef
  (delay
    (try
      (com.sun.jna.NativeLibrary/getInstance "cljcef")
      (catch java.lang.UnsatisfiedLinkError e
        (throw e)))))

(def ^:no-doc cef
  (delay
    (if (Platform/isLinux)
      (try
        (com.sun.jna.NativeLibrary/getInstance "cef")
        (catch java.lang.UnsatisfiedLinkError e
          (throw e)))
      @cljcef)))


(defn test-load-cljcef []
  ((requiring-resolve 'com.phronemophobic.cef/prepare-environment!))
  @cljcef)

(defn test-load-cef []
  ((requiring-resolve 'com.phronemophobic.cef/prepare-environment!))
  @cef)


(defmacro ^:no-doc defc
  ([fn-name lib ret]
   `(defc ~fn-name ~lib ~ret []))
  ([fn-name lib ret args]
   (let [cfn-sym (with-meta (gensym "cfn") {:tag 'com.sun.jna.Function})]
     `(let [~cfn-sym (delay (.getFunction ~(with-meta `(deref ~lib) {:tag 'com.sun.jna.NativeLibrary})
                                          ~(name fn-name)))]
        (defn- ~fn-name [~@args]
          (.invoke (deref ~cfn-sym)
                   ~ret (to-array [~@args])))))))



(def ^:no-doc
  objlib (delay
           (try
             (com.sun.jna.NativeLibrary/getInstance "CoreFoundation")
             (catch UnsatisfiedLinkError e
               nil))))

(def ^:no-doc
  main-queue (delay
               (when @objlib
                 (.getGlobalVariableAddress ^com.sun.jna.NativeLibrary objlib "_dispatch_main_q"))))

(defc dispatch_sync objlib void [f])
(defc dispatch_async objlib void [f])

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

(defonce dispatch-executor (delay
                             (let [thread-factory
                                   (reify
                                     java.util.concurrent.ThreadFactory
                                     (newThread [this r]
                                       (let [thread (.newThread (Executors/defaultThreadFactory)
                                                                r)]
                                         (.setDaemon thread true)
                                         thread)))]
                               (Executors/newSingleThreadExecutor thread-factory))))

(defn dispatch-async
  "Run `f` on the main thread. Will return immediately."
  [f]
  (if (Platform/isLinux)
    (.submit @dispatch-executor f)
    (let [callback (DispatchCallback. f)
          args (to-array [])]
      (dispatch_async @main-queue nil callback)
      ;; please don't garbage collect me :D
      (identity args)
      nil)))

(defn dispatch-sync
  "Run `f` on the main thread. Waits for `f` to complete before returning."
  [f]
  (if (Platform/isLinux)
    (.get (.submit @dispatch-executor f))
    (let [callback (DispatchCallback. f)
          args (to-array [])]
      (dispatch_sync @main-queue nil callback)
      ;; please don't garbage collect me :D
      (identity args)
      nil)))


(defonce ^:no-doc not-garbage
  (atom []))

(defn ^:no-doc preserve!
  "Store this value so it's not garbage collected"
  [x]
  (swap! not-garbage conj x)
  x)
