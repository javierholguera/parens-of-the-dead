(ns parens-of-the-dead.system
  (:require [com.stuartsierra.component :as component]
            [org.httpkit.server :as server]
            [parens-of-the-dead.web :refer [app]]))

(defn- start-server [handler port]
  (let [server (server/run-server handler {:port port})]
    (println "Server is running at " port)
    server))

(defn- stop-server [server]
  (when server
    (server))) ;; run-server returns a fn that stops itself

(defrecord ParensOfTheDead
    []
  component/Lifecycle
  (start [this]
    (assoc this :server (start-server #'app 9009)))
  (stop [this]
    (stop-server (:server this))
    (dissoc this :server)))

(defn create-system
  []
  (ParensOfTheDead.))

(defn -main [& args]
  (.start (create-system)))

