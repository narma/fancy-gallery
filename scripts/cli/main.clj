(ns cli.main
  (:require 
    [clojure.string :as str]
    [images.metadata :refer [write-metadata]]
    [images.resize :refer [resize]]))

(defn -main 
  [script str-args]
  (let [args (str/split str-args #"\s")]
    (case script
      "metadata"  (write-metadata (first args))
      "resize"   (apply resize args)
      (printf"Invalid command '%s'.\n" script))))