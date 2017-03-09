(ns om-learn.handler
  (:require [hiccup.page :refer [html5 include-js include-css]]
            [yada.resources.classpath-resource :refer [new-classpath-resource]]
            [yada.yada :as yada]))

(defn main-page []
  [:body {}
   [:div#app.ui.grid]
   (include-js "static/js/app.js")])

(defn login-page []
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
  (yada/resource
   {:methods
    {:get
     {:produces "text/html"
      :response (html5 {:lang "pt-br"}
                       [:head [:title "Presidio PB"]
                        (include-css "static/css/semantic.min.css")]
                       [:link {:rel "icon" :type "image/png" :href "static/favicon.ico"}]
                       content)}}}))

(defn handler []
  ["/" {""       (yada/handler (layout (main-page)))
        "login"  (yada/handler (layout (login-page)))

        "static" (new-classpath-resource "static")}])
