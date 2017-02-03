(ns om-learn.app
  (:require [om.next :as om]
            [goog.dom :as gdom]
            [om.dom :as dom]))

(def app-state (atom {:counter/count 0}))

(def parser (om/parser {:read read :mutate mutate}))

(defmulti read (fn [env key params] key))

(defmethod read :default
  [{:keys [state]} key params]
  (let [st @state]
    (if-let [[_ value] (find st key)]
      {:value value}
      {:value :not-found})))

(defmethod read :counter/count
  [{:keys [state]} key params]
  {:value (:count @state)})

(defui App
  static om/IQuery
  (query [this]
    '[{:keys [:counter/count]} (om/props this)])
  Object
  (render [this]
    (let [{:keys [counter/count]} (om/props this)]
            (dom/div nil
                     (dom/h2 nil "Hello World")
                     (dom/p nil count)))))

(def reconciler
  (om/reconciler {:state  app-state
                  :parser parser}))

(enable-console-print!)
(when-let [section (. js/document (.getElementById "app"))]
  (om/add-root! reconciler App section))