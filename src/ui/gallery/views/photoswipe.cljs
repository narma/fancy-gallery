(ns gallery.views.photoswipe
  (:require [om.next :as om :refer-macros [defui]]
            [sablono.core :as html :refer-macros [html]]
            [gallery.util.log :refer [log]]
            [gallery.state :refer [read mutate]]))
            
(defn map-photo 
  [{:keys [name w h]}]
  {:src (str "/photos/2000/" name)
   :msrc (str "/photos/thumbs/" name)
   :w w
   :h h})

(defui Photoswipe
  static om/IQuery
  (query [this]
   '[:app/photos])
  Object
  (componentDidUpdate 
   [this prev-props prev-state]
   (let [_ (log (clj->js (om/props this)))
         node (js/ReactDOM.findDOMNode this)
         {:keys [app/photos]} (om/props this)
         items (map map-photo photos)
         _ (log items)]
     (when-not (empty? items)
          (log "init!!!")
          (.init (js/PhotoSwipe.
                  node
                  js/PhotoSwipeUI_Default
                  (clj->js items))))))
                     
  (render 
   [this]
   (let [{:keys [app/title app/count] :as entity}
         (get-in (om/props this) [:app/counter 0])]
     (html
             ;; Root element of PhotoSwipe. Must have class pswp.
      [:div {:class "pswp"
             :tabIndex -1
             :role "dialog"
             :aria-hidden true}

              ;; Background of PhotoSwipe.
              ;; It's a separate element as animating opacity is faster than rgba().
       [:div {:class "pswp__bg"}]

              ;; Slides wrapper with overflow:hidden.
       [:div {:class "pswp__scroll-wrap"}

               ;; Container that holds slides.
               ; PhotoSwipe keeps only 3 of them in the DOM to save memory.
               ; Don't modify these 3 pswp__item elements, data is added later on.
        [:div {:class "pswp__container"}
         [:div {:class "pswip__item"}]
         [:div {:class "pswip__item"}]
         [:div {:class "pswip__item"}]]

               ;; Default (PhotoSwipeUI_Default) interface on top of sliding area. Can be changed.
        [:div {:class "pswp__ui pswp__ui--hidden"}

         [:div {:class "pswp__top-bar"}

                 ;; Controls are self-explanatory. Order can be changed.
          [:div {:class "pswp__counter"}]

          [:button {:class "pswp__button pswp__button--close"
                    :title "Close (Esc)"}]

          [:button {:class "pswp__button pswp__button--share"
                    :title "Share"}]

          [:button {:class "pswp__button pswp__button--fs"
                    :title "Toggle fullscreen"}]

          [:button {:class "pswp__button pswp__button--zoom"
                    :title "Zoom in/out"}]

                 ;; Preloader demo http://codepen.io/dimsemenov/pen/yyBWoR
                 ; element will get class pswp__preloader--active when preloader is running
          [:div {:class "pswp__preloader"}
           [:div {:class "pswp__preloader__icn"}
            [:div {:class "pswp__preloader__cut"}
             [:div {:class "pswp__preloader__donut"}]]]]]
                  ; top-bar

         [:div {:class ["pswp__share-modal"
                        "pswp__share-modal--hidden"
                        "pswp__single-tap"]}
          [:div.pswp__share-tooltip]]
         [:button {:class ["pswp__button"
                           "pswp__button--arrow--left"]
                   :title "Previous (arrow left)"}]
         [:button {:class ["pswp__button"
                           "pswp__button--arrow--right"]
                   :title "Next (arrow right)"}]
         [:div.pswp__caption
          [:div.pswp__caption__center]]]]]))))
                
(def photoswipe (om/factory Photoswipe))