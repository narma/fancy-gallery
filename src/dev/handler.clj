(ns dev.handler 
 (:require 
  [bidi.ring :refer (make-handler files)]
  [pandeiro.boot-http.impl :refer [dir-handler]]))
  
  
(def dev-handler 
   (make-handler ["/" [
                       ["photos/" (files {:dir "photos"})]
                       [true (dir-handler {:dir "target/dev"})]]]))
                       
