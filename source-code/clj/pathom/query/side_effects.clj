
(ns pathom.query.side-effects
    (:require [com.wsscode.pathom3.interface.eql :as pathom.eql]
              [pathom.query.utils                :as query.utils]
              [pathom.register.state             :as register.state]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn process-query!
  ; @param (map) environment
  ; @param (*) query
  ;
  ; @usage
  ; (process-query! my-environment my-query)
  ;
  ; @return (map)
  [environment query]
  (pathom.eql/process environment query))

(defn process-request!
  ; @param (map) request
  ;
  ; @usage
  ; (process-request! {...})
  ;
  ; @return (map)
  [request]
  (let [query       (query.utils/request->query request)
        environment (assoc @register.state/ENVIRONMENT :request request)]
       (process-query! environment query)))
