(set-env!
  :source-paths #{"src/clj" "src/cljs"}
  :resource-paths #{"resources"}
  :dependencies '[[org.clojure/clojure "1.9.0-alpha14"]
                  [org.clojure/clojurescript "1.9.473"
                   :exclusions [org.clojure/clojure]]

                  [com.datomic/datomic-free "0.9.5554"]
                  [org.clojure/tools.nrepl "0.2.12"]
                  [org.clojure/tools.namespace "0.3.0-alpha3"
                   :exclusions [org.clojure/tools.reader]]

                  [org.clojure/core.async "0.2.395"
                   :exclusions [org.clojure/tools.reader]]
                  [org.clojure/test.generative "0.5.2"
                   :exclusions [org.clojure/tools.namespace]]

                  [com.stuartsierra/component "0.3.2"]

                  [environ "1.1.0"]
                  [boot-environ "1.1.0"]

                  [adzerk/boot-cljs-repl "0.3.3" :scope "test"]
                  [adzerk/boot-cljs "2.0.0-SNAPSHOT" :scope "test"]
                  [adzerk/boot-reload "0.5.0" :scope "test"]

                  [org.danielsz/system "0.3.2-SNAPSHOT"
                   :exclusions [com.stuartsierra/component
                                org.clojure/tools.reader]]

                  [reloaded.repl "0.2.3"
                   :exclusions [com.stuartsierra/component
                                com.stuartsierra/dependency
                                org.clojure/tools.namespace]]

                  [com.cemerick/piggieback "0.2.2-SNAPSHOT" :scope "test"
                   :exclusions [com.google.guava/guava
                                com.google.javascript/closure-compiler-externs
                                org.clojure/tools.reader]]

                  [hiccup "1.0.5"]
                  [javax.servlet/servlet-api "2.5"]
                  [ring/ring "1.5.1"]
                  [http-kit "2.2.0"]
                  [bidi "2.0.16"]

                  [binaryage/devtools "0.9.1" :scope "test"]

                  [weasel "0.7.0" :scope "test"
                   :exclusions [http-kit com.google.guava/guava
                                com.google.javascript/closure-compiler-externs
                                org.clojure/tools.reader]]

                  [org.omcljs/om "1.0.0-alpha47"]

                  [onetom/boot-lein-generate "0.1.3" :scope "test"]]

  :exclusions ['org.clojure/clojure
               'org.clojure/clojurescript])

(require '[adzerk.boot-cljs :refer [cljs]]
         '[adzerk.boot-cljs-repl :refer [cljs-repl start-repl]]
         '[adzerk.boot-reload :refer [reload]]
         '[om-learn.system :refer [dev-system]]
         '[environ.core :refer [env]]
         '[environ.boot :refer [environ]]
         '[system.boot :refer [system run]]
         'boot.lein)

(boot.lein/generate)

(deftask dev []
         (comp
           (environ :env {:http-port "3000"
                          :db-uri    "datomic:mem://chat"})
           (watch)
           (speak)
           (system :sys #'dev-system :auto true :files ["handler.clj"])
           (reload)
           (cljs-repl :nrepl-opts {:port 9009} :ids #{"js/app"})
           (cljs)
           (target)))
