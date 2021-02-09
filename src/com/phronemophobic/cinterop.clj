(ns com.phronemophobic.cinterop
  (:require [net.n01se.clojure-jna :as jna])
  (:import com.sun.jna.Pointer
           com.sun.jna.Memory
           com.sun.jna.ptr.FloatByReference
           com.sun.jna.ptr.IntByReference
           com.sun.jna.IntegerType))

(def main-class-loader @clojure.lang.Compiler/LOADER)
(def void Void/TYPE)

(def cljcef
  (try
    (com.sun.jna.NativeLibrary/getInstance "cljcef")
    (catch java.lang.UnsatisfiedLinkError e
      nil)))


(defmacro defc
  ([fn-name ret]
   `(defc ~fn-name ~ret []))
  ([fn-name ret args]
   `(defc ~fn-name cljcef ~ret ~args))
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

(defn dispatch-sync [f]
  (if (and main-queue dispatch_sync)
    (let [callback (DispatchCallback. f)
          args (to-array [main-queue nil callback])]
      (.invoke ^com.sun.jna.Function dispatch_sync void args)
      ;; please don't garbage collect me :D
      (identity args)
      nil)
    (f)))


(defonce not-garbage
  (atom []))

(defn preserve!
  "Store this value so it's not garbage collected"
  [x]
  (swap! not-garbage conj x)
  x)
