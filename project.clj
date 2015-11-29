(defproject
  boot-project
  "0.0.0-SNAPSHOT"
  :dependencies
  [[org.clojure/clojure "1.7.0"]
   [adzerk/boot-cljs "1.7.48-6"]
   [adzerk/boot-cljs-repl "0.2.0"]
   [adzerk/boot-reload "0.4.1"]
   [pandeiro/boot-http "0.7.0"]
   [mathias/boot-sassc "0.1.5"]
   [org.clojure/clojurescript "1.7.145"]
   [org.omcljs/om "1.0.0-alpha14"]
   [sablono "0.3.6"]
   [datascript "0.13.2"]
   [org.clojure/clojure "1.7.0"]
   [me.raynes/conch "0.8.0"]
   [me.raynes/fs "1.4.6"]]
  :source-paths
  ["src" "scripts" "resources/public"])