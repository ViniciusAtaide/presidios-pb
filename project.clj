(defproject
  boot-project
  "0.0.0-SNAPSHOT"
  :dependencies
  [[org.clojure/clojure "1.8.0"]
   [org.clojure/clojurescript "1.9.293"]
   [org.clojure/tools.nrepl "0.2.12"]
   [org.clojure/core.async "0.2.395"]
   [org.clojure/test.generative "0.5.2"]
   [com.datomic/datomic-free "0.9.5544"]
   [com.stuartsierra/component "0.3.2"]
   [aero "1.0.2"]
   [adzerk/boot-cljs "1.7.228-2" :scope "test"]
   [adzerk/boot-cljs-repl "0.3.3" :scope "test"]
   [adzerk/boot-reload "0.5.0" :scope "test"]
   [reloaded.repl "0.2.3"]
   [com.cemerick/piggieback "0.2.1" :scope "test"]
   [weasel "0.7.0" :scope "test"]
   [org.omcljs/om "1.0.0-alpha47"]
   [yada "1.1.0"]
   [bidi "2.0.16"]
   [aleph "0.4.1"]
   [boot/core "2.6.0" :scope "compile"]
   [org.danielsz/system "0.3.2-SNAPSHOT"]
   [org.danielsz/system "0.3.1"]
   [org.danielsz/system "0.3.1"]
   [environ/environ.core "0.3.1"]]
  :repositories
  [["clojars" {:url "https://repo.clojars.org/"}]
   ["maven-central" {:url "https://repo1.maven.org/maven2"}]]
  :source-paths
  ["src" "resources"])
