(ns gallery.state
  (:require [om.next :as om]))


(defmulti read om/dispatch)

(defmulti mutate om/dispatch)
