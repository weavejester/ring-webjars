(defproject ring-webjars "0.1.0"
  :description "Ring middleware to serve assets from WebJars"
  :url "https://github.com/weavejester/ring-webjars"
  :license {:name "The MIT License"
            :url "http://opensource.org/licenses/MIT"}
  :dependencies [[org.clojure/clojure "1.6.0"]
                 [org.webjars/webjars-locator "0.19"]
                 [ring/ring-core "1.4.0"]]
  :profiles
  {:dev {:dependencies [[org.webjars/bootstrap "3.3.5"]
                        [ring-mock "0.1.5"]]}})
