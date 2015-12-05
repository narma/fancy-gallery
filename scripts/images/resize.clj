(ns images.resize
  "Resize all images in src-dir and write to dst-dir
  "
  (:require
   [clojure.java.io :as io]
   [utils.im :refer [shrink]]
   [utils.io :refer [image? images]]))
  
(defn resize
  [src-dir out-dir w h]
  (doseq [img (images src-dir)]
    (shrink (.getAbsolutePath img)
            (str out-dir "/" (.getName img))
            {:w w :h h})))
