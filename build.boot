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

                 [environ "1.1.0"]
                 [boot-environ "1.1.0"]

                 [adzerk/boot-cljs-repl "0.3.3"]
                 [adzerk/boot-cljs "1.7.228-2" :scope "test"]
                 [adzerk/boot-reload "0.5.0" :scope "test"]

                 [org.danielsz/system "0.3.1"]

                 [reloaded.repl "0.2.3"]
                 [com.cemerick/piggieback "0.2.1" :scope "test"]
                 [weasel "0.7.0" :scope "test"]

                 [org.omcljs/om "1.0.0-alpha47"]
                 [http-kit "2.2.0"]
                 [ring "1.5.1"]])


(require '[adzerk.boot-cljs :refer [cljs]]
         '[adzerk.boot-cljs-repl :refer [cljs-repl start-repl]]
         '[adzerk.boot-reload :refer [reload]]
         '[om-learn.system :refer [dev-system]]
         '[environ.boot :refer [environ]]
         '[system.boot :refer [system run]])

(deftask dev
  []
  (comp
   (environ :env {:http-port "3000" :db-uri "datomic:mem://chat"})
   (watch :verbose true)
   (system :sys #'dev-system :auto true :files ["server.clj"])
   (reload)
   (cljs-repl)
   (cljs :source-map true)))
