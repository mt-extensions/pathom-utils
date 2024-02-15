
(ns pathom.tools.request.utils
    (:require [fruits.http.api   :as http]
              [fruits.reader.api :as reader]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn request->query
  ; @note
  ; - The primary source of the query vector is the transit parameters map of the given request.
  ; - The fallback source of the query vector is the parameters map of the given request.
  ;
  ; @description
  ; Returns the query vector from the given request map.
  ;
  ; @param (map) request
  ; {:params (map)(opt)
  ;   {:query (string or vector)(opt)}
  ;  :transit-params (map)(opt)
  ;   {:query (string or vector)(opt)}}
  ;
  ; @usage
  ; (request->query {:transit-params {:query [:my-resolver] ...} ...})
  ; =>
  ; [:my-resolver]
  ;
  ; @usage
  ; (request->query {:transit-params {:query "[:my-resolver]" ...} ...})
  ; =>
  ; [:my-resolver]
  ;
  ; @usage
  ; (request->query {:params {:query [:my-resolver] ...} ...})
  ; =>
  ; [:my-resolver]
  ;
  ; @usage
  ; (request->query {:params {:query "[:my-resolver]" ...} ...})
  ; =>
  ; [:my-resolver]
  ;
  ; @return (vector)
  [request]
  ; @note
  ; When uploading files, the request body might be a FormData object which contain the query as a string!
  ;
  ; @bug (#4529)
  ; In case the query is a vector with only one keyword item, ...
  ; ... the received query in the parameters map might lose its containing vector.
  ;     Therefore, the primary source of the query must be the transit parameters map of the request!
  ;
  ; E.g., [:my-resolver]                            <= sent by the client
  ;       =>
  ;       {:transit-params {:query [:my-resolver]}  <= received by the server
  ;        :params         {:query :my-resolver}}   <= received by the server (where is the containing vector?)
  ;
  ; cljs-ajax/cljs-ajax       {:mvn/version "0.8.0"}
  ; ring/ring                 {:mvn/version "1.9.0"}
  ; ring/ring-json            {:mvn/version "0.5.1"}
  ; ring-server/ring-server   {:mvn/version "0.5.0"}
  ; ring-transit/ring-transit {:mvn/version "0.1.6"}
  (letfn [(f0 [%] (cond (-> % vector?) (-> %)
                        (-> % string?) (-> % reader/parse-edn)))
          (f1 [%] (when (-> % vector?) (-> %)))]
         (or (if-let [query (http/request->transit-param request :query)] (-> query f0 f1))
             (if-let [query (http/request->param         request :query)] (-> query f0 f1)))))
