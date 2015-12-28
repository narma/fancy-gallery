(ns gallery.core
  (:require [goog.dom :as gdom]
   [om.dom :as dom] ;; don't touch! https://github.com/omcljs/om/issues/475
   [om.next :as om :refer-macros [defui]]
   [cognitect.transit :as t]
   [gallery.state :refer [read mutate]]
   [gallery.server.io :refer [fetch]]
   [gallery.util.log :refer [log]]
   [gallery.views.index :refer [Index]]))

(enable-console-print!)
                              
(def init-state {:app/photos []})
 
(defn fetch-data
 [{:keys [remote-thumbs remote-photos]} cb]
 (when remote-thumbs
  (log "fetch thumbs")
  (fetch "/photos/thumbs/metadata.json" 
   #(cb {:app/thumbs (t/read (om/reader) %)})))
 (when remote-photos
  (log "fetch photos")
  (fetch "/photos/2000/metadata.json" 
   #(cb {:app/photos (t/read (om/reader) %)}))))
 

(defn main 
 []
 (let [reconciler
       (om/reconciler
          {:state init-state
           :remotes [:remote :remote-thumbs :remote-photos]
           :send fetch-data
           :parser (om/parser {:read read :mutate mutate})})]    
  (om/add-root! reconciler
     Index (gdom/getElement "app"))))