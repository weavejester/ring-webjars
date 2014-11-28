(ns ring.middleware.webjars-test
  (:require [clojure.test :refer :all]
            [clojure.java.io :as io]
            [ring.middleware.webjars :refer :all]
            [ring.mock.request :as mock]))

(defn- slurp-response [handler uri]
  (some-> (mock/request :get uri) handler :body slurp))

(defn- slurp-webjars [path]
  (slurp (io/resource (str "META-INF/resources/webjars/" path))))

(deftest test-wrap-webjars
  (let [handler (wrap-webjars (constantly nil))]
    (is (nil? (handler (mock/request :get "/foo"))))
    (is (nil? (handler (mock/request :get "/assets"))))
    (is (nil? (handler (mock/request :get "/assets/bootstrap"))))
    (is (= (slurp-response handler "/assets/bootstrap/less/close.less")
           (slurp-webjars "bootstrap/3.3.1/less/close.less")))))
