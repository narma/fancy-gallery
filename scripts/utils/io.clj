(ns utils.io
 (:require
  [clojure.java.io :as io]
  [me.raynes.fs :as fs]))

(defn image?
  [file]
  (let [image-exts #{".jpg" ".jpeg" ".png"}
        ext (-> (.getName file)
                (fs/extension))]
    (contains? image-exts ext)))
    
 (defn images
  [dir]
  (->> (file-seq (io/file dir))
    (filter image?)))