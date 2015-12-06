(ns utils.im
  "ImageMagick tools helpers"
  (:require 
   [clojure.string :as str]
   [me.raynes.conch :refer [programs with-programs let-programs] :as sh]))  
   
(programs identify convert mogrify)

(defn image-size
  "returns image size by filename => (w h)"
  [filename]
  (map #(Integer/parseInt %)
       (-> (identify "-ping" "-format" "%w %h" filename)
           (str/split #"\s"))))

(defn shrink
  [filename out {:keys [w h]}]
  (convert filename "-resize" (format "%sx%s" w h) out))
  
(defn thumb
 [filename out {:keys [w h]}]
 (let [size (format "%sx%s" w h)]
   (mogrify filename "-define" "jpeg:size=500x" "-thumbnail" (str size "^") 
          "-gravity" "center" "-extent" size)))