
(ns pathom.tools.register.state

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

; @description
; Stored handlers (mutation and/or resolver functions).
;
; @atom (map)
; {:my-environment (map)
;   {:my-handler (mutation or resolver function)
;    ...}
;  ...}
;
; @usage
; (deref HANDLERS)
; =>
; {:my-environment {:my-mutation #com.wsscode.pathom3.connect.operation.Mutation{...}
;                   :my-resolver #com.wsscode.pathom3.connect.operation.Resolver{...} ...} ...}
 (defonce HANDLERS (atom {})))

; @description
; Stored environments.
;
; @atom (map)
; {:my-environment (map)
;   {:com.wsscode.pathom3.connect.indexes/index-attributes (map)
;    :com.wsscode.pathom3.connect.indexes/index-io (map)
;    :com.wsscode.pathom3.connect.indexes/index-mutations (map)
;    :com.wsscode.pathom3.connect.indexes/index-oir (map)
;    :com.wsscode.pathom3.connect.indexes/index-resolvers (map)
;    ...}
;  ...}
;
; @usage
; (deref ENVIRONMENTS)
; =>
; {:my-environment
;   {:com.wsscode.pathom3.connect.indexes/index-attributes {...}
;    :com.wsscode.pathom3.connect.indexes/index-io {...}
;    :com.wsscode.pathom3.connect.indexes/index-oir {...}
;    :com.wsscode.pathom3.connect.indexes/index-mutations {my-mutation #com.wsscode.pathom3.connect.operation.Mutation{...} ...}
;    :com.wsscode.pathom3.connect.indexes/index-resolvers {my-resolver #com.wsscode.pathom3.connect.operation.Resolver{...} ...} ...} ...}
(defonce ENVIRONMENTS (atom {}))
