(ns om-learn.db
  (:require [datomic.api :as d]
            [com.stuartsierra.component :refer [Lifecycle]]))

(defrecord DatabaseRecord [uri conn]
  Lifecycle
  (start [component]
    (let [conn (d/connect uri)]
      (assoc component :conn conn)))
  (stop [component]
    (when conn (d/release conn))
    (assoc component :conn nil)))

(defn new-db [uri]
  (map->DatabaseRecord {:uri uri}))
