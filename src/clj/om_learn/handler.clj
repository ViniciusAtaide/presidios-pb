(ns om-learn.handler
  (:require [hiccup.page :refer [html5 include-js]]
            [bidi.ring :refer [make-handler]]
            [ring.util.response :refer [response]]
            [ring.middleware.resource :refer [wrap-resource]]
            [ring.middleware.content-type :refer [wrap-content-type]]
            [ring.middleware.not-modified :refer [wrap-not-modified]]))

(def main-page
  (html5 {:lang "pt-br"}
         [:head [:title "Presidio PB"]]
         [:body {}
          [:div#app]
          (include-js "js/app.js")]))

(def handler
  (-> (make-handler ["/" {"" (fn [req] (response main-page))}])
      (wrap-resource "js")))
