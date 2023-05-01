
(ns pathom.env.utils
    (:require [com.wsscode.pathom3.connect.operation :as pathom.co]
              [noop.api                              :refer [return]]))

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
  [{:keys [request]}]
  (return request))

(defn env->resolver-params
  ; @param (map) env
  ;
  ; @usage
  ; (env->resolver-params {...})
  ;
  ; @return (map)
  [env]
  ; The 'pathom.co/params' functions only returns the parameters in case of resolver functions.
  (pathom.co/params env))

(defn env->mutation-params
  ; @param (map) env
  ;
  ; @usage
  ; (env->mutation-params {...})
  ;
  ; @return (map)
  [env]
  ; It seems that the is possible to extract the parameters from 'env' map in case of mutation functions.
  ;
  ; The 'pathom.co/params' function that used by the env->resolver-params' function always returns a map, therefore
  ; the 'env->mutation-params' always returns a map as well.
  (or (-> env :com.wsscode.pathom3.connect.planner/graph :com.wsscode.pathom3.connect.planner/mutations first :params)
      (return {})))

(defn env->params
  ; @param (map) env
  ;
  ; @usage
  ; (env->params {...})
  ;
  ; @return (map)
  [env]
  ; The 'env->resolver-params' and 'env->mutation-params' functions always return maps
  ; so if the first one ('env->resolver-params') returns an empty value this functions goes
  ; to the other one ('env->mutation-params') and tries to extract the params by it.
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
  (let [params (env->params env)]
       (param-key params)))

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
