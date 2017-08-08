(ns om-learn.system
  (:require [system.core :refer [defsystem]]
            (system.components
             [aleph :refer [new-web-server]]
             [datomic :refer [new-datomic-db]])
            [environ.core :refer [env]]
            [ring.middleware.session :refer [wrap-session]]
            [om-learn.handler :refer [handler]]
            [com.stuartsierra.component :as component]))

(defsystem dev-system
  [:db (new-datomic-db (env :db-uri))
   :web (component/using
         (new-web-server (read-string (env :http-port))
                         handler)
         [:db])])
