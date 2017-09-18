(ns om-learn.layout
  (:require
   [om.dom :as dom]
   [om.next :as om :refer-macros [defui]]))

(defui ^:once Navbar
  static om/IQuery
  (query [this]
         [:navbar/current-user])
  Object
  (render [this]
          (let [{:keys [current-user]} (om/props this)]
            (println current-user "!")
            (dom/div #js {:className "ui three item menu"}
                     (map-indexed
                      (fn [i val]
                        (dom/a #js {:key i :className (str "item " (if false "active" ""))} (str val)))
                      ["Menu 1" "Menu 2" "Menu 3"])))))

(def ui-navbar (om/factory Navbar))

(defui ^:once Layout
  static om/IQuery
  (query [this]
         [{:navbar (om/get-query Navbar)}])
  Object
  (render [this]
          (let [{:keys [factory props]} (om/get-computed this)]
            (dom/div #js {:className "ui grid container"}
                     (dom/div #js {:className "row"}
                              (ui-navbar)
                              (dom/div #js {:className "row"}
                                       (dom/div #js {:className "column"}
                                                (dom/h1 #js {:style #js {:textAlign "center"}} "Controle de Apreensões")
                                                (factory props))))))))

