(ns om-learn.routes
  (:require [om-learn.routes.index :refer [Index]]))

(def bidi-routes
  ["/" {"" :index}])

(def routes
  {:index Index})
