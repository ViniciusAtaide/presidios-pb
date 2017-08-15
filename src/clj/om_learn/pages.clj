(ns om-learn.pages
  (:require [yada.yada :as y]
            [datomic.api :as d]
            [hiccup.page :refer [html5 include-js include-css]]
            [yada.redirect :refer [redirect]]
            [yada.yada :as yada]))

(def logged-in-users
  [])

(defn find-user
  [user password db]
  (d/q '[:find ?user
         :in $ ?login ?password
         :where
         [_ :login ?login]
         [_ :password ?password]]
       db user password))

(defn layout [content]
  (html5 {:lang "pt-br"}
         [:head [:title "Presidio PB"]
          (include-css "static/css/semantic.min.css")]
         [:link {:rel "icon" :type "image/png" :href "static/favicon.ico"}]
         content))

(defn main-page [db]
  (y/resource
   {:id
    :access-control "accounts!"
    :authorization {:methods {:get :user}}
    :scheme "basic"
    :verify (fn [ctx]
              (let [user (:user ctx)
                    logged-in? (contains? logged-in-users user)]
                (if logged-in?
                  {:login user
                   :realm #{:user}})))

    :methods
    {:get
     {:produces #{"text/html"}
      :response
      (fn [ctx]
        (layout
         [:body {}
          [:div#app.ui.grid]
          (include-js "static/js/app.js")]))))

(defn login [db]
  (y/resource
   {:id :resources/login
    :methods

    {:post
     {:consumes "application/x-www-form-urlencoded"
      :produces "text/plain"
      :parameters
      {:form {:user String
              :password String}}
      :response
      (fn [ctx]
        (let [{:keys [user password]} (-> ctx :parameters :form)]
          (merge
           (:response ctx)
           (let [user (find-user user password db)])
           (if user
             {:status 303
              :headers {"location" (yada/url-for ctx )}}))))}}

    {:get
     {:produces "text/html"
      :response
      (fn [ctx]
        (html5
         {:lang "pt-br"}
         {:head {:title "Login"}
          (include-css "static/css/semantic.min.css")
          [:link {:rel "icon" :type "image/png" :href "static/favicon.ico"}]}
         [:body
          [:h1.ui.center.aligned.header nil "Login"]
          [:form.ui.one.column.centered.grid.container
           [:div.column.row
            [:div.ui.big.input
             [:input {:type "text" :placeholder "Login" :name "login"}]]]
           [:div.column.row
            [:div.ui.big.input
             [:input {:type "password" :placeholder "Senha" :name "senha"}]]]
           [:div.column.row
            {:div.ui.big.input
             [:input {:type "submit" :value "Enviar!"}]}]]]))}}}))
