(ns com.phronemophobic.fs
  (:require [clojure.java.io :as io])
  (:import (java.util.zip ZipFile GZIPInputStream ZipOutputStream ZipEntry)
           (org.apache.commons.compress.archivers.tar
            TarArchiveInputStream
            TarArchiveEntry)
           (java.io
            PipedOutputStream
            PipedInputStream)
           (java.io ByteArrayOutputStream File PrintStream PipedInputStream PipedOutputStream)
           (org.apache.commons.compress.compressors.bzip2 BZip2CompressorInputStream)
           (org.apache.commons.compress.compressors.xz XZCompressorInputStream))
  )

;; raynes.fs unnecessarily coerces everything to a file
;; https://github.com/clj-commons/fs/blob/master/src/me/raynes/fs/compression.clj
;; using raynes.fs code, but with streams

(defn- tar-entries
  "Get a lazy-seq of entries in a tarfile."
  [^TarArchiveInputStream tin]
  (when-let [entry (.getNextTarEntry tin)]
    (cons entry (lazy-seq (tar-entries tin)))))


(defn set-mode
  ([^File f i]
   (set-mode f (bit-and i 2r111) true)
   (set-mode f (bit-and (bit-shift-right i 6) 2r111) false))
  ([^File f b user?]
   (if (> b 7)
     (throw (IllegalArgumentException. "Bad mode"))
     (do (.setReadable f (pos? (bit-and b 4)) user?)
         (.setWritable f (pos? (bit-and b 2)) user?)
         (.setExecutable f (pos? (bit-and b 1)) user?)))))


(defn- check-final-path-inside-target-dir! [^File f ^File target-dir entry]
  (when-not (-> f .getCanonicalPath (.startsWith (str (.getCanonicalPath target-dir) File/separator)))
    (throw (ex-info "Expanding entry would be created outside target dir"
                    {:entry entry
                     :entry-final-path f
                     :target-dir target-dir}))))

(defn untar
  "Takes a tarfile `source` and untars it to `target`."
  [source target]
  (with-open [tin (TarArchiveInputStream. (io/input-stream source))]
    (let [target-dir-as-file (io/as-file target)]
      (doseq [^TarArchiveEntry entry (tar-entries tin)
              :when (not (.isDirectory entry))
              :let [output-file (io/file target (.getName entry))]]
        (check-final-path-inside-target-dir! output-file target-dir-as-file entry)
        (.mkdirs (.getParentFile output-file))
        (io/copy tin output-file)
        (when (.isFile entry)
          (set-mode output-file (.getMode entry)))))))


(defn bunzip2
  "Takes a path to a bzip2 file `source` and uncompresses it."
  [source target]
  (io/copy (BZip2CompressorInputStream. source)
           target))

(defn untar-bz2 [source target]
  (let [pis (PipedInputStream.)
        pos (PipedOutputStream. pis)
        bunzip2-thread (future
                         (with-open [is (io/input-stream source)]
                           (bunzip2 is pos)))
        untar-thread (future
                       (untar pis target))]
    @bunzip2-thread
    @untar-thread
    nil))


