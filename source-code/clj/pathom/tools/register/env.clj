
(ns pathom.tools.register.env
    (:require [pathom.tools.register.state :as register.state]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn get-environment
  ; @description
  ; - Returns an environment map that contains resolver and mutation functions registered by the ['reg-handlers!'](#reg-handlers_) functions.
  ; - Equivalent to the 'p.ci/register' function (in terms of output).
  ;
  ; @param (keyword)(opt) environment-id
  ; Default: :global
  ;
  ; @usage
  ; (get-environment)
  ; =>
  ; {}
  ;
  ; @usage
  ; (get-environment :my-environment)
  ; =>
  ; {}
  ;
  ; @return (map)
  ; {}
  ([]
   (get-environment :global))

  ([environment-id]
   (-> register.state/ENVIRONMENTS deref (get environment-id))))
