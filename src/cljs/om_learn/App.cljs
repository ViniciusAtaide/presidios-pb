(ns om-learn.app
  (:require [om.next :as om :refer-macros [defui]]
            [om-learn.routes.index :refer [index]]
            [goog.dom :as gdom]
            [om.dom :as dom]))

(defonce app-state (atom {:counter/count 0}))

(defmulti read om/dispatch)
(defmulti mutate om/dispatch)

(defmethod mutate 'increment
  [{:keys [state]} _ _]
  {:action
   #(swap! state update-in [:counter/count] inc)})

(defmethod mutate 'decrement
  [{:keys [state]} _ _]
  {:action
   #(swap! state update-in [:counter/count] dec)})

(defmethod read :counter/count
  [{:keys [state]} key params]
  {:value (:counter/count @state)})

(def parser (om/parser {:read read :mutate mutate}))

(defui ^:once App
  static om/IQuery
  (query [this]
    '[:counter/count])
  Object
  (render [this]
    (let [{:keys [counter/count]} (om/props this)]
      (dom/div nil
               (dom/p nil count)
               (index)
               (dom/button
                 #js{:onClick #(om/transact! this '[(increment)])}
                 "Incrementa")
               (dom/button
                 #js{:onClick #(om/transact! this '[(decrement)])}
                 "Decrementa")))))

(defonce reconciler
         (om/reconciler {:state  app-state
                         :parser parser}))

(enable-console-print!)

(om/add-root! reconciler App (gdom/getElement "app"))
