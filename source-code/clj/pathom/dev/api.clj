
(ns pathom.dev.api
    (:require [pathom.dev.debug.resolvers :as debug.resolvers]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

; @tutorial Debug resolver
;
; @links
; [pathom3.wsscode.com/docs](https://pathom3.wsscode.com/docs)
;
; The ':pathom/debug' resolver prints the processed query to the server console.
;
; @usage
; 1. Register the debug resolver to the 'p.eql/process' function!
; 2. Associate the query vector into the environment map before processing the query!
;
; (:require [com.wsscode.pathom3.interface.eql :as pathom.eql]
;           [pathom.dev.api                    :as pathom.dev]
;           [pathom.tools.api                  :as pathom.tools])
;
; (pathom.tools/reg-handlers! :my-environment {:pathom/debug pathom.dev/debug})
;
; (defn process-my-query
;   []
;   (let [query       [:pathom/debug ...]
;         environment (pathom.tools/get-environment :my-environment)
;         environment (pathom.tools/env<-context environment {:query query})]
;        (pathom.eql/process environment query)))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

; @redirect (pathom.dev.debug.resolvers/*)
(def debug debug.resolvers/debug)
