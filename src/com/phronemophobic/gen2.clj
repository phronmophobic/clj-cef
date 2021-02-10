(ns com.phronemophobic.gen2
  (:require [clojure.data.json :as json]
            [com.phronemophobic.cinterop :as cinterop
             :refer [preserve!]])
  (:import com.sun.jna.Structure$FFIType$size_t
           com.sun.jna.CallbackProxy
           com.sun.jna.Pointer
           com.sun.jna.WString
           [com.phronemophobic.cljcef
            CefStringUtf16])
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

(defc cef_string_wide_to_utf16 cljcef Integer/TYPE [wstr len cef-string])

(defn cef-edits [structs]
  (update-in structs ["_cef_base_ref_counted_t" "props"]
             (fn [props]
               (mapv (fn [prop]
                       (if (= "fptr" (get prop "type"))
                         (assoc prop "args" [["void"]])
                         prop))
                     props))))

(defn load-structs []
  (cef-edits
   (json/read
    (clojure.java.io/reader
     (clojure.java.io/resource "cef.json")))))









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



;; public class Functions extends Structure {
;;   public static interface OpenFunc extends Callback {
;;     int invoke(String name, int options);
;;   }
;;   public static interface CloseFunc extends Callback {
;;     int invoke(int fd);
;;   }
;;   public OpenFunc open;
;;   public CloseFunc close;
;; }


(defn str->camelcase [s]
  (let [s (clojure.string/replace s #"_t$" "")
        parts (clojure.string/split s #"_" )
        parts (map clojure.string/capitalize parts)]
    (clojure.string/join parts)))

(defn interface-name [prop]
  (str (str->camelcase (get prop "name")) "Func"))

(def cef-enums
  #{
    "cef_log_severity_t"
    "cef_state_t"
    "cef_return_value_t"
    "cef_cookie_priority_t"
    "cef_cookie_same_site_t"
    "cef_termination_status_t"
    "cef_path_key_t"
    "cef_storage_type_t"
    "cef_errorcode_t"
    "cef_cert_status_t"
    "cef_window_open_disposition_t"
    "cef_drag_operations_mask_t"
    "cef_text_input_mode_t"
    "cef_v8_accesscontrol_t"
    "cef_v8_propertyattribute_t"
    "cef_postdataelement_type_t"
    "cef_resource_type_t"
    "cef_transition_type_t"
    "cef_urlrequest_flags_t"
    "cef_urlrequest_status_t"
    "cef_process_id_t"
    "cef_thread_id_t"
    "cef_thread_priority_t"
    "cef_message_loop_type_t"
    "cef_com_init_mode_t"
    "cef_value_type_t"
    "cef_jsdialog_type_t"
    "cef_menu_id_t"
    "cef_mouse_button_type_t"
    "cef_touch_event_type_t"
    "cef_pointer_type_t"
    "cef_paint_element_type_t"
    "cef_event_flags_t"
    "cef_menu_item_type_t"
    "cef_context_menu_type_flags_t"
    "cef_context_menu_media_type_t"
    "cef_context_menu_media_state_flags_t"
    "cef_context_menu_edit_state_flags_t"
    "cef_key_event_type_t"
    "cef_focus_source_t"
    "cef_navigation_type_t"
    "cef_xml_encoding_type_t"
    "cef_xml_node_type_t"
    "cef_dom_document_type_t"
    "cef_dom_event_category_t"
    "cef_dom_event_phase_t"
    "cef_dom_node_type_t"
    "cef_file_dialog_mode_t"
    "cef_color_model_t"
    "cef_duplex_mode_t"
    "cef_cursor_type_t"
    "cef_uri_unescape_rule_t"
    "cef_json_parser_options_t"
    "cef_json_writer_options_t"
    "cef_pdf_print_margin_type_t"
    "cef_scale_factor_t"
    "cef_plugin_policy_t"
    "cef_referrer_policy_t"
    "cef_response_filter_status_t"
    "cef_color_type_t"
    "cef_alpha_type_t"
    "cef_text_style_t"
    "cef_main_axis_alignment_t"
    "cef_cross_axis_alignment_t"
    "cef_button_state_t"
    "cef_horizontal_alignment_t"
    "cef_menu_anchor_position_t"
    "cef_menu_color_type_t"
    "cef_ssl_version_t"
    "cef_ssl_content_status_t"
    "cef_scheme_options_t"
    "cef_cdm_registration_error_t"
    "cef_composition_underline_style_t"
    "cef_channel_layout_t"
    "cef_media_route_create_result_t"
    "cef_media_route_connection_state_t"
    "cef_media_sink_icon_type_t"
    "cef_text_field_commands_t"

    })

(def type-map
  (into
   {"char" "byte"
    "wchar_t" "char"
    "long long" "long"
    "size_t" "SizeT"
    "const char* " "String"
    "int64" "long"
    "uint32" "int"
    "char16" "char"
    "int32" "int"
    "uint64" "long"
    
    

    "cef_paint_element_type_t" "int"
    "cef_process_id_t" "int"
    "cef_string_map_t" "Pointer"
    "cef_string_multimap_t" "Pointer"
    
    

    "cef_file_dialog_mode_t" "int"
    "cef_color_type_t" "int"
    "cef_color_t" "int"
    "cef_string_t" "CefStringUtf16"
    "cef_string_userfree_t"  "CefStringUtf16"
    "cef_composition_underline_style_t" "int"
    "cef_event_flags_t" "int"
    "cef_string_list_t" "Pointer"
    "cef_channel_layout_t" "int"
    "cef_main_axis_alignment_t" "int"
    "cef_cross_axis_alignment_t" "int"
    "cef_state_t" "int"
    "cef_drag_operations_mask_t" "int"
    "cef_mouse_button_type_t" "int"}
   (map #(vector % "int") cef-enums)))



(def struct-name-offset (count "struct _"))
(defn s->type [s]
  (cond

    (vector? s)
    (let [pptype (first s)]
      (cond
        (and (string? pptype)
             (let [pptype (clojure.string/replace pptype #"^const " "")]
               (or (.startsWith pptype "struct ")
                   (.startsWith pptype "cef_"))))
        (s->type (clojure.string/replace pptype #"^const " ""))

        :else
        "Pointer"))
    

    (contains? type-map s)
    (get type-map s)

    (.startsWith s "struct")
    (let [name (str->camelcase (subs s struct-name-offset))]
      name)

    (.startsWith s "cef_")
    (let [name (str->camelcase s)]
      name)

    :else
    s))

(defn prop->type [prop]
  (let [ptype (get prop "type")]
    (cond
      (= ptype "fptr")
      (interface-name prop)

      (vector? ptype)
      (let [pptype (first ptype)]
        (cond
          (and (string? pptype)
               (or (.startsWith pptype "struct ")
                   (.startsWith pptype "cef_")))
          (s->type pptype)
          

          :else
          "Pointer"))

      (contains? type-map ptype)
      (get type-map ptype)
      
      (.startsWith ptype "cef_")
      (str->camelcase ptype)
      
      :else ptype)))







(defn gen-prop [prop]
  (str "public " (prop->type prop) " " (get prop "name") ";"))



(defn gen-fn-interface [prop]
  
  (str "public static interface " (interface-name prop) " extends Callback {

" (s->type (get prop "ret")) " " (get prop "name") "(" (clojure.string/join ", "
                                                                    (for [[i arg] (map-indexed vector (get prop "args"))]
                                                                      (str (s->type arg) " x"i )
                                                                      )
                                                                    ) ");"" 
}"))

(defn gen-fn-method [struct prop]
  (when (and (= (s->type (first (get prop "args")))
                (str->camelcase (get struct "name")))
             ;; clear is a banned name. figure out a work around later
             (not= "clear" (get prop "name")))
    (let [camel-name (str->camelcase (get prop "name"))
          fn-method-name
          (str (Character/toLowerCase (first camel-name))
               (subs camel-name 1))

          fn-method (str "this." (get prop "name"))
          maybe-return (when (not= "void" (get prop "ret"))
                         "return")

          ]
      (str "public " (s->type (get prop "ret")) " " fn-method-name " ("
           (clojure.string/join ", "
                                (rest
                                 (for [[i arg] (map-indexed vector (get prop "args"))
                                       :let [arg-type (s->type arg)
                                             arg-type (if (= "CefStringUtf16" arg-type)
                                                        "String"
                                                        arg-type)]]
                                   (str arg-type " x"i )
                                   ))
                                )") {

"maybe-return "  this." (get prop "name")  "." (get prop "name") "("
           (clojure.string/join
            ", "
            (for [[i arg] (map-indexed vector (get prop "args"))]
              (if (zero? i)
                "this"
                (if (= "CefStringUtf16" (s->type arg))
                  (str "CljCefLib.toCefString(" (str "x"i) ")")
                  (str " x"i ))))) ");

}" ))))

(defn class-name [struct]
  (str->camelcase (get struct "name")))

(defn gen-java [struct]
  (let [props (get struct "props")]
    (str "// AUTO GENERATED BY gen2.clj!

package com.phronemophobic.cljcef;\n\n"

         "import com.phronemophobic.cljcef.*;\n\n"
         "import com.sun.jna.Structure;\n\n"
         "import com.sun.jna.Callback;\n\n"
         "import com.sun.jna.Pointer;\n\n"
         "import java.util.List;\n\n"
         "import java.util.Arrays;\n\n"
         
         "public class " (class-name struct) " extends Structure{\n"

         (when (= "base"
                  (-> props first (get "name")))
           (str "public " (class-name struct) "(){
base.size.setValue(this.size());

"
                (when (= "cef_base_ref_counted_t"
                         (-> props first (get "type")))
                  "ReferenceCountImpl.track(this.getPointer());")
                "

}"))

         "\n\n"

         

         (clojure.string/join
          "\n\n"
          (->> props
               (filter #(= "fptr" (get % "type")))
               (map gen-fn-interface )))

         "\n\n"

         (clojure.string/join
          "\n\n"
          (map gen-prop props))
         "\n\n"

         "protected List getFieldOrder() {
                                            return Arrays.asList(" (clojure.string/join ", " (map #(str "\"" (get % "name") "\"") (get struct "props"))) ");
 }"

         "\n\n"
         (clojure.string/join
          "\n\n"
          (->> props
               (filter #(= "fptr" (get % "type")))
               (map #(gen-fn-method struct %))))

         "}"


         ))
  )
(defn write-java! [struct]
  (let [fname (str (class-name struct) ".java")
        path (clojure.java.io/file  "javasrc" "com" "phronemophobic" "cljcef" fname)]
    (spit path (gen-java struct))))

(defn write-javas! []
  (run! write-java! (vals (load-structs))))

(defn- get-type-doc [prop]
  (let [ptype (get prop "type")
        type-doc (if (= ptype "fptr")
                   (str "(fn ["
                        (clojure.string/join
                         ","
                         (for [arg (get prop "args")]
                           (s->type arg)))
                        "]) -> " (s->type (get prop "ret")))
                   ptype)]
    type-doc))

(defn import-cef-classes*
  ([]
   (import-cef-classes* (vals (load-structs))))
  ([structs]
   (let [classes
         (into []
               cat
               (for [struct structs
                     :let [sname (get struct "name")]]
                 (into [(symbol (str (str->camelcase sname)))]
                       (for [prop (get struct "props")
                             :when (= "fptr" (get prop "type"))]
                         (symbol (str                                  
                                  (str->camelcase sname)
                                  "$"
                                  (interface-name prop)))))
                 ))]
     `(import (quote
               [~(symbol "com.phronemophobic.cljcef")
                ~@classes])))))

(defmacro import-cef-classes  []
  (import-cef-classes*))


(defn cef-string [s]
  (let [cef-str (preserve! (CefStringUtf16.))
        wstr (preserve! (WString. s))
        _ (cef_string_wide_to_utf16 wstr (.length wstr) cef-str)]
    cef-str))

(defn make-doc-string [struct]
  (let [props (->> (get struct "props")
                   (remove #(= "base" (get % "name"))))]
    (str

     (->> (get struct "comment")
          (map #(clojure.string/replace % #"^[/\s]+" ""))
          (remove empty?)
          (map #(str "  " %))
          (clojure.string/join "\n"))
     "\n\n"

     (clojure.string/join
      "\n\n"
      (for [prop props
            :let [comment (get prop "comment")]
            :when (seq comment)
            :let [text (->> comment
                            (map #(clojure.string/replace % #"^[/\s]+" ""))
                            (remove empty?)
                            (map #(str "  " %))
                            (clojure.string/join "\n"))]]
        (str
         "  "
         (keywordize (get prop "name")) " " (get-type-doc prop)
         "\n" text))))))

(defn gen-wrapper* [struct]
  (let [sname (get struct "name")

        
        props (->> (get struct "props")
                   (remove #(= "base" (get % "name"))))
        
        

        fname (symbol (str "map->"
                           (-> sname
                               (clojure.string/replace #"^cef_" "")
                               (clojure.string/replace #"_t$" "")
                               symbolize)))
        merge-fname (symbol (str "merge->"
                                 (-> sname
                                     (clojure.string/replace #"^cef_" "")
                                     (clojure.string/replace #"_t$" "")
                                     symbolize)))


        doc (make-doc-string struct)

        map-doc
        (str "Make a " (str->camelcase sname) "\n"
             doc)

        merge-doc
        (str "Merge properties of a " (str->camelcase sname) "\n"
             doc)

        constructor (symbol (str (str->camelcase sname) "."))

        arg (into {:as 'm
                   :keys (vec
                          (for [prop props]
                            (symbolize (get prop "name"))))})

        struct-sym 'struct
        
        set-properties
        (for [prop props]
          `(when (contains? ~'m ~(keywordize (get prop "name")))
             (set! (~(symbol (str "." (get prop "name")))
                    ~struct-sym)
                   ~(let [ptype (get prop "type")]
                      (case ptype

                        "fptr"
                        (let [fn-args (for [i (range (count (get prop "args")))]
                                        (gensym "arg-"))]
                          `(do
                             (preserve!
                              (reify
                                ~(symbol (str (str->camelcase sname)
                                              "$"
                                              (interface-name prop)))

                                (~(symbol (get prop "name")) [this# ~@fn-args]
                                 (try
                                   (~(symbolize (get prop "name")) ~@fn-args)
                                   (catch Exception e#
                                     (println e#))))))))

                        "cef_string_t"
                        `(cef-string ~(symbolize (get prop "name")))

                        ;; else
                        (symbolize (get prop "name")))
                      )
                   )))


        ]

    `(do
       (defn ~merge-fname ~merge-doc [~struct-sym ~arg]
         (let [valid-keys# #{~@(map #(keywordize (get % "name")) props)}]
           (assert (every? valid-keys#
                           (keys ~'m))
                   (str "Invalid key(s) passed to " ~(str merge-fname) ": "
                        (clojure.string/join
                         ", "
                         (remove valid-keys# (keys ~'m))))))
         (do
           ~@set-properties
           (doto ~struct-sym
             (.write))))
       (defn ~fname ~map-doc
         ([]
          (preserve! (~constructor)))
         ([~arg]
          (let [~struct-sym (preserve! (~constructor))]
            (~merge-fname ~struct-sym ~'m)))))))



(defn gen-wrappers*
  ([]
   (gen-wrappers* (vals (load-structs))))
  ([structs]
   `(do
      ~@(map gen-wrapper* structs))))

(defmacro gen-wrappers []
  (gen-wrappers*))

(defn gen-doc* [struct]
  (let [sname (get struct "name")]
    `(extend-protocol ~'IDoc
       ~(symbol (str->camelcase sname))
       (~'doc [_#]
        ~(make-doc-string struct))
       (~'print-doc [this#]
        (println (~'doc this#))))))

(defn gen-docs* [structs]
  `(do
     (defprotocol ~'IDoc
       (~'doc [_#])
       (~'print-doc [_#]))
     ~@(map gen-doc* structs)))

(defmacro gen-docs []
  (gen-docs* (vals (load-structs))))




