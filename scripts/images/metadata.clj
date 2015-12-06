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
 (:import [java.io ByteArrayInputStream ByteArrayOutputStream]))
   
(defn image-info
  [file]
  (let [[w h] (image-size (.getAbsolutePath file))]
    {:w w :h h :name (.getName file)}))

(defn gen-metadata
  [dir]
  (->> (images dir)
    (map image-info)))

(defn write-metadata
  [dir]
  (let [metadata (gen-metadata dir)
        buff (ByteArrayOutputStream. 4096)
        w (t/writer buff :json)
        out (str dir "/metadata.json")]
    (println metadata)
    (t/write w metadata)
    (spit out (.toString buff)))) 
