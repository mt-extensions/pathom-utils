
(ns pathom.query.utils
    (:require [http.api           :as http]
              [pathom.debug.state :as debug.state]
              [pathom.env.utils   :as env.utils]
              [reader.api         :as reader]
              [vector.api         :as vector]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn request->query
  ; @ignore
  ;
  ; @description
  ; In case of debug mode set, this function adds the
  ; :pathom/debug key to the query and the query processor can use this debug
  ; resolver to prints the received query vectors to the server console.
  ;
  ; @param (map) request
  ; {:params (map)(opt)
  ;   {:query (string or vector)(opt)}
  ;  :transit-params (map)(opt)
  ;   {:query (string or vector)(opt)}}
  ;
  ; @usage
  ; (request->query {...})
  ;
  ; @return (vector)
  [request]
  ; When uploading files the request body is a FormData object which contains the query as a string!
  (letfn [(query-f [query] (cond (vector? query) (-> query)
                                 (string? query) (-> query reader/read-edn)))
          (debug-f [query] (if @debug.state/DEBUG-MODE? (-> query (vector/cons-item :pathom/debug))
                                                        (-> query)))]

         ; BUG#4529
         ; In case of the query is a vector with only one keyword item, ...
         ; ... the received query in the 'params' map somehow looses its containing vector,
         ;     therefore the query has to be derived from the 'transit-params' map instead!
         ;
         ; E.g., [:my-resolver]                            <= sent by the client
         ;       =>
         ;       {:transit-params {:query [:my-resolver]}  <= received by the server
         ;        :params         {:query :my-resolver}}   <= received by the server (where is the containing vector?)
         (if-let [query (http/request->transit-param request :query)]
                 (-> query query-f debug-f)
                 (if-let [query (http/request->param request :query)]
                         (-> query query-f debug-f)))))

(defn env->query
  ; @ignore
  ;
  ; @param (map) env
  ; {:request (map)
  ;   {:params (map)
  ;     {:query (vector)(opt)}}}
  ;
  ; @usage
  ; (env->query {...})
  ;
  ; @return (vector)
  [env]
  (-> env env.utils/env->request request->query))
