(set-env!
 :source-paths #{"src/clj" "src/cljs"}
 :resource-paths #{"resources"}
 :dependencies '[[org.clojure/clojure "1.9.0-alpha14"]
                 [org.clojure/clojurescript "1.9.293"
                  :exclusions [org.clojure/clojure]]
                 [compojure "1.5.2"]
                 [com.datomic/datomic-free "0.9.5554"]
                 [org.clojure/tools.nrepl "0.2.12"]
                 [org.clojure/tools.namespace "0.3.0-alpha3"
                  :exclusions [org.clojure/tools.reader]]

                 [org.clojure/core.async "0.2.395"
                  :exclusions [org.clojure/tools.reader]]
                 [org.clojure/test.generative "0.5.2"
                  :exclusions [org.clojure/tools.namespace]]

                 [com.stuartsierra/component "0.3.2"]

                 [hiccup "1.0.5"]
                 [environ "1.1.0"]
                 [boot-environ "1.1.0"]

                 [adzerk/boot-cljs-repl "0.3.3"]
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
                 [weasel "0.7.0" :scope "test"
                  :exclusions [http-kit com.google.guava/guava
                               com.google.javascript/closure-compiler-externs
                               org.clojure/tools.reader]]

                 [org.omcljs/om "1.0.0-alpha47"]
                 [http-kit "2.3.0-alpha1"]

                 [javax.servlet/servlet-api "2.5"]
                 [ring/ring-core "1.5.1"]
                 [ring/ring-jetty-adapter "1.5.1"]

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

`(set-env!
  :repositories #(conj % ["my.datomic.com" {:url "https://my.datomic.com/repo"
                                            :username (env :datomic-username)
                                            :password (env :datomic-password)}]))

`(set-env!
  :dependencies
  #(conj % '[com.datomic/datomic-pro "0.9.5554"
             :exclusions [commons-codec
                          com.google.guava/guava]]))

(require '[om-learn.system :as system])

(boot.lein/generate)

(deftask dev []
  (comp
    (environ :env {:http-port "3000"
                   :db-uri "datomic:mem://chat"})
    (watch)
    (notify :visual true :title "CLJS")
    (system :sys #'dev-system :auto true :files ["server.clj"])
    (reload)
    (cljs-repl)
    (cljs :source-map true
          :compiler-options {:parallel-build true
                             :compiler-stats true})
    (target)))
