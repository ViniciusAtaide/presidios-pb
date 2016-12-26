(import java.io.File)

(configure-repositories!
 (let [creds-file (File. (boot.App/bootdir) "credentials.gpg")
       creds-data (gpg-decrypt creds-file :as :edn)]
   (fn [{:keys [url] :as repo-map}]
     (merge repo-map (creds-data url)))))
