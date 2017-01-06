(ns om-learn.db
  (:require [clojure.java.io :as io]
            [com.stuartsierra.component :as c]
            [datomic.api :as d]))

(defrecord DatomicDatabase [uri schema initial-data connection]
  c/Lifecycle
  (start [component]
    (d/create-database uri)
    (let [c (d/connect uri)]
      @(d/transact c schema)
      @(d/transact c initial-data)
      (assoc component :connection c)))
  (stop [component]
    (d/delete-database uri)
    (assoc component :connection nil)))

(defn new-database [db-uri]
  (.DatomicDatabase
   db-uri
   (first (datomic.Util/readAll (io/reader (io/resource "schema.edn"))))
   (first (datomic.Util/readAll (io/reader (io/resource "initial-data.edn"))))))
