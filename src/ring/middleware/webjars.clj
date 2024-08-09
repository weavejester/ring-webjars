(ns ring.middleware.webjars
  (:import [org.webjars WebJarAssetLocator])
  (:require [ring.middleware.head :as head]
            [ring.util.codec :as codec]
            [ring.util.request :as req]
            [ring.util.response :as resp]))

(def ^:private webjars-pattern
  #"META-INF/resources/webjars/(.*)")

(def ^:private webjars-no-version-pattern
  #"([^/]+)/(?:[^/]+)/(.*)")

(defn- add-asset-path [prefix asset-map resource]
  (let [[_ full-path] (re-matches webjars-pattern resource)
        [_ name path] (re-matches webjars-no-version-pattern full-path)]
    (assoc asset-map
           (str prefix "/" full-path)     resource
           (str prefix "/" name "/" path) resource)))

(defn- asset-map [^WebJarAssetLocator locator prefix]
  (reduce (partial add-asset-path prefix) {} (.listAssets locator "")))

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
