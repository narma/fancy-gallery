(ns gallery.views.index
  (:require [om.next :as om :refer-macros [defui]]
            [sablono.core :as html :refer-macros [html]]
            [gallery.util.log :refer [log]]
            [gallery.state :refer [read mutate]]
            [gallery.views.photoswipe :refer [Photoswipe photoswipe]]))
            
(defmethod mutate 'photo/click
  [{:keys [state]} _ {:keys [index]}]
  {:action
    (fn []
     (swap! state update-in [:photoswipe] assoc :goto index :is-open true)
     (swap! state update-in [:toggle] inc))})
            
(defn photo-html
  [onClick index {:keys [name] :as opts}]
  [:figure {:key name :on-click #(onClick index)}
    [:img {:src (str "/photos/thumbs/" name)}]])
    
(defui Index
  static om/IQuery
  (query [this]
   `[:app/thumbs :app/photos :toggle {:photoswipe ~(om/get-query Photoswipe)}])
  Object
  (render 
   [this]
   (let [{:keys [app/thumbs app/photos]} (om/props this)
         onPhotoClick (fn [index]
                       (om/transact! this `[(photo/click {:index ~index})]))]
     (html [:div
            (photoswipe (:photoswipe (om/props this)))
            [:div.gallery
               (map-indexed (partial photo-html onPhotoClick) thumbs)]]))))
            