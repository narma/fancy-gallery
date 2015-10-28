(ns images.prep
  "Generate metadata in edn format for dir with photos
  format is:
  [{:w width :h height :name filename}]
  "
  (:require
   [clojure.java.io :as io]
   [clojure.string :as str]
   [clojure.pprint :refer [pprint]]
   [me.raynes.conch :refer [programs with-programs let-programs] :as sh]
   [me.raynes.fs :as fs]

   [boot.core :as core]
   [boot.task.built-in :as task]
   ))

(programs identify)

;;; utils
(defn abs-path
  "build and return absolute file path"
  [p & paths]
  (-> (apply fs/file p paths)
      .getCanonicalFile
      .getAbsolutePath))


(defn image?
  [fname]
  (let [image-exts #{".jpg" ".jpeg" ".png"}]
    (contains? image-exts (fs/extension fname))))

(defn images
  "returns [java.io.File] filtered by image extensions"
  [dir]
  (let [f (io/file dir)
        fs (file-seq f)]
     (filter #(->> % .getName fs/extension) fs)))

(defn image-size
  "returns image size by filename => (w h)"
  [filename]
  (println filename)
  (map #(Integer/parseInt %)
       (-> (identify "-ping" "-format" "%w %h" filename)
           (str/split #"\s"))))



(defn gen-metadata
  [dir]
  (letfn [(metadata [path] path)
          (walk [root _ files]
                (for [name files
                      :when (image? name)]
                   (let [[w h] (image-size
                                (abs-path root name))]
                     {:w w :h h :name name})))]
    (fs/walk walk dir)))

(first (second (gen-metadata "../photos")))



(defn main
  [& args]
  (println "hello")
  (let [dir "../photos"
        photos (images dir)]
    (for [p photos
          :let [n (.getName p)
                path (str dir "/" n)]]
      (let [[w h] (image-size path)]
        {:w w :h h :name n}))))

;; (core/deftask hello
;;   "Print a friendly greeting."
;;   []
;;   (println "hello"))




