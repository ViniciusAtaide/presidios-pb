(ns om-learn.routes
  (:require [om-learn.routes.main-routes :refer [Index]]))

(def bidi-routes
  ["/" {"" :index}])

(def routes
  {:index Index})
