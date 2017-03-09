(ns om-learn.routes.index
  (:require [om.next :as om :refer-macros [defui]]
            [om.dom :as dom]))

(defui ^:once Index
  Object
  (render [this]
          (dom/div nil "index")))
