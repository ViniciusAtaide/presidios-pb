(ns om-learn.main
  (:require [om-learn.app :as app]))

(def init []
  (enable-console-print!)
  (when-let [section (. js/document (.getElementById "app"))]
    (app/init section)))