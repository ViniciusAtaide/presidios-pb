(ns om-learn.layout
  (:require
    [om.dom :as dom]
    [om.next :as om :refer-macros [defui]]))

(defui ^:once Navbar
  static om/IQuery
  (query [this]
    [:active])
  Object
  (render [this]
    (let [{:keys [current-user active]} (om/props this)]
      (dom/div #js {:className "ui three item menu"}
               (println current-user active)
               (map-indexed
                 (fn [i val]
                   (dom/a #js {:key i :className (str "item " (if (= active val) "active" ""))} (str val " " current-user)))
                 ["Menu 1" "Menu 2" "Menu 3"])))))

(def ui-navbar (om/factory Navbar))

(defui ^:once Layout
  static om/IQuery
  (query [this]
    [{:navbar (om/get-query Navbar)}])
  Object
  (render [this]
    (let [{:keys [factory props]} (om/get-computed this)
          {:keys [navbar]} (om/props this)]
      (dom/div #js {:className "ui grid container"}
               (dom/div #js {:className "row"}
                        (println navbar)
                        (ui-navbar navbar)
                        (dom/div #js {:className "row"}
                                 (dom/div #js {:className "column"}
                                          (dom/h1 #js {:style #js {:textAlign "center"}} "Controle de Apreensões")
                                          (factory props))))))))

