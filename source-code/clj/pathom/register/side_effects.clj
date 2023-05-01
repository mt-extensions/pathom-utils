
(ns pathom.register.side-effects
    (:require [com.wsscode.pathom3.connect.indexes :as pathom.ci]
              [pathom.register.helpers             :as register.helpers]
              [pathom.register.state               :as register.state]
              [random.api                          :as random]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn reset-environment!
  ; @ignore
  []
  ; After a handler function or functions being registered the 'reset-environment!'
  ; function rebuilds the Pathom environment in order to the re-registered handler
  ; functions overwrite their previously registered instances.
  ; Its important in development builds where a code reloader (e.g. wrap-reload)
  ; can reload the changed namespaces and register changed handler functions.
  (let [handlers    (-> register.state/HANDLERS deref vals)
        registry    [handlers]
        environment (pathom.ci/register registry)]
       (reset! register.state/ENVIRONMENT environment)))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn reg-handler!
  ; @param (keyword)(opt) handler-id
  ; @param (handler function) handler-f
  ;
  ; @usage
  ; (pco/defmutation my-mutation [env] ...)
  ; (reg-handler! ::handler my-mutation)
  ;
  ; @usage
  ; (pco/defresolver my-resolver [env] ...)
  ; (reg-handler! ::handler my-resolver)
  ([handler-f]
   (reg-handler! (random/generate-keyword) handler-f))

  ([handler-id handler-f]
   (swap! register.state/HANDLERS assoc handler-id handler-f)
   (reset-environment!)))

(defn reg-handlers!
  ; @param (keyword)(opt) handlers-id
  ; @param (handler functions in vector) handler-fs
  ;
  ; @usage
  ; (pco/defmutation my-mutation [env] ...)
  ; (pco/defresolver my-resolver [env] ...)
  ; (def HANDLERS [my-mutation my-resolver])
  ; (reg-handlers! ::handlers HANDLERS)
  ([handler-fs]
   (reg-handlers! (random/generate-keyword) handler-fs))

  ([handlers-id handler-fs]
   (swap! register.state/HANDLERS assoc handlers-id handler-fs)
   (reset-environment!)))
