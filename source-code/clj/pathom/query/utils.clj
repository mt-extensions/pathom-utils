
(ns pathom.query.utils
    (:require [http.api           :as http]
              [noop.api           :refer [return]]
              [pathom.debug.state :as debug.state]
              [pathom.env.helpers :as env.helpers]
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
  ; When uploading files the request body is a FormData object which contains
  ; the query as a string!
  (letfn [(query-f [query] (cond (vector? query) (return               query)
                                 (string? query) (reader/string->mixed query)))
          (debug-f [query] (if @debug.state/DEBUG-MODE? (vector/cons-item query :pathom/debug)
                                                        (return           query)))]

         ; BUG#4529
         ; In case of the query is a vector with one keyword item, ...
         ; ... the received query in the params map losts its containing vector,
         ;     therefore the query has to be read from the transit-params map!
         ;
         ; E.g. [:my-resolver]                            <= sent by the client
         ;      =>
         ;      {:transit-params {:query [:my-resolver]}  <= received by the server
         ;       :params         {:query :my-resolver}}   <= received by the server
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
  (-> env env.helpers/env->request request->query))
