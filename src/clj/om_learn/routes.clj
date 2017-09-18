(ns om-learn.routes
  (:require [buddy.sign.jwt :as jwt]
            [clojure.edn :as edn]
            [datomic.api :as d]
            [om-learn.pages :refer [layout main-page]]
            [schema.core :as s]
            [yada.resources.classpath-resource :refer [new-classpath-resource]]
            [yada.security :refer [verify]]
            [yada.yada :as yada])
  (:import java.util.UUID))

(def secret (str (UUID/randomUUID)))

(def logged-in-users
  (atom []))

(defn find-user
  [user password db]
  (d/q '[:find ?user
         :in $ ?user ?password
         :where
         [_ :login ?user]
         [_ :password ?password]]
       db user password))

(defmethod verify :app/signed-cookie
  [ctx scheme]
  (some->
    (get-in ctx [:cookies "session"])
    (jwt/unsign secret)
    :claims
    edn/read-string))

(defn- login-fn [ctx]
  (if (contains? logged-in-users (:user ctx))
    {:login (:user ctx)
     :realm #{:user}}))

(defn- main [layout db]
  (yada/resource
    {:id :pages/main-page
     :methods
         {:get
          {:produces #{"text/html"}
           :response main-page}}}))

(defn- login-check [ctx db]
  (let [{:keys [user password]} (-> ctx :parameters :form)]
    (merge
      (:response ctx)
      (let [user (find-user user password db)]
        (if user
          (do
            (swap! logged-in-users conj user)
            {:status  303
             :cookies
             {"session"
              {:value
               (jwt/sign
                {:claims (pr-str {:user user :roles #{:user}})}
                secret)}}})
          {:body "Login falhou"
           :status 401})))))

(defn- login [db]
  (yada/resource
    {:id :resources/login
     :methods
         {:post
          {:consumes "application/x-www-form-urlencoded"
           :produces "text/plain"
           :parameters
           {:form {:user     String
                   :password s/Str}}
           :response (fn [ctx] (login-check ctx db))}}}))

(defn routes [db]
  ["/" {""       (main layout db)
        "static" (new-classpath-resource "static")}])

(comment
  :access-control
  {:authorization {:methods {:get :user}}
   :scheme        :app/signed-whoami-header
   :verify        login-fn})
