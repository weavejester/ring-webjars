(ns ring.middleware.webjars-test
  (:require [clojure.test :refer [deftest is testing]]
            [clojure.java.io :as io]
            [ring.middleware.webjars :refer [wrap-webjars]]
            [ring.mock.request :as mock]))

(defn- get-req [uri]
  (mock/request :get uri))

(defn- slurp-webjars [path]
  (slurp (io/resource (str "META-INF/resources/webjars/" path))))

(defn- async-response [handler request]
  (let [respond (promise)
        raise   (promise)]
    (handler request respond raise)
    @respond))

(deftest test-wrap-webjars
  (testing "sync handlers"
    (let [handler (wrap-webjars (constantly nil))]
      (is (nil? (handler (get-req "/foo"))))
      (is (nil? (handler (get-req "/assets"))))
      (is (nil? (handler (get-req "/assets/bootstrap"))))
      (let [resp (handler (get-req "/assets/bootstrap/css/bootstrap.css"))]
        (is (= (get-in resp [:headers "Content-Type"])
               "text/css"))
        (is (= (slurp (:body resp))
               (slurp-webjars "bootstrap/5.3.7/css/bootstrap.css"))))
      (let [resp (handler (get-req "/assets/bootstrap/5.3.7/css/bootstrap.css"))]
        (is (= (get-in resp [:headers "Content-Type"])
               "text/css"))
        (is (= (slurp (:body resp))
               (slurp-webjars "bootstrap/5.3.7/css/bootstrap.css"))))))

  (testing "async handlers"
    (let [handler (wrap-webjars (fn [_ respond _] (respond nil)))]
      (is (nil? (async-response handler (get-req "/foo"))))
      (is (nil? (async-response handler (get-req "/assets"))))
      (is (nil? (async-response handler (get-req "/assets/bootstrap"))))
      (let [resp (async-response handler (get-req "/assets/bootstrap/css/bootstrap.css"))]
        (is (= (get-in resp [:headers "Content-Type"])
               "text/css"))
        (is (= (slurp (:body resp))
               (slurp-webjars "bootstrap/5.3.7/css/bootstrap.css"))))
      (let [resp (async-response handler (get-req "/assets/bootstrap/5.3.7/css/bootstrap.css"))]
        (is (= (get-in resp [:headers "Content-Type"])
               "text/css"))
        (is (= (slurp (:body resp))
               (slurp-webjars "bootstrap/5.3.7/css/bootstrap.css")))))))
