(ns gallery.core
  (:require [goog.dom :as gdom]
   [om.dom :as dom] ;; don't touch! https://github.com/omcljs/om/issues/475
   [om.next :as om :refer-macros [defui]]
   [gallery.state :refer [read mutate]]
   [gallery.parts.photoswipe :refer [Photoswipe]]))

(enable-console-print!)

(def init-data {:gallery/photos []})

(def reconciler
  (om/reconciler
    {:state init-data
     :parser (om/parser {:read read :mutate mutate})}))

(defn main []
  (om/add-root! reconciler
    Photoswipe (gdom/getElement "app")))