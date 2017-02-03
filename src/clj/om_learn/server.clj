(ns om-learn.server
  (:require [hiccup.page :refer [html5 include-js]])
  (:use ring.util.response))

(def main-page
  (html5 {:lang "pt-br"}
         [:head [:title "teste"]]
         [:body {}
          [:div#app]
          (include-js "js/app.js")]))

(defn handler [{uri :uri}]
  (if (= uri "/")
    (response main-page)))

