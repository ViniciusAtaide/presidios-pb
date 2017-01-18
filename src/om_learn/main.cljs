(ns om-learn.main
  (:require [om-learn.app :as om-learn]))

(defn init []
  (enable-console-print!
   (when-let [section (. js/document (getElementById "app"))]
     (om-learn/init section))))
