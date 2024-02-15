
(ns pathom.tools.register.state)

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

; @description
; Stored handlers (mutation and/or resolver functions).
;
; @atom (map)
; {:my-environment (map)
;   {:my-handler (mutation or resolver function)}}
;
; @usage
; (deref HANDLERS)
; =>
; {:my-environment {:my-mutation ...
;                   :my-resolver ...}}
(defonce HANDLERS (atom {}))

; @description
; Stored environments.
;
; @atom (map)
; {...}
;
; @usage
; (deref ENVIRONMENTS)
; =>
; {...}
(defonce ENVIRONMENTS (atom {}))
