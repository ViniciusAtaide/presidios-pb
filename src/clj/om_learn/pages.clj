(ns om-learn.pages
  (:require [yada.yada :as y]
            [datomic.api :as d]
            [hiccup.page :refer [html5 include-js include-css]]
            [yada.redirect :refer [redirect]]
            [yada.yada :as yada]
            [buddy.sign.jwt :as jwt]
            [clojure.edn :as edn]
            [yada.security :refer [verify]])
  (:import (java.util UUID)))

(defn layout [content]
  (html5 {:lang "pt-br"}
         [:head [:title "Presidio PB"]]
         [:link {:rel "icon" :type "image/png" :href "static/favicon.ico"}]
         (include-css "static/css/semantic.min.css")
         content))

(defn main-page [ctx]
  (layout
    [:body
     [:div#app]
     (include-js "static/js/app.js")]))
