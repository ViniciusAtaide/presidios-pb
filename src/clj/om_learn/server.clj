(ns om-learn.server
    (:require [hiccup.page :refer [html5 include-js]]
              [ring/util :refer [response]]))

(def main-page
  (html5 {:lang "pt-br"}
         [:head [:title "teste"]]
         [:body {}
          [:h1 {} "teste"]
          [:div#app]
          (include-js "/js/app.js")]))

(defn handler [{:keys [uri]} :as req]
  (if (= uri "/")
    (response main-page)))
    
