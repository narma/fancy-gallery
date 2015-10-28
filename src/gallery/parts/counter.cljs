(ns gallery.parts.counter
  (:require [om.next :as om :refer-macros [defui]]
            [om.dom :as dom]
            [datascript.core :as d]
            [gallery.state :refer [read mutate]]))


(defmethod read :app/counter
  [{:keys [state selector]} _ _]
  {:value (d/q '[:find [(pull ?e ?selector) ...]
                 :in $ ?selector
                 :where [?e :app/title]]
            (d/db state) selector)})


(defmethod mutate 'app/increment
  [{:keys [state]} _ entity]
  {:value [:app/counter]
   :action (fn [] (d/transact! state
                    [(update-in entity [:app/count] inc)]))})

(defui Counter
  static om/IQuery
  (query [this]
    [{:app/counter [:db/id :app/title :app/count]}])
  Object
  (render [this]
    (let [{:keys [app/title app/count] :as entity}
          (get-in (om/props this) [:app/counter 0])]
      (dom/div nil
        (dom/h2 nil title)
        (dom/span nil (str "Count!: " count))
        (dom/button
          #js {:onClick
               (fn [e]
                 (om/transact! this
                   `[(app/increment ~entity)]))}
          "Click me!")))))
