(ns om-learn.system
  (:require [system.core :refer [defsystem]]
            (system.components
             [datomic :refer [new-datomic-db]])
            [environ.core :refer [env]]
            [ring.middleware.session :refer [wrap-session]]
            [om-learn.routes :refer [routes]]
            [yada.yada :as yada]
            [com.stuartsierra.component :as component]))

(defrecord WebServer [port db]
  component/Lifecycle
  (start [component]
    (let [listener (yada/listener (routes db) {:port (read-string port)})]
      (assoc component :listener listener)))

  (stop [component]
    (when-let [close (get-in component [:listener :close])]
      (close))
    (assoc component :listener nil)))

(defn dev-system []
  (component/system-map
   :db (new-datomic-db (env :db-uri))
   :web (component/using
         (map->WebServer {:port (env :port)})
         [:db])))
