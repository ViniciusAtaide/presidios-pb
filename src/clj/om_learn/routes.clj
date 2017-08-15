(ns om-learn.routes
  (:require [yada.yada :as y]
            [om-learn.pages :refer [layout main-page login-page]]
            [yada.resources.classpath-resource :refer [new-classpath-resource]]))

(defn routes [db]
  ["/" {""       (layout (main-page) db)
        "login"  (login-page db)
        "static" (new-classpath-resource "static")}])

