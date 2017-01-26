(ns om-learn.system
  (:require [system.core :refer [defsystem]]
            [environ.core :refer [env]]
            [om-learn.server :refer [handler]]
            [om-learn.db :refer [new-db]]
            [com.stuartsierra.component :as component]))

(defsystem dev-system
  [:db (new-db (env :db-uri))
   :web (component/using
          (new-web-server (read-string (env :http-port)) handler)
          [:db])])
