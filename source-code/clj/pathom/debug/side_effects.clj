
(ns pathom.debug.side-effects
    (:require [pathom.debug.state :as debug.state]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn set-debug-mode!
  ; @description
  ; Turns the debug mode on.
  ;
  ; @usage
  ; (set-debug-mode!)
  []
  (reset! debug.state/DEBUG-MODE? true))

(defn quit-debug-mode!
  ; @description
  ; Turns the debug mode off.
  ;
  ; @usage
  ; (quit-debug-mode!)
  []
  (reset! debug.state/DEBUG-MODE? false))

(defn toggle-debug-mode!
  ; @description
  ; Toggles the debug mode on/off.
  ;
  ; @usage
  ; (toggle-debug-mode!)
  []
  (swap! debug.state/DEBUG-MODE? not))
