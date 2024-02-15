
(ns pathom.dev.debug.resolvers
    (:require [com.wsscode.pathom3.connect.operation :refer [defresolver]]
              [pathom.dev.debug.messages :as debug.messages]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn debug-f
  ; @ignore
  ;
  ; @param (map) env
  ; {:query (vector)(opt)}
  ; @param (map) resolver-props
  ;
  ; @return (string)
  [{:keys [query]} _]
  (if query (-> query str println)
            (-> debug.messages/MISSING-QUERY-ERROR println))
  (-> "Follow the white rabbit"))

(defresolver debug
             ; @ignore
             ;
             ; @param (map) env
             ; @param (map) resolver-props
             ;
             ; @return (namespaced map)
             ; {:pathom/debug (string)}
             [env resolver-props]
             {:pathom/debug (debug-f env resolver-props)})
