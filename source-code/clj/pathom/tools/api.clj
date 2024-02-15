
(ns pathom.tools.api
    (:require [pathom.tools.env.utils :as env.utils]
              [pathom.tools.register.env :as register.env]
              [pathom.tools.register.state :as register.state]
              [pathom.tools.register.side-effects :as register.side-effects]
              [pathom.tools.request.utils :as request.utils]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

; @tutorial Registering handlers
;
; @links
; [pathom3.wsscode.com/docs](https://pathom3.wsscode.com/docs)
;
; @---
; The ['reg-handlers!'](#reg-handlers_) function stores the provided handlers (mutation and/or resolver functions),
; then rebuilds the corresponding environment by using the 'pci/register' function.
;
; - Handles different environments (i.e. handler sets).
; - Supports hot code reloading (by updating changed environments).
; - If no environment ID is provided, the default environment ID is ':global'.
;
; @usage
; (pco/defmutation my-mutation [_] ...)
; (pco/defresolver my-resolver [_] ...)
; (reg-handlers! :my-environment {:my-mutation my-mutation
;                                 :my-resolver my-resolver})

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

; @tutorial Providing registered handlers
;
; @links
; [pathom3.wsscode.com/docs](https://pathom3.wsscode.com/docs)
;
; @---
; The ['get-environment'](#get-environment) function provides a specific environment (i.e. handler set) to the 'p.eql/process' function.
;
; @usage
; (pco/defresolver my-resolver [_] ...)
; (reg-handlers! :my-environment {:my-resolver my-resolver})
;
; (let [environment (get-environment :my-environment)]
;      (p.eql/process environment [:my-reosolver]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

; @redirect (pathom.tools.env.utils/*)
(def env<-context         env.utils/env<-context)
(def env->resolver-params env.utils/env->resolver-params)
(def env->resolver-param  env.utils/env->resolver-param)
(def env<-resolver-param  env.utils/env<-resolver-param)
(def env->mutation-dex    env.utils/env->mutation-dex)
(def env->mutation-params env.utils/env->mutation-params)
(def env->mutation-param  env.utils/env->mutation-param)
(def env<-mutation-param  env.utils/env<-mutation-param)

; @redirect (pathom.tools.register.env/*)
(def get-environment register.env/get-environment)

; @redirect (pathom.tools.register.side-effects/*)
(def reg-handlers!    register.side-effects/reg-handlers!)
(def remove-handlers! register.side-effects/remove-handlers!)

; @redirect (pathom.tools.register.state/*)
(def HANDLERS     register.state/HANDLERS)
(def ENVIRONMENTS register.state/ENVIRONMENTS)

; @redirect (pathom.tools.request.utils/*)
(def request->query request.utils/request->query)
