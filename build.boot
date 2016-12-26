(set-env!
 :resources-path #{"src", "resources"}
 :dependencies '[[com.datomic/datomic-free "0.9.5544"]
                 [org.clojure/core.async "0.2.395"]
                 [com.stuartsierra/component "0.3.2"]
                 [org.clojure/test.generative "0.5.2"]])
