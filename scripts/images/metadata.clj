(ns images.metadata
  "Generate metadata in edn format for dir with photos
  format is:
  [{:w width :h height :name filename}]
  "
  (:require
   [clojure.java.io :as io]
   [cognitect.transit :as t]
   [utils.im :refer [image-size]]
   [utils.io :refer [images]])
 (:import [java.io File ByteArrayInputStream ByteArrayOutputStream]))
   
(defn image-info
  [^java.io.File file]
  (let [[w h] (image-size (.getAbsolutePath file))]
    {:w w :h h :name (.getName file)}))

(defn gen-metadata
  [dir]
  (->> (images dir)
       (sort-by #(.getName %))
    (map image-info)))

(defn write-metadata
  [dir & [metadata-filename]]
  (let [metadata (gen-metadata dir)
        buff (ByteArrayOutputStream. 4096)
        w (t/writer buff :json)
        out-filename (or metadata-filename "metadata.json")
        out (str dir "/" out-filename)]
    (t/write w metadata)
    (spit out (.toString buff)))) 
