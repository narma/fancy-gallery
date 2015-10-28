(ns gallery.core
  (:require [goog.dom :as gdom]
            [om.next :as om :refer-macros [defui]]
            [datascript.core :as d]
            [gallery.state :refer [read mutate]]
            [gallery.parts.counter :refer [Counter]]
            [gallery.parts.photoswipe :refer [Photoswipe]]))

(enable-console-print!)

(def conn (d/create-conn {}))

(d/transact! conn
  [{:db/id -1
    :app/title "Hello, DataScript!"
    :app/count 0}])

(def reconciler
  (om/reconciler
    {:state conn
     :parser (om/parser {:read read :mutate mutate})}))

(defn main []
  (om/add-root! reconciler
    Photoswipe (gdom/getElement "app")))

(main)
