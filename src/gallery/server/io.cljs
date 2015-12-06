(ns gallery.server.io
 (:require [gallery.util.log :refer [error]]))

(defn fetch 
 [url cb & [{:as opts}]] 
 (let [promise (js/fetch url opts)] 
   (doto promise
    (.then #(.then (.text %) cb))
    (.catch #(error %)))))