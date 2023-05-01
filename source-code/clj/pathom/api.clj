
(ns pathom.api
    (:require [pathom.debug.resolvers]
              [pathom.debug.side-effects    :as debug.side-effects]
              [pathom.debug.state           :as debug.state]
              [pathom.env.utils             :as env.utils]
              [pathom.query.side-effects    :as query.side-effects]
              [pathom.register.side-effects :as register.side-effects]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

; pathom.debug.side-effects
(def set-debug-mode!    debug.side-effects/set-debug-mode!)
(def quit-debug-mode!   debug.side-effects/quit-debug-mode!)
(def toggle-debug-mode! debug.side-effects/toggle-debug-mode!)

; pathom.debug.state
(def DEBUG-MODE? debug.state/DEBUG-MODE?)

; pathom.env.utils
(def env->request         env.utils/env->request)
(def env->resolver-params env.utils/env->resolver-params)
(def env->mutation-params env.utils/env->mutation-params)
(def env->params          env.utils/env->params)
(def env->param           env.utils/env->param)
(def env<-param           env.utils/env<-param)

; pathom.query.side-effects
(def process-query!   query.side-effects/process-query!)
(def process-request! query.side-effects/process-request!)

; pathom.register.side-effects
(def reg-handler!  register.side-effects/reg-handler!)
(def reg-handlers! register.side-effects/reg-handlers!)
