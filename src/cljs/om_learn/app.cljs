(ns om-learn.app
  (:require [om.next :as om :refer-macros [defui]]
            [goog.dom :as gdom]
            [om.dom :as dom]
            [compassus.core :as compassus]
            [bidi.bidi :as bidi]
            [pushy.core :as pushy]
            [om-learn.routes :refer [routes bidi-routes]]
            [om-learn.layout :refer [Layout]]))

(defonce app-state (atom {:counter/count 0}))

(declare app)

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

(defmethod read :default
  [{:keys [state]} key params]
  (let [st @state]
    (if-let [[_ value] (find st key)]
      {:value value}
      {:value :not-found})))

(def parser (compassus/parser {:read read :mutate mutate}))

(defonce reconciler
  (om/reconciler {:state  app-state
                  :parser parser}))

(defn update-route!
  [{:keys [handler] :as route}]
  (let [current-route (compassus/current-route app)]
    (when (not= handler current-route)
      (compassus/set-route! app handler))))


(def history
  (pushy/pushy update-route!
               (partial bidi/match-route bidi-routes)))

(def app (compassus/application {:routes routes
                                 :index-route :index
                                 :reconciler reconciler
                                 :mixins [(compassus/wrap-render Layout)
                                          (compassus/did-mount (fn [_]
                                                                 (pushy/start! history)))
                                          (compassus/will-unmount (fn [_]
                                                                   (pushy/stop! history)))]}))

(compassus/mount! app (gdom/getElement "app"))

(enable-console-print!)
