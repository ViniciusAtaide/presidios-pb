(ns user
  (:require [com.stuartsierra.component :as component]
            [om-learn.system :as system]))

(defn new-dev-system
  "criar um sistema de desenvolvimento"
  []
  (let [config (system/config :dev)]
    (system/configure-components
     (component/system-using
      (system/new-system-map config)
      (system/new-dependency-map))
     config)))


(reloaded.repl/set-init! new-dev-system)

(defn cljs-repl
  "abre um repl clojurescript"
  []
  (eval
   '(do (in-ns 'boot.user)
        (start-repl))))
