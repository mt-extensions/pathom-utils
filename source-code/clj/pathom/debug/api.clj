
(ns pathom.debug.api
    (:require [pathom.debug.resolvers :as resolvers]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

; @tutorial Debug resolver
;
; @links
; [pathom3.wsscode.com/docs](https://pathom3.wsscode.com/docs)
;
; @---
; The ':pathom/debug' resolver prints the processed query to the server console.
;
; @usage
; (:require [com.wsscode.pathom3.interface.eql :as pathom.eql]
;           [pathom.debug.api                  :as pathom.debug]
;           [pathom.tools.api                  :as pathom.tools])
;
; (pathom.tools/reg-handlers! :my-environment {:pathom/debug pathom.debug/debug})
;
; ;; Requirements:
; ;; - Provide the debug resolver to the 'p.eql/process' function!
; ;; - Associate the query vector to the environment map (before processing the query)!
; (defn process-my-query
;   []
;   (let [query       [:pathom/debug ...]
;         environment (pathom.tools/get-environment :my-environment)
;         environment (pathom.tools/env<-context environment {:query query})]
;        (pathom.eql/process environment query)))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

; @description
; ':pathom/debug' resolver
;
; @usage
; (:require [pathom.debug.api :as pathom.debug]
;           [pathom.tools.api :as pathom.tools])
;
; (pathom.tools/reg-handlers! :my-environment {:pathom/debug pathom.debug/debug})
(def debug resolvers/debug)
