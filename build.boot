(set-env!
 :source-paths #{"src/clj" "src/cljs"}
 :resource-paths #{"resources"}
 :dependencies  '[[org.clojure/clojurescript "1.9.473"
                   :exclusions [org.clojure/clojure
                                com.google.guava/guava]]

                  [com.datomic/datomic-free "0.9.5554"
                   :exclusions [io.netty/netty-all]]
                  [org.clojure/tools.namespace "0.3.0-alpha3"
                   :exclusions [org.clojure/tools.reader]]
                  [org.clojure/tools.nrepl "0.2.12" :scope "test"]
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

                  [http-kit "2.2.0"]
                  [bidi "2.0.16"
                   :exclusions [ring/ring-core]]
                  [yada "1.2.1"
                   :exclusions [aleph
                                manifold
                                ring-swagger
                                prismatic/schema
                                org.mozilla/rhino
                                com.google.guava/guava
                                com.google.code.findbugs/jsr305
                                com.cognitect/transit-java
                                com.cognitect/transit-clj
                                commons-codec]]

                  [aleph "0.4.2-alpha8"]

                  [binaryage/devtools "0.9.1" :scope "test"]

                  [weasel "0.7.0" :scope "test"
                   :exclusions [http-kit com.google.guava/guava
                                com.google.javascript/closure-compiler-externs
                                org.clojure/tools.reader]]

                  [org.omcljs/om "1.0.0-alpha47"
                   :exclusions [commons-codec
                                com.fasterxml.jackson.core/jackson-core]]
                  [kibu/pushy "0.3.6"]
                  [compassus "1.0.0-alpha2"]]

 :exclusions ['org.clojure/clojure
              'org.clojure/clojurescript])

(require '[adzerk.boot-cljs :refer [cljs]]
         '[adzerk.boot-cljs-repl :refer [cljs-repl start-repl]]
         '[adzerk.boot-reload :refer [reload]]
         '[om-learn.system :refer [dev-system]]
         '[environ.core :refer [env]]
         '[environ.boot :refer [environ]]
         '[system.boot :refer [system run]])
(deftask cider []
  (require 'boot.repl)
  (swap! @(resolve 'boot.repl/*default-dependencies*)
         concat '[[org.clojure/tools.nrepl "0.2.12"]
                  [cider/cider-nrepl "0.15.1-SNAPSHOT"]
                  [refactor-nrepl "2.4.0-SNAPSHOT"]])
  (swap! @(resolve 'boot.repl/*default-middleware*)
         concat '[cider.nrepl/cider-middleware
                  refactor-nrepl.middleware/wrap-refactor])
  identity)

(deftask dev []
  (comp
   (environ :env {:http-port "3000"
                  :db-uri    "datomic:mem://chat"})
   (watch)
   (system :sys #'dev-system :auto true :files ["handler.clj"])
   (reload)
   (cljs-repl :ids #{"static/js/app"})
   (cljs :ids #{"static/js/app"} :nrepl-opts {:port 3001})
   (target)))
