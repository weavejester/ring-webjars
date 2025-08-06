(defproject ring-webjars "0.3.0"
  :description "Ring middleware to serve assets from WebJars"
  :url "https://github.com/weavejester/ring-webjars"
  :license {:name "The MIT License"
            :url "http://opensource.org/licenses/MIT"}
  :dependencies [[org.clojure/clojure "1.9.0"]
                 [org.webjars/webjars-locator "0.52"]
                 [com.fasterxml.jackson.core/jackson-core "2.19.2"]
                 [io.github.classgraph/classgraph "4.8.181"]
                 [ring/ring-core "1.14.2"]]
  :profiles
  {:dev {:dependencies [[org.webjars/bootstrap "5.3.7"]
                        [ring/ring-mock "0.6.2"]]}})
