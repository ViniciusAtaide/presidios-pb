(ns om-learn.server
  (:require [yada.yada :as yada]))

(defn content-routes []
  ["/" (yada/handler "hello")])
