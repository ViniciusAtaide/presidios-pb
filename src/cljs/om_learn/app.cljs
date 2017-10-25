(ns om-learn.app
  (:require [om.next :as om :refer-macros [defui]]
            [goog.dom :as gdom]
            [om.dom :as dom]
            [compassus.core :as compassus]
            [bidi.bidi :as bidi]
            [pushy.core :as pushy]
            [om-learn.routes :refer [routes bidi-routes]]
            [om-learn.layout :refer [Layout]]))

(defonce app-state (atom {:navbar
                          {:active "Menu 2" :current-user "vinny"}}))

(declare app)

(defmulti mutate om/dispatch)

(defn read
  [{:keys [state] :as env} key params]
  (let [st @state]
    (if-let [[_ v] (find st key)]
      {:value v}
      {:value :not-found})))

(defmethod mutate 'change-user
  [{:keys [state]} _ {:keys [new-name]}]
  {:action
   #(swap! state update-in [:navbar :current-user] new-name)})

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

(def app (compassus/application {:routes      routes
                                 :index-route :index
                                 :reconciler  reconciler
                                 :mixins      [(compassus/wrap-render Layout)
                                               (compassus/did-mount (fn [_]
                                                                      (pushy/start! history)))
                                               (compassus/will-unmount (fn [_]
                                                                         (pushy/stop! history)))]}))

(defonce mounted? (atom false))

(defn init! []
  (enable-console-print!)
  (if-not @mounted?
    (do
      (compassus/mount! app (gdom/getElement "app"))
      (swap! mounted? not))
    (let [route->component (-> app :config :route->component)
          c (om/class->any (compassus/get-reconciler app) (get route->component (compassus/current-route app)))]
      (.forceUpdate c))))
