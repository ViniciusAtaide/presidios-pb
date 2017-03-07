(ns om-learn.routes.index
  (:require [om.next :as om :refer-macros [defui]]
            [om.dom :as dom]))

(defui ^:once Index
  static om/IQuery
  (query [this]
         [:counter/count])
  Object
  (render [this]
          (let [{:keys [counter/count]} (om/props this)]
            (dom/div nil (str "index" count)))))

