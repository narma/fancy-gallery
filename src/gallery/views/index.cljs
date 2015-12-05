(ns gallery.views.index
  (:require [om.next :as om :refer-macros [defui]]
            [sablono.core :as html :refer-macros [html]]
            [gallery.state :refer [read mutate]]
            [gallery.views.photoswipe :refer [Photoswipe]]))
            
(defn photo-html
  [{:keys [img-src id]}]
  [:div {:key id}
    [:img {:src img-src}]])
    
(defui Index
  static om/IQuery
  (query [this]
    '[:app/photos])
  Object
  (render [this]
    (let [{:keys [app/photos]} (om/props this)]
      (html [:div.gallery]
        (map photo-html photos)))))
            