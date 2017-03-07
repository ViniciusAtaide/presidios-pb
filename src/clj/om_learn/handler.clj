(ns om-learn.handler
  (:require [hiccup.page :refer [html5 include-js]]
            [yada.resources.classpath-resource :refer [new-classpath-resource]]
            [yada.yada :as yada]))

(def main-page
  (html5 {:lang "pt-br"}
         [:head [:title "Presidio PB"]]
         [:link {:rel "icon" :type "image/png" :href "static/favicon.ico"}]
         [:body {}
          [:div#app]
          (include-js "static/js/app.js")]))


(def handler
  ["/" {"" (yada/handler
            (yada/resource
             {:methods
              {:get
               {:produces "text/html"
                :response main-page}}}))
        "static" (new-classpath-resource "static")}])
