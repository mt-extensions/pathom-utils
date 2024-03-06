
(ns pathom.tools.register.side-effects
    (:require [com.wsscode.pathom3.connect.indexes :as pathom.ci]
              [fruits.map.api                      :as map]
              [pathom.tools.register.state         :as register.state]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn rebuild-environment!
  ; @ignore
  ;
  ; @note
  ; The provided environment ID identifies a specific environment that must be rebuilt.
  ;
  ; @description
  ; Rebuilds a specific environment identified by the given environment ID (stored in the 'ENVIRONMENTS' atom).
  ; Apply when any handler (mutation or resolver function) is registered or removed (into/from the 'HANDLERS' atom).
  ;
  ; @param (keyword)(opt) environment-id
  ; Default: :global
  ;
  ; @usage
  ; (defresolver my-resolver [_ _] ...)
  ; (reg-handler! :my-environment my-resolver)
  ; (rebuild-environment! :my-environment)
  ([]
   (rebuild-environment! :global))

  ([environment-id]
   (let [handlers    (-> register.state/HANDLERS deref (get environment-id) vals)
         environment (-> handlers pathom.ci/register)]
        (swap! register.state/ENVIRONMENTS assoc environment-id environment))))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn reg-handlers!
  ; @note
  ; The provided environment ID identifies a specific environment that the given handlers are registered into.
  ;
  ; @description
  ; Register multiple handlers (mutation and/or resolver functions) (into the 'HANDLERS' atom), then rebuilds the corresponding environment.
  ;
  ; @param (keyword)(opt) environment-id
  ; Default: :global
  ; @param (map) handlers
  ; {:my-handler (mutation or resolver function)}
  ;
  ; @usage
  ; (reg-handlers! :my-environment {...})
  ;
  ; @usage
  ; (pco/defmutation my-mutation [_] ...)
  ; (pco/defresolver my-resolver [_] ...)
  ; (reg-handlers! :my-environment {:my-mutation my-mutation
  ;                                 :my-resolver my-resolver})
  ([handlers]
   (reg-handlers! :global handlers))

  ([environment-id handlers]
   (swap! register.state/HANDLERS update environment-id merge handlers)
   (rebuild-environment! environment-id)))

(defn remove-handlers!
  ; @note
  ; The provided environment ID identifies a specific environment that the identified handlers are removed from.
  ;
  ; @description
  ; Removes multiple handlers (mutation and/or resolver functions) (from the 'HANDLERS' atom), then rebuilds the corresponding environment.
  ;
  ; @param (keyword)(opt) environment-id
  ; Default: :global
  ; @param (keywords in vector) handler-ids
  ; [(keyword) handler-id]
  ;
  ; @usage
  ; (remove-handlers! :my-environment [:my-mutation :my-resolver])
  ;
  ; @usage
  ; (pco/defmutation my-mutation [_] ...)
  ; (pco/defresolver my-resolver [_] ...)
  ; (reg-handlers! :my-environment {:my-mutation my-mutation
  ;                                 :my-resolver my-resolver})
  ; (remove-handlers! :my-environment [:my-mutation :my-resolver])
  ([handler-ids]
   (remove-handlers! :global handler-ids))

  ([environment-id handler-ids]
   (swap! register.state/HANDLERS update environment-id map/remove-keys handler-ids)
   (rebuild-environment! environment-id)))
