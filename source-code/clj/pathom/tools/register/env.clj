
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
  ; {:com.wsscode.pathom3.connect.indexes/index-attributes {...}
  ;  :com.wsscode.pathom3.connect.indexes/index-io {...}
  ;  :com.wsscode.pathom3.connect.indexes/index-oir {...}
  ;  :com.wsscode.pathom3.connect.indexes/index-mutations {my-mutation #com.wsscode.pathom3.connect.operation.Mutation{...} ...}
  ;  :com.wsscode.pathom3.connect.indexes/index-resolvers {my-resolver #com.wsscode.pathom3.connect.operation.Resolver{...} ...} ...}
  ;
  ; @usage
  ; (get-environment :my-environment)
  ; =>
  ; {:com.wsscode.pathom3.connect.indexes/index-attributes {...}
  ;  :com.wsscode.pathom3.connect.indexes/index-io {...}
  ;  :com.wsscode.pathom3.connect.indexes/index-oir {...}
  ;  :com.wsscode.pathom3.connect.indexes/index-mutations {my-mutation #com.wsscode.pathom3.connect.operation.Mutation{...} ...}
  ;  :com.wsscode.pathom3.connect.indexes/index-resolvers {my-resolver #com.wsscode.pathom3.connect.operation.Resolver{...} ...} ...}
  ;
  ; @return (map)
  ; {:com.wsscode.pathom3.connect.indexes/index-attributes (map)
  ;  :com.wsscode.pathom3.connect.indexes/index-io (map)
  ;  :com.wsscode.pathom3.connect.indexes/index-mutations (map)
  ;  :com.wsscode.pathom3.connect.indexes/index-oir (map)
  ;  :com.wsscode.pathom3.connect.indexes/index-resolvers (map)
  ;  ...}
  ([]
   (get-environment :global))

  ([environment-id]
   (-> register.state/ENVIRONMENTS deref (get environment-id))))
