(ns om-learn.layout
  (:require [om.dom :as dom]
            [om.next :as om :refer-macros [defui]]))

(defui ^:once Layout
  Object
  (render [this]
          (let [{:keys [factory props]} (om/props this)]
            (dom/div nil
                     (dom/p nil "layout")
                     (factory props)))))

