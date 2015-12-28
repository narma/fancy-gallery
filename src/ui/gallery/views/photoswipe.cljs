(ns gallery.views.photoswipe
  (:require [om.next :as om :refer-macros [defui]]
            [sablono.core :as html :refer-macros [html]]
            [gallery.util.log :refer [log]]
            [gallery.state :refer [read mutate]]))
            
(defn map-photo 
  [{:keys [name w h]}]
  {:src (str "/photos/2000/" name)
   :rawsrc (str "/photos/" name)
   :w w
   :h h})
   
(def !empty? (complement empty?))

(defmethod mutate 'photoswipe/onclose
 [{:keys [state]} _ _]
 {:action
  (fn []
   (swap! state dissoc :photoswipe))})

(defui Photoswipe
  static om/IQuery
  (query [this]
   '[:photos :goto :is-open])
   
  Object
  (initLocalState [_]
    {:is-open false})
  
  (handleClose [this]
   (om/update-state! this :is-open (constantly false))
   (om/transact! this `[(photoswipe/onclose)])
   (when-let [on-close (:on-close (om/props this))]
     (on-close)))
    
  (showGallery [this items index]
   (when (!empty? items)
     (om/update-state! this :is-open (constantly true))
     (let [node (js/ReactDOM.findDOMNode this)
           pw (js/PhotoSwipe.
               node
               js/PhotoSwipeUI_Default
               (clj->js items)
               (clj->js {:index index
                         :getImageURLForShare #(.. (aget this :photoswipe)
                                                -currItem -rawsrc)}))]
          
       (aset this :photoswipe pw)
       (.listen pw "close" #(.handleClose this))
      (.init pw))))
      
  (goTo [this index] 
    (when-let [pw (aget this :photoswipe)]
     (when-not (nil? index)
       (.goTo pw index))))
                   
 (componentWillReceiveProps 
  [this next-props]
  (let [{:keys [photos goto]} next-props
        items (map map-photo photos)
        state (om/get-state this)
        pw (aget this :photoswipe)]
   (if (:is-open next-props)
     (if-not (:is-open state)
       (.showGallery this items goto)
       (do 
        (set! (.. pw -items -length) 0)
        (doseq [i items]
         (.push (.-items pw) i))
        (.invalidateCurrItems pw)
        (.updateSize pw true)
        (.goTo this goto)))
       
    (when (:is-open state)
      (.close pw)))))
      
 (componentWillUnmount 
  [this]
  (when-let [pw (aget this :photoswipe)]
    (.close pw)))
                     
 (render 
  [this]
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
       [:div.pswp__caption__center]]]]])))
                
(def photoswipe (om/factory Photoswipe))