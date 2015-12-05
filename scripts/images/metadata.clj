(ns images.metadata
  "Generate metadata in edn format for dir with photos
  format is:
  [{:w width :h height :name filename}]
  "
  (:require
   [clojure.java.io :as io]
   [clojure.data.json :as json]
   [utils.im :refer [image-size]]
   [utils.io :refer [images]]))
   
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
        out (str dir "/metadata.json")]
    (println metadata)
    (spit out (json/write-str metadata))))
