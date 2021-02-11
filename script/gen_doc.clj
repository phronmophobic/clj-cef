(ns gen-doc
  (:require [codox.main :as codox]))

(defn -main [& args]
  (codox/generate-docs
   {:source-paths ["src/"]
    :namespaces '[com.phronemophobic.cef
                  com.phronemophobic.cinterop]
    :output-path "doc/reference"}))
