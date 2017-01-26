(ns om-learn.server
  (:require [com.stuartsierra.component :as component]
            [ring.adapter.jetty :refer [run-jetty]]))

(defn handler [req]
  {:status 200
   :headers {"Content-Type" "text/html"}
   :body "Hello"})

(defrecord ServerRecord [port conn]
  component/Lifecycle
  (start [component]
         (let [server (run-jetty handler {:host "localhost" :port port})]))
  (stop [component]
    (when ())))


(defn new-server
  ([port] (map->ServerRecord {:port port})))
