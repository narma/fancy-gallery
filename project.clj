(defproject
  boot-project
  "0.0.0-SNAPSHOT"
  :dependencies
  [[org.clojure/clojure "1.7.0"]
   [adzerk/boot-cljs "1.7.48-6"]
   [adzerk/boot-cljs-repl "0.2.0"]
   [adzerk/boot-reload "0.4.1"]
   [pandeiro/boot-http "0.7.0"]
   [deraen/boot-sass "0.1.1"]
   [org.clojure/clojurescript "1.7.170"]
   [org.omcljs/om "1.0.0-alpha24"]
   [sablono "0.3.6"]
   [datascript "0.13.2"]]
  :source-paths
  ["src" "style" "resources/public"])
