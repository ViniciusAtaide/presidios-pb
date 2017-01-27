(ns om-learn.db
  (:require [datomic.api :as d :refer [connect release]]
            [com.stuartsierra.component :refer [Lifecycle]]))

(defrecord DatabaseRecord [uri conn]
  Lifecycle
  (start [component]
    (let [conn (connect uri)]
      (assoc component :conn conn)))
  (stop [component]
    (when conn (release conn))
    (assoc component :conn nil)))

(defn new-db [uri]
  (map->DatabaseRecord {:uri uri}))
