#!/usr/bin/env boot

(set-env!
 :source-paths #{"src" "style"}
 :resource-paths #{"resources/public"}
 :dependencies '[
                 [org.clojure/clojure "1.7.0"]

                  ;; boot stuff
                 [adzerk/boot-cljs "1.7.170-3"]
                 ;; repl stuff
                 [adzerk/boot-cljs-repl "0.3.0"]
                 [com.cemerick/piggieback "0.2.1" :scope "test"]
                 [weasel "0.7.0" :scope "test"]
                 [org.clojure/tools.nrepl "0.2.12" :scope "test"]

                 [adzerk/boot-reload "0.4.2"]
                 [pandeiro/boot-http "0.7.0"]
                 [mathias/boot-sassc "0.1.5"]

                 [org.clojure/clojurescript "1.7.170"]
                 [org.omcljs/om "1.0.0-alpha24"]
                 [sablono "0.4.0"]
                 [datascript "0.13.3"]
                 ]
                 )

(require
  '[adzerk.boot-cljs :refer [cljs]]
  '[adzerk.boot-cljs-repl :refer [cljs-repl start-repl]]
  '[adzerk.boot-reload :refer [reload]]
  '[pandeiro.boot-http :refer [serve]]
  '[mathias.boot-sassc :refer [sass]]
  '[boot.cli :as cli])

;; How to use js libs in clojurescript
; http://hugoduncan.org/post/clojurescript-libs-with-js-dependencies/
(deftask dev
  "Start development environment"
  []
  (comp (serve :dir "target"
               :port 5000)
        (watch)
        (sass :sass-file "main.scss"
              :source-maps true)
        (speak)
        (reload :on-jsload 'gallery.core/main)
        (cljs-repl)
        (cljs :source-map true
              :optimizations :none
              :compiler-options {
;                                 :foreign-libs [{:file "js/photoswipe/photoswipe.js"
;                                                 :provides ["PhotoSwipe"]}]
;                                 :externs ["js/photoswipe/ext.js"]
                                 :warnings {:single-segment-namespace false}})
        ))

(deftask prod
  "Compile production version"
  []
  (set-env! :target-path "prod")
  (comp
    (cljs :optimizations :advanced
          :compiler-options {:warnings {:single-segment-namespace false}})
    (sass :compression true)))

;; Scripts
(def scripts-deps '[
                 [org.clojure/clojure "1.7.0"]
                 [me.raynes/conch "0.8.0"]
                 [me.raynes/fs "1.4.6"]
                 ])
(deftask scripts
  "Execute scripts"
  []
  (set-env! :source-paths #{"scripts"})
  (set-env! :dependencies scripts-deps)
  identity)

(deftask prep-images
  "prepate images script"

  [i images-dir IMAGES_DIR str "Dir with original photos"
   d dest-dir DEST_DIR str "Destination dir"
   s size SIZE int "New dimension size"]

  (require 'images.prep)
  (with-pre-wrap fileset
    (let [prep (resolve 'images.prep/main)]
      (prep images-dir dest-dir)
      fileset)))

(task-options!
  prep-images {:images-dir "photos"})

(deftask lein
  "lein generate helper"
  []
  (set-env! :source-paths #{"src" "scripts"})
  (set-env! :dependencies #(into [] (concat % scripts-deps)))
  identity)
