
(ns pathom.env.utils
    (:require [com.wsscode.pathom3.connect.operation :as pathom.co]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn env->request
  ; @param (map) env
  ; {:request (map)}
  ;
  ; @usage
  ; (env->request {...})
  ;
  ; @return (map)
  [env]
  (:request env))

(defn env->resolver-params
  ; @param (map) env
  ;
  ; @usage
  ; (env->resolver-params {...})
  ;
  ; @return (map)
  [env]
  ; - The 'pathom.co/params' function returns parameters only of resolver functions.
  ; - The 'pathom.co/params' function always returns a map.
  (-> env pathom.co/params))

(defn env->mutation-params
  ; @param (map) env
  ;
  ; @usage
  ; (env->mutation-params {...})
  ;
  ; @return (map)
  [env]
  ; - It seems that is possible to extract the parameters of mutation functions from the 'env' map.
  ; - The 'env->resolver-params' function always returns a map. Therefore, the 'env->mutation-params' always returns a map as well.
  (or (-> env :com.wsscode.pathom3.connect.planner/graph :com.wsscode.pathom3.connect.planner/mutations first :params)
      (-> {})))

(defn env->params
  ; @param (map) env
  ;
  ; @usage
  ; (env->params {...})
  ;
  ; @return (map)
  [env]
  ; The 'env->resolver-params' and 'env->mutation-params' functions always return maps
  ; so if the first one ('env->resolver-params') returns an empty value this functions steps
  ; to the other one ('env->mutation-params') and tries to extract the params with it.
  (if (-> env env->resolver-params empty? not)
      (-> env env->resolver-params)
      (-> env env->mutation-params)))

(defn env->param
  ; @param (map) env
  ; @param (keyword) param-key
  ;
  ; @usage
  ; (env->param {...} :my-param)
  ;
  ; @return (*)
  [env param-key]
  (-> env env->params param-key))

(defn env<-param
  ; @param (map) env
  ; @param (keyword) param-key
  ; @param (*) param-value
  ;
  ; @usage
  ; (env<-param {...} :my-param "My value")
  ;
  ; @return (map)
  [env param-key param-value]
  ; https://github.com/wilkerlucio/pathom3/blob/main/src/main/com/wsscode/pathom3/connect/operation.cljc
  (assoc-in env [:com.wsscode.pathom3.connect.planner/node :com.wsscode.pathom3.connect.planner/params param-key]
            param-value))
