(set-env!
 :source-paths #{"src"}
 :resource-paths #{"resources"}
 :dependencies '[[org.clojure/clojure "1.9.0-alpha14"]
                 [org.clojure/clojurescript "1.9.293"]
                 [org.clojure/tools.nrepl "0.2.12"]

                 [org.clojure/core.async "0.2.395"]
                 [org.clojure/test.generative "0.5.2"]
                 [com.datomic/datomic-free "0.9.5544"]
                 [com.stuartsierra/component "0.3.2"]


                 [aero "1.0.1"]
                 [adzerk/boot-cljs "1.7.228-2" :scope "test"]
                 [adzerk/boot-cljs-repl "0.3.3" :scope "test"]
                 [adzerk/boot-reload "0.4.13" :scope "test"]

                 [reloaded.repl "0.2.3"]
                 [com.cemerick/piggieback "0.2.2-SNAPSHOT" :scope "test"]
                 [weasel "0.7.0" :scope "test"]

                 [org.omcljs/om "1.0.0-alpha47"]

                 [yada "1.2.0"]
                 [bidi "2.0.16"]
                 [aleph "0.4.1"]])

(require '[adzerk.boot-cljs :refer [cljs]]
         '[adzerk.boot-cljs-repl :refer [cljs-repl start-repl]]
         '[adzerk.boot-reload :refer [reload]]
         '[com.stuartsierra.component :as component]
         '[reloaded.repl :refer [go]]
         'clojure.tools.namespace.repl
         '[om-learn.system :refer [new-system]])

(def repl-port 5600)

(def project "om-learn")

(task-options!
 repl {:client true
       :port    repl-port}
 aot {:namespace #{'om-learn.main}})

(deftask dev-system
  "criar um sistema dev"
  []
  (try
    (require 'user)
    (go)
    (catch Exception e
      (boot.util/fail "Exceção Lançada")
      (boot.util/print-ex e)))
  identity)

(deftask dev
  "ponto de entrada dev"
  []
  (set-env! :source-paths #(conj % "dev"))

  (apply clojure.tools.namespace.repl/set-refresh-dirs (get-env :directories))

  (comp
   (watch)
   (speak)
   (reload :on-jsload 'om-learn.main/init)
   (cljs-repl :nrepl-opts {:client  false
                           :port    repl-port
                           :init-ns 'user})
   (cljs :ids #{"om-learn"} :optimizations :none)
   (dev-system)
   (target)))

(defn- run-system [profile]
  (println "Abrindo o sistema com o profile " profile)
  (let [system (new-system profile)]
    (component/start system)
    (intern 'user 'system system)
    (with-pre-wrap fileset
      (assoc fileset :system system))))

(deftask run [p profile VAL kw "Profile"]
  (comp
    (repl :server true
      :port (case profile :prod 5601 :beta 5602 5600)
      :init-ns 'user)
    (run-system (or profile :prod))
    (wait)))

(def environment-name (str project "-prod"))
