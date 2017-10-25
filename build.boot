(set-env!
 :source-paths #{"src/cljs" "src/clj"}
 :resource-paths #{"resources"}
 :dependencies
 '[[org.clojure/clojure "1.9.0-alpha20"
    :exclusions
    [org.clojure/clojurescript]]
   [org.clojure/clojurescript "1.9.908"
    :exclusions
    [org.clojure/clojure]]
   [com.datomic/datomic-free "0.9.5561.56"
    :exclusions
    [org.clojure/clojure org.clojure/clojurescript io.netty/netty-all]]

   [com.google.guava/guava "21.0"]
   [org.clojure/tools.namespace "0.3.0-alpha4"
    :exclusions
    [org.clojure/clojure
     org.clojure/clojurescript
     org.clojure/tools.reader]]
   [org.clojure/tools.nrepl "0.2.13"
    :scope
    "test"
    :exclusions
    [org.clojure/clojure org.clojure/clojurescript]]
   [org.clojure/core.async "0.3.443"
    :exclusions
    [org.clojure/clojure
     org.clojure/clojurescript
     org.clojure/tools.reader]]
   [com.stuartsierra/component "0.3.2"
    :exclusions
    [org.clojure/clojure org.clojure/clojurescript]]
   [environ "1.1.0"
    :exclusions
    [org.clojure/clojure org.clojure/clojurescript]]
   [boot-environ "1.1.0"
    :exclusions
    [org.clojure/clojure org.clojure/clojurescript]]
   [adzerk/boot-cljs-repl "0.4.0-SNAPSHOT"
    :scope
    "test"
    :exclusions
    [org.clojure/clojure org.clojure/clojurescript]]
   [adzerk/boot-cljs "2.1.4-SNAPSHOT"
    :scope
    "test"
    :exclusions
    [org.clojure/clojure org.clojure/clojurescript]]
   [adzerk/boot-reload "0.5.2"
    :scope
    "test"
    :exclusions
    [org.clojure/clojure org.clojure/clojurescript]]
   [org.danielsz/system "0.4.1-SNAPSHOT"
    :exclusions
    [org.clojure/clojure
     org.clojure/tools.analyzer.jvm
     org.clojure/tools.namespace
     org.clojure/clojurescript
     com.stuartsierra/component
     org.clojure/tools.reader
     org.clojure/core.async]]
   [reloaded.repl "0.2.3"
    :exclusions
    [org.clojure/clojure
     org.clojure/clojurescript
     com.stuartsierra/component
     org.clojure/tools.namespace
     com.stuartsierra/dependency]]
   [com.cemerick/piggieback "0.2.2-SNAPSHOT"
    :scope
    "test"
    :exclusions
    [org.clojure/clojure
     org.clojure/clojurescript
     com.google.javascript/closure-compiler-externs
     com.google.guava/guava
     org.clojure/tools.reader]]
   [yada/aleph "1.2.8"
    :exclusions
    [org.clojure/clojure
     riddley
     org.clojure/clojurescript
     manifold
     hiccup
     commons-codec
     org.clojure/tools.reader]]
   [manifold "0.1.7-alpha5"
    :exclusions
    [org.clojure/clojure org.clojure/clojurescript]]
   [hiccup "2.0.0-alpha1"
    :exclusions
    [org.clojure/clojure org.clojure/clojurescript]]
   [bidi "2.1.2"
    :exclusions
    [org.clojure/clojure org.clojure/clojurescript ring/ring-core]]
   [yada/lean "1.2.8"
    :exclusions
    [com.cognitect/transit-java
     bidi
     yada/aleph
     org.clojure/clojure
     clj-time
     riddley
     commons-fileupload
     ring-swagger
     org.clojure/clojurescript
     org.mozilla/rhino
     commons-codec
     com.google.guava/guava
     com.cognitect/transit-clj
     yada/core
     prismatic/schema
     manifold
     joda-time
     com.google.code.findbugs/jsr305
     ring/ring-core
     aleph]]
   [yada/jwt "1.2.8"
    :exclusions
    [org.clojure/clojure
     riddley
     org.clojure/clojurescript
     io.aleph/dirigiste
     commons-codec
     yada/core
     manifold]]
   [cljsjs/jquery "3.2.1-0"
    :exclusions
    [org.clojure/clojure org.clojure/clojurescript]]
   [binaryage/devtools "0.9.4"
    :scope
    "test"
    :exclusions
    [org.clojure/clojure org.clojure/clojurescript]]
   [weasel "0.7.0"
    :scope
    "test"
    :exclusions
    [org.clojure/clojure
     org.clojure/clojurescript
     com.google.javascript/closure-compiler-externs
     com.google.guava/guava
     org.clojure/tools.reader]]
   [org.omcljs/om "1.0.0-beta1"
    :exclusions
    [org.clojure/clojure
     org.clojure/clojurescript
     com.fasterxml.jackson.core/jackson-core
     commons-codec]]
   [fulcrologic/fulcro "1.0.0"]
   [kibu/pushy "0.3.8"
    :exclusions
    [org.clojure/clojure org.clojure/clojurescript]]
   [compassus "1.0.0-alpha3"
    :exclusions
    [org.clojure/clojure org.clojure/clojurescript]]]

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

(deftask proto []
  (require 'boot.repl)
  (swap! @(resolve 'boot.repl/*default-dependencies*)
         concat '[[proto-repl "0.3.1"]])
  identity)

(deftask dev []
  (task-options! repl {:port 3002})
  (comp
   (environ :env {:port   "3000"
                  :db-uri "datomic:mem://chat"})
   (watch)
   (system :sys #'dev-system :auto true :files ["system.clj"])
   (cljs-repl :ids #{"static/js/app"})
   (reload :on-jsload 'om-learn.app/init!)
   (speak)
   (cljs :source-map true
         :compiler-options {:parallel-build true}
         :ids #{"static/js/app"})
   (target)))
