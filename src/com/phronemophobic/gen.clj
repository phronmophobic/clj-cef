(ns com.phronemophobic.gen
  (:require [clojure.data.json :as json])
  (:import com.sun.jna.Structure$FFIType$size_t
           com.sun.jna.CallbackProxy
           com.sun.jna.Pointer)
  )

(def void Void/TYPE)
(def main-class-loader @clojure.lang.Compiler/LOADER)

(def cljcef
  (try
    (com.sun.jna.NativeLibrary/getInstance "cljcef")
    (catch java.lang.UnsatisfiedLinkError e
      nil)))

(defmacro defc
  ([fn-name ret]
   `(defc ~fn-name ~ret []))
  ([fn-name ret args]
   (let [cfn-sym (with-meta (gensym "cfn") {:tag 'com.sun.jna.Function})]
     `(if cljcef
        (let [~cfn-sym (.getFunction ~(with-meta 'cljcef {:tag 'com.sun.jna.NativeLibrary})
                                     ~(name fn-name))]
          (defn- ~fn-name [~@args]
            (.invoke ~cfn-sym
                     ~ret (to-array [~@args]))))
        (defn- ~fn-name [~@args]
          (throw (Exception. (str ~(name fn-name) " not loaded."))))))))



(defn load-structs []
  (json/read
   (clojure.java.io/reader
    (clojure.java.io/resource "cef.json"))))

(defn api-structs []
  (let [structs (load-structs)]
    (into {}
          (comp
           (filter (fn [[k {:strs [path]}]]
                     (.endswith path "capi.h"))))
          structs)))


(def simple-types
  #{"int"
    "float"
    "double"
    "short"
    "size_t"
    "uint32"
    ["void"]
    "char"})

(defn simple-type? [ptype]
  (or (simple-types ptype)
      (and (vector? ptype)
           (simple-type? (first ptype)))))


(defn type->str [ptype]
  (cond

    (= ["void"] ptype)
    "void*"

    (vector? ptype)
     (str (type->str (first ptype)) "*")

     (simple-type? ptype)
     ptype



     (map? ptype)
     
     (get ptype "decl")

     :else
     (str "uhoh " ptype)))



(defn type->binding [ptype]
  (cond

    (simple-type? ptype)
    "x"

    (map? ptype)
    (get ptype "name")

    :else
    (str "uhoh " ptype)))

(defn type->arg [ptype]
  (cond

    (simple-type? ptype)
    (str (type->str ptype) " " (type->binding ptype))

    (map? ptype)
    (get ptype "decl")

    :else
    (str "uhoh " ptype))
  )

(defn struct-getter [sname [pname ptype]]
  (str
   
   (type->str ptype)
   " "
   sname "_get_" pname "(" sname "* s){ return s->"pname  "; }"))

(defn struct-setter [sname [pname ptype]]
  (when (or (simple-type? ptype)
            (= "cef_string_t" ptype)
            (map? ptype))
    (case ptype
      "cef_string_t"
      (str
       
       "void"
       " "
       sname "_set_" pname "(" sname "* self, const char *s)"

       "{ CefString(&self->"pname ").FromASCII(s); }")


      ;;else
      (str
       
       "void"
       " "
       sname "_set_" pname "(" sname "* s, "
       (type->arg ptype)  " )"
       "{ s->"pname  " = " (type->binding ptype) "; }")))
  )

(defn struct-create [struct]
  (let [sname (get struct "name")]
   (str
    sname "* "
    sname "_create(){\n"
    sname "* s = new " sname "();\n"
    (when (= (get-in struct ["props" "base"]) "cef_base_ref_counted_t")
        (str "s->base.size = sizeof("sname ");\n"))
    "return s;\n"
    "}"
    )))


(defn struct-destroy [struct]
  (let [sname (get struct "name")]
    (str
     "void "
     sname "_destroy( "sname "* s){\n"
     "delete s;\n"
     "}"
     )))


(defn gen-struct [struct]
  (let [sname (get struct "name")]
   (clojure.string/join "\n\n"
                        (into
                         [(struct-create struct)
                          (struct-destroy struct)]
                         (sequence
                          cat
                          (for [[pname ptype] (get struct "props")
                                :when (not= "base" pname)]
                            [(when (simple-type? ptype)
                               (struct-getter sname [pname ptype]))
                             (struct-setter sname [pname ptype])]))))))

(defn gen-headers [structs]
  (let [headers (->> structs
                     vals
                     (map #(get % "path"))
                     set)]
    (clojure.string/join "\n"
                         (map #(str "#include \"" % "\"")
                              headers))))

(defn gen-c-wrappers
  ([]
   (gen-c-wrappers (load-structs)))
  ([structs]
   (clojure.string/join "\n\n"
                        [(gen-headers structs)
                         "extern \"C\" {\n"
                         (clojure.string/join "\n\n"
                                              (map gen-struct (vals structs)))
                         "\n}"])))

(defn write-wrappers! []
  (spit "csource/capi-wrappers.h"
        (gen-c-wrappers)))


(defonce not-garbage
  (atom []))

(defn preserve!
  "Store this value so it's not garbage collected"
  [x]
  (swap! not-garbage conj x)
  x)


(defn keywordize [s]
  (-> s
      (clojure.string/replace #"_" "-")
      keyword))

(defn symbolize [s]
  (-> s
      (clojure.string/replace #"_" "-")
      symbol))

(defn size-t [n]
  (com.sun.jna.Structure$FFIType$size_t. n))


(defn type->class [t]
  (case t
    "int" `Integer/TYPE
    "float" `Float/TYPE
    "double" `Double/TYPE
    "short" `Short/TYPE
    "size_t" `com.sun.jna.Structure$FFIType$size_t
    "uint32" `Integer/TYPE
    "char" `Character/TYPE
    "int64" `Long/TYPE
    "void" `void
    "cef_string_t" `String
    "cef_process_id_t" Integer/TYPE
    "cef_drag_operations_mask_t" Integer/TYPE
    "cef_text_input_mode_t" Integer/TYPE
    "cef_paint_element_type_t" Integer/TYPE

    ;; else
    (cond

      (vector? t)
      `Pointer

      (map? t)
      `com.sun.jna.CallbackProxy

      :else
      (assert false (str "Unknown Type: " t)))))

(declare coerce-type)

(defn coerce-callback [{:strs [args ret] :as type}]
  (let [ret-sym (gensym "ret-")]
    `(preserve!
      (fn [x#]
        (assert (fn? x#) (str "Expecting function. received " x#))
        (reify
          com.sun.jna.CallbackProxy
          (getParameterTypes [_#]
            (into-array Class [~@(map type->class args)]))
          (getReturnType [_#]
            ~(type->class ret))
          (callback ^void [_# args#]
            (.setContextClassLoader (Thread/currentThread) main-class-loader)
            (try
              (let [~ret-sym (apply x# args#)]
                ~(if (= ret "void")
                   nil
                   (list (coerce-type ret)
                         ret-sym)))
              (catch Exception e#
                (println e#)))))
        ))))

(defn coerce-type [type]
  (case type
    "int" `int
    "float" `float
    "double" `double
    "short" `short
    "size_t" `size-t
    "uint32" `int
    "int64" `long
    "char" `char
    "cef_string_t" `str
    ["char"] `str
    

    ;; else
    (cond

      (and (map? type)
           (= "fptr" (get type "type")))
      (coerce-callback type)

      
      (vector? type)
      `(fn [x#]
         (assert (instance? Pointer x#))
         x#)

      :else
      (assert false (str "Unknown Type: " type)))))


(comment

  (def tstruct (->(load-structs )
                  vals
                  (nth 2)
                  ))

  ,)
(declare type->class)
(defn def-struct-fns* [structs]
  `(do
      ~@(for [struct (vals structs)
              :let [sname (get struct "name")
                    create-name (symbol
                                 (str sname "_create"))
                    destroy-name (symbol
                                  (str sname "_destroy"))]]
          `(do
             (defc ~create-name Pointer)
             (defc ~destroy-name void [~'struct])
             ~@(for [[pname ptype] (get struct "props")
                     :when (or (simple-type? ptype)
                               (= "cef_string_t" ptype)
                               (map? ptype))
                     :let [set-name (symbol
                                     (str sname "_set_" pname))]]
                 `(defc ~set-name void [~'self ~'x]))))))

(defmacro def-struct-fns
  ([]
   (def-struct-fns* (load-structs)))
  ([structs]
   (def-struct-fns* structs)))



(defn can-gen-callback? [ptype]
  (let [test (fn [ptype]
               (or (#{"int"
                      "float"
                      "double"
                      "short"
                      "size_t"
                      "uint32"
                      "char"
                      "int64"
                      "void"
                      "cef_process_id_t"

                      "cef_drag_operations_mask_t"
                      "cef_text_input_mode_t"
                      "cef_paint_element_type_t"

                      
                      "cef_string_t"}
                    ptype)
                   (vector? ptype)
                   (map? ptype)))]
    (and (test (get ptype "ret"))
         (every? test (get ptype "args")))))

(defn can-gen-struct? [struct]
  (every?
   (fn ok? [ptype]

     (and
      (not= ptype [["char"]])
      (or (simple-type? ptype)
          (= "cef_string_t" ptype)
          (and (map? ptype)
               (can-gen-callback? ptype)))))
   (-> struct
       (get "props")
       (dissoc "base")
       vals
       )))

(defn -gen-clj-struct [struct]
  (let [fname (-> (get struct "name")
                  (clojure.string/replace #"^cef_" "")
                  (clojure.string/replace #"_t$" "")
                  (clojure.string/replace #"_" "-"))

        fname (str "->" fname)
        merge-name (symbol (str "merge" fname))

        fname (symbol fname)
        sname (get struct "name")
        prop-keys (-> struct
                      (get "props")
                      keys
                      (->> (remove #{"base"}))
                      (->> (map symbolize)))
        asserts (for [[k v] (get struct "props")]
                  `(assert ))
        struct-sym (gensym "struct-")]
    `(do
       (defn ~merge-name [~'struct {:keys [~@prop-keys]}]
         ~@(let [struct-sym 'struct]
             (for [[pname ptype] (get struct "props")
                   :when (not= pname "base")
                   :let [set-name (symbol
                                   (str sname "_set_" pname))
                         binding (symbolize pname)]]
               `(when ~binding
                  (~set-name ~struct-sym
                   (~(coerce-type ptype) ~binding)))))
         ~'struct)
       (defn ~fname [{:keys [~@prop-keys] :as ~'m}]
         (let [~struct-sym (preserve!
                            (~(symbol
                               (str sname "_create"))))]
           (~merge-name ~struct-sym ~'m))))
    ))


(defmacro gen-clj-structs []
  `(do
     ~@(->> (load-structs)
            vals
            (filter can-gen-struct?)
            (map -gen-clj-struct))))


(comment
  (gen-clj-structs))

(def-struct-fns)
(gen-clj-structs)



