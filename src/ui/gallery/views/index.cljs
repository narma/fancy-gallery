(ns gallery.views.index
  (:require [om.next :as om :refer-macros [defui]]
            [sablono.core :as html :refer-macros [html]]
            [gallery.util.log :refer [log]]
            [gallery.views.photoswipe :refer [photoswipe]]))
            
(defn photo-html
  [{:keys [name] :as opts}]
  [:figure {:key name :on-click #(log %1)}
    [:img {:src (str "/photos/thumbs/" name)}]])
    
(defui Index
  static om/IQuery
  (query [this]
    '[:app/thumbs :app/photos])
  Object
  (render 
   [this]
   (let [{:keys [app/thumbs app/photos]} (om/props this)]
     (html [:div
            (photoswipe (om/props this))
            [:div.gallery
               (map photo-html thumbs)]]))))
            