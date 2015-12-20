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

; (def init-state {:app/photos [{:name "/photos/thumbs/fotoencho_0232.jpg"}
;                               {:name "/photos/thumbs/fotoencho_0808.jpg"}
;                               {:name "/photos/thumbs/fotoencho_0457.jpg"}
;                               {:name "/photos/thumbs/fotoencho_0098.jpg"}
;                               {:name "/photos/thumbs/fotoencho_0350.jpg"}
;                               {:name "/photos/thumbs/fotoencho_0020.jpg"}
;                               {:name "/photos/thumbs/fotoencho_0137.jpg"}
;                               {:name "/photos/thumbs/fotoencho_0215.jpg"}
;                               {:name "/photos/thumbs/fotoencho_0332.jpg"}
;                               {:name "/photos/thumbs/fotoencho_0488.jpg"}]})
                              
(def init-state {:app/photos []})
 
(defn ask-server-about-state 
 [{:keys [remote-init]} cb]
 (println "ask")
 (when remote-init
  (fetch "/photos/thumbs/metadata.json" 
   #(cb {:app/photos (t/read (om/reader) %)}))))
 

(defn main 
 []
 (let [reconciler
       (om/reconciler
          {:state init-state
           :remotes [:remote :remote-init]
           :send ask-server-about-state
           :parser (om/parser {:read read :mutate mutate})})]    
  (om/add-root! reconciler
     Index (gdom/getElement "app"))))