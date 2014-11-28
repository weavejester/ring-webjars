(defproject ring-webjars "0.1.0-SNAPSHOT"
  :description "Ring middleware to serve assets from WebJars"
  :url "https://github.com/weavejester/ring-webjars"
  :license {:name "The MIT License"
            :url "http://opensource.org/licenses/MIT"}
  :dependencies [[org.clojure/clojure "1.5.1"]
                 [org.webjars/webjars-locator "0.19"]
                 [org.slf4j/slf4j-nop "1.7.7"]
                 [ring/ring-core "1.3.2"]]
  :profiles
  {:dev {:dependencies [[org.webjars/bootstrap "3.3.1"]
                        [ring-mock "0.1.5"]]}})
