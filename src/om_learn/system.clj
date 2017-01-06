(ns om-learn.system
  (:require
   [com.stuartsierra.component :refer [system-map system-using]]
   [om-learn.db :refer [new-database]]
   [aero.core :as aero]
   [clojure.java.io :as io]))

(defn config
  [profile]
  (aero/read-config (io/resource "config.edn") {:profile profile}))

(defn configure-components
  [system config]
  (merge-with merge system config))

(defn new-system-map
  [configs]
  (system-map
    :db (new-database (:db config))))

(defn new-dependency-map
  []
  {})

(defn new-system
  [profile]
  (let [config (config profile)]
    (-> (new-system-map config)
        (configure-components config)
        (system-using (new-dependency-map)))))
