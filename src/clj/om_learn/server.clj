(ns om-learn.server
  (:require [com.stuartsierra.component :as component]
            [ring.adapter.jetty :refer [run-jetty]]
            [ring.util.response :refer [response]]
            [hiccup.page :refer [html5 include-js] ]))

(def main-page
  (html5 {:lang "pt-br"}
         [:head [:title "teste"]]
         [:body {}
          [:h1 {} "teste"]
          [:div#app]
          (include-js "/js/app.js")]))

(defn handler [req]
  (response main-page))