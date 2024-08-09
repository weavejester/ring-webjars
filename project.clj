(defproject ring-webjars "0.2.1"
  :description "Ring middleware to serve assets from WebJars"
  :url "https://github.com/weavejester/ring-webjars"
  :license {:name "The MIT License"
            :url "http://opensource.org/licenses/MIT"}
  :dependencies [[org.clojure/clojure "1.9.0"]
                 [org.webjars/webjars-locator "0.52"]
                 [ring/ring-core "1.12.2"]]
  :profiles
  {:dev {:dependencies [[org.webjars/bootstrap "5.3.3"]
                        [ring/ring-mock "0.4.0"]]}})
