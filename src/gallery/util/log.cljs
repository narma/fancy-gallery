(ns gallery.util.log)

(defn log
  [& args]
  (js/console.log (pr-str args)))
  
(defn error
 [& args]
 (js/console.error (pr-str args)))  
 
