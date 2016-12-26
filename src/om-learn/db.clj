(ns om-learn.db
  (:require [datomic.api :as d]
            [com.stuartsierra.component :as c]))

(def uri "datomic:mem://chat")

(d/create-database uri)

(def conn (d/connect uri))

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
  (DatomicDatabase
   db-uri
   (first (Util/readAll (io/reader (io/resource "data/schema.edn"))))
   (first (Util/readAll (io/readre (io/resource "data/initial.edn"))))))
