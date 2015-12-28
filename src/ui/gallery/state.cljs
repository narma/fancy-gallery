(ns gallery.state
  (:require [om.next :as om]))

(defmulti read om/dispatch)
(defmulti mutate om/dispatch)

(defmethod read :photoswipe
 [{:keys [state] :as env} _ params]
 (let [st @state]
  {:value (merge (:photoswipe st) {:photos (:app/photos st)})}))

(defmethod read :app/thumbs
  [{:keys [state ast] :as env} k _]
  (let [data (get @state k [])] 
   (merge
     {:value data} 
     (when (empty? data)
       {:remote-thumbs ast}))))
       
(defmethod read :app/photos
 [{:keys [state ast] :as env} k _]
 (let [data (get @state k [])] 
  (merge
    {:value data} 
    (when (empty? data)
      {:remote-photos ast}))))
