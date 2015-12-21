#!/usr/bin/env boot

(set-env!
 :source-paths #{"src/ui" "style"}
 :resource-paths #{"resources/public"}
 :target-path "target/dev"
 :dependencies '[
                 [org.clojure/clojure "1.7.0"]
                 ;; clojurescript
                 [org.clojure/clojurescript "1.7.170"]
                 [org.omcljs/om "1.0.0-alpha25"]
                 [sablono "0.4.0"]
                 ;; dev deps
                 ;; dev server
                 [ring/ring-devel "1.4.0" :scope "test"]
                 [bidi "1.21.1" :scope "test"]
                 ;; boot stuff
                 [adzerk/boot-cljs "1.7.170-3" :scope "test"]
                 [adzerk/boot-reload "0.4.2" :scope "test"]
                 [pandeiro/boot-http "0.7.0" :scope "test"]
                 [mathias/boot-sassc "0.1.5" :scope "test"]
                 [tonsky/boot-anybar "0.1.0" :scope "test"]
                 ;; repl stuff
                 [adzerk/boot-cljs-repl "0.3.0"]
                 [com.cemerick/piggieback "0.2.1" :scope "test"]
                 [weasel "0.7.0" :scope "test"]
                 [org.clojure/tools.nrepl "0.2.12" :scope "test"]])
                 

(require
  '[adzerk.boot-cljs :refer [cljs]]
  '[adzerk.boot-cljs-repl :refer [cljs-repl start-repl]]
  '[adzerk.boot-reload :refer [reload]]
  '[pandeiro.boot-http :refer [serve]]
  '[mathias.boot-sassc :refer [sass]]
  '[tonsky.boot-anybar :refer [anybar]]
  '[boot.cli :as cli])

;; How to use js libs in clojurescript
; http://hugoduncan.org/post/clojurescript-libs-with-js-dependencies/
(deftask dev
  "Start development environment"
  []
  (comp (serve :handler 'dev.handler/dev-handler
               :reload true
               :port 5000)
        (watch)
        (speak)
        ; (anybar)
        (reload :on-jsload 'gallery.core/main)
        (cljs-repl)
        (cljs :source-map true
              :optimizations :none)
        (sass :sass-file "main.scss"
              :source-maps true)
        (target :dir ["target/dev"])))

(deftask prod
  "Compile production version"
  []
  (set-env! :target-path "target/prod")
  (comp
    (cljs :optimizations :advanced)
    (sass :sass-file "main.scss"
              :source-maps false)))

;; Scripts
(def scripts-deps '[
                    [org.clojure/clojure "1.7.0"]
                    [me.raynes/conch "0.8.0"]
                    [me.raynes/fs "1.4.6"]
                    [com.cognitect/transit-clj "0.8.285"]])

(deftask scripts
 []
 (set-env! :source-paths #{"scripts"})
 (set-env! :dependencies scripts-deps)
 identity)
 
(deftask cli
  "Execute cli"
  [x script SCRIPT str "script to execute"
   a args ARGS str "args for script"]
  (set-env! :source-paths #{"scripts"})
  (set-env! :dependencies scripts-deps)
  (require 'cli.main)
  (with-pre-wrap fileset
    (let [main (resolve 'cli.main/-main)]
      (main script args))
    fileset))
  

(deftask lein
  "lein generate helper"
  []
  (set-env! :source-paths #{"src/ui" "scripts"})
  (set-env! :dependencies #(into [] (concat % scripts-deps)))
  identity)
