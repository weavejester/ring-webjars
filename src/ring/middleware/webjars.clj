(ns ring.middleware.webjars
  (:import [org.webjars WebJarAssetLocator])
  (:require [ring.middleware.head :as head]
            [ring.util.codec :as codec]
            [ring.util.request :as req]
            [ring.util.response :as resp]))

(def ^:private webjars-pattern
  #"META-INF/resources/webjars/([^/]+)/([^/]+)/(.*)")

(defn- asset-path [prefix resource]
  (let [[_ name _version path] (re-matches webjars-pattern resource)]
    (str prefix "/" name "/" path)))

(defn- asset-map [^WebJarAssetLocator locator prefix]
  (->> (.listAssets locator "")
       (map (juxt (partial asset-path prefix) identity))
       (into {})))

(defn- request-path [request]
  (codec/url-decode (req/path-info request)))

(defn- get-request? [request]
  (#{:head :get} (:request-method request)))

(defn- asset-response [path request]
  (-> (resp/resource-response path)
      (head/head-response request)))

(defn wrap-webjars
  ([handler]
     (wrap-webjars handler "/assets"))
  ([handler prefix]
     (let [assets (asset-map (WebJarAssetLocator.) prefix)]
       (fn
         ([request]
          (if (get-request? request)
            (if-let [path (assets (request-path request))]
              (asset-response path request)
              (handler request))
            (handler request)))
         ([request respond raise]
          (if (get-request? request)
            (if-let [path (assets (request-path request))]
              (respond (asset-response path request))
              (handler request respond raise))
            (handler request respond raise)))))))
