(ns utils.im
  "ImageMagick tools helpers"
  (:require 
   [clojure.string :as str]
   [me.raynes.conch :refer [programs with-programs let-programs] :as sh]))  
   
(programs identify convert)

(defn image-size
  "returns image size by filename => (w h)"
  [filename]
  (map #(Integer/parseInt %)
       (-> (identify "-ping" "-format" "%w %h" filename)
           (str/split #"\s"))))

(defn shrink
  [filename out {:keys [w h]}]
  (convert filename "-resize" (str w "x" h ">") out))