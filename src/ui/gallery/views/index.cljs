(ns gallery.views.index
  (:require [om.next :as om :refer-macros [defui]]
            [sablono.core :as html :refer-macros [html]]
            [gallery.views.photoswipe :refer [Photoswipe]]))
            
(defn photo-html
  [{:keys [name] :as opts}]
  (println opts)
  [:figure {:key name}
    [:img {:src (str "/photos/thumbs/" name)}]])
    
(defui Index
  static om/IQuery
  (query [this]
    '[:app/photos])
  Object
  (render 
   [this]
   (let [{:keys [app/photos]} (om/props this)]
     (html [:div.gallery
            (map photo-html photos)]))))
            