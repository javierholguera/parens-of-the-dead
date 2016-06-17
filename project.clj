(defproject parens-of-the-dead "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :main parens-of-the-dead.system
  :dependencies [[com.stuartsierra/component "0.3.1"]
                 [compojure "1.5.0"]
                 [http-kit "2.1.18"]
                 [org.clojure/clojure "1.8.0"]
                 [org.clojure/clojurescript "1.9.36"]
                 [quiescent "0.3.2"]]
  :profiles {:dev {:plugins [[lein-cljsbuild "1.1.4-SNAPSHOT"]
                             [lein-figwheel "0.5.4"]]
                   :dependencies [[reloaded.repl "0.2.2"]]
                   :source-paths ["dev"]
                   :cljsbuild {:builds [{:id "undead-example"
                                         :source-paths ["src" "dev"]
                                         :figwheel true
                                         :compiler {:output-to "target/classes/public/app.js"
                                                    :output-dir "target/classes/public/out"
                                                    :main "parens-of-the-dead.client"
                                                    :asset-path "/out"
                                                    :optimizations :none
                                                    :recompile-dependents true
                                                                                                        :source-map true}}]}}})
