(ns om-learn.system
  (:require [system.core :refer [defsystem]]
            (system.components
             [datomic :refer [new-datomic-db]]
             [http-kit :refer [new-web-server]])
            [environ.core :refer [env]]
            [om-learn.server :refer [handler]]
            [com.stuartsierra.component :as component]))

(defsystem dev-system
  [:db (new-datomic-db (env :db-uri))
   :web (component/using
            (new-web-server (.Integer (env :port)) handler)
            [:db])])
