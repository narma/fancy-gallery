(ns gallery.core
  (:require [goog.dom :as gdom]
   [om.dom :as dom] ;; don't touch! https://github.com/omcljs/om/issues/475
   [om.next :as om :refer-macros [defui]]
   [gallery.state :refer [read mutate]]
   [gallery.server.io :refer [fetch]]
   [gallery.util.log :refer [log]]
   [gallery.views.index :refer [Index]]))

(enable-console-print!)

(def init-data {:photos/list []})

(defn main 
 []
 (let [reconciler
       (om/reconciler
          {:state init-data
           :parser (om/parser {:read read :mutate mutate})})]    
  (om/add-root! reconciler
     Index (gdom/getElement "app"))
  (fetch "/static/photos.js" #(log %))))