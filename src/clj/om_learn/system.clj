(ns om-learn.system
  (:require [system.core :refer [defsystem]]
            (system.components
             [aleph :refer [new-web-server]]
             [datomic :refer [new-datomic-db]])
            [environ.core :refer [env]]
            [om-learn.handler :refer [handler]]
            [com.stuartsierra.component :as component]
            [bidi.ring :refer [make-handler]]))

(defsystem dev-system
  [:db (new-datomic-db (env :db-uri))
   :web (component/using
          (new-web-server (read-string (env :http-port))
                          (make-handler handler))
          [:db])])
