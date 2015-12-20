(ns dev.handler 
 (:require 
  [bidi.ring :refer (make-handler files)]))
  
  
(def dev-handler 
   (make-handler ["/" [
                       ["photos/" (files {:dir "photos"})]
                       ["" (files {:dir "target/dev"})]]]))
                       
