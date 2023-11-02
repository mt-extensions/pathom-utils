
(ns pathom.debug.resolvers
    (:require [com.wsscode.pathom3.connect.operation :refer [defresolver]]
              [pathom.query.utils                    :as query.utils]
              [pathom.register.side-effects          :as register.side-effects]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn debug-f
  ; @ignore
  ;
  ; @param (map) env
  ; @param (map) resolver-props
  ;
  ; @return (string)
  [env _]
  (-> env query.utils/env->query str println)
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

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(register.side-effects/reg-handler! :pathom/debug debug)
