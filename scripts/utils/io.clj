(ns utils.io
 (:require
  [clojure.java.io :as io]
  [me.raynes.fs :as fs])
 (:import [java.io File]))

(defn image?
  [^File file]
  (let [image-exts #{".jpg" ".jpeg" ".png"}
        ext (-> (.getName file)
                (fs/extension))]
    (and 
      (.isFile file)
      (contains? image-exts ext))))


(defn list-files 
 [^File dir]
 (->> (.listFiles dir)
    (filter #(.isFile %))))
 
        
(defn images
 [^String dir]
 (->> (io/file dir)
  list-files
  (filter image?)))