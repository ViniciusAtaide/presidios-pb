(ns om-learn.handler
  (:require [hiccup.page :refer [html5 include-js include-css]]
            [yada.resources.classpath-resource :refer [new-classpath-resource]]
            [yada.yada :refer [resource] :as y]
            [yada.redirect :refer [redirect]]
            [datomic.api :as d]))

(def main-page
  [:body {}
   [:div#app.ui.grid]
   (include-js "static/js/app.js")])

(def login-page
  [:body {}
   [:h1.ui.center.aligned.header {} "Login"]
   [:form.ui.one.column.centered.grid.container {}
    [:div.column.row
     [:div.ui.big.input
      [:input {:type "text" :placeholder "Login" :name "login"}]]]
    [:div.column.row
     [:div.ui.big.input
      [:input {:type "password" :placeholder "Senha" :name "senha"}]]]]])

(defn layout [content]
  (y/resource
   {:access-control
    {:realm "account"
     :scheme "basic"
     :verify (fn [ctx] (println ctx))}
    :methods
    {:get
     {:produces #{"text/html"}
      :response (fn [ctx]
                  (println "cheguei")
                  (html5 {:lang "pt-br"}
                         [:head [:title "Presidio PB"]
                          (include-css "static/css/semantic.min.css")]
                         [:link {:rel "icon" :type "image/png" :href "static/favicon.ico"}]
                         content))}}}))

(def route ["/" {""  (layout main-page)
                 "login" (layout login-page)
                 "static" (new-classpath-resource "static")}])

(defn handler [{:keys [uri]}]
  (println (y/handler route {:uri uri}))
  (y/handler route {:uri uri}))
