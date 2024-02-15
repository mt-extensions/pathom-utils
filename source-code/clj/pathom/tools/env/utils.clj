
(ns pathom.tools.env.utils
    (:require [com.wsscode.pathom3.connect.operation :as pathom.co]
              [fruits.vector.api :as vector]
              [fruits.map.api :as map]
              [fruits.seqable.api :as seqable]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn env<-context
  ; @description
  ; Merges the given context map onto the given environment map.
  ;
  ; @param (map) env
  ; @param (map) context
  ;
  ; @usage
  ; (env<-context {...} {:query [:my-resolver] :request {...}})
  ; =>
  ; {:query [:my-resolver] :request {...} ...}
  ;
  ; @return (map)
  [env context]
  (merge env context))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn env->resolver-params
  ; @note
  ; The 'p.eql/process' function associates the resolver parameters to the environment map provided to resolver functions.
  ;
  ; @note
  ; The environment map (provided to resolver functions) contains details only of the resolver that took the environment map.
  ; Therefore, no need to provide the resolver name to specify which resolver's parameters should be returned.
  ;
  ; @description
  ; Returns the resolver parameters from the given environment map.
  ;
  ; @param (map) env
  ; {:com.wsscode.pathom3.connect.planner/node (map)(opt)
  ;   {:com.wsscode.pathom3.connect.planner/params (map)(opt)
  ;    ...}
  ;  ...}
  ;
  ; @usage
  ; (env->resolver-params {...})
  ; =>
  ; {:my-param "My value"}
  ;
  ; @usage
  ; (env->resolver-params {:com.wsscode.pathom3.connect.planner/node {:com.wsscode.pathom3.connect.planner/params {:my-param "My value"} ...} ...})
  ; =>
  ; {:my-param "My value"}
  ;
  ; @usage
  ; (pco/defresolver my-resolver
  ;   []
  ;   {:my-resolver ...})
  ;
  ; @return (map)
  [env]
  ; - The 'pathom.co/params' function returns parameters only of resolver functions.
  ; - The 'pathom.co/params' function always returns a map.
  (-> env pathom.co/params))

(defn env->resolver-param
  ; @note
  ; The 'p.eql/process' function associates the resolver parameters to the environment map provided to resolver functions.
  ;
  ; @note
  ; The environment map (provided to resolver functions) contains details only of the resolver that took the environment map.
  ; Therefore, no need to provide the resolver name to specify which resolver's parameters should be returned.
  ;
  ; @description
  ; Returns a specific resolver parameter from the given environment map.
  ;
  ; @param (map) env
  ; {:com.wsscode.pathom3.connect.planner/node (map)(opt)
  ;   {:com.wsscode.pathom3.connect.planner/params (map)(opt)
  ;    ...}
  ;  ...}
  ; @param (*) param-key
  ;
  ; @usage
  ; (env->resolver-param {...} :my-param)
  ; =>
  ; "My value"
  ;
  ; @usage
  ; (env->resolver-param {:com.wsscode.pathom3.connect.planner/node {:com.wsscode.pathom3.connect.planner/params {:my-param "My value"} ...} ...} :my-param)
  ; =>
  ; "My value"
  ;
  ; @return (*)
  [env param-key]
  (-> env (env->resolver-params)
          (get param-key)))

(defn env<-resolver-param
  ; @note
  ; The environment map (provided to resolver functions) contains details only of the resolver that took the environment map.
  ; Therefore, no need to provide the resolver name to specify which resolver's parameters should be updated.
  ;
  ; @description
  ; Associates the given value to the given environment map as a resolver parameter.
  ;
  ; @param (map) env
  ; @param (*) param-key
  ; @param (*) param-value
  ;
  ; @usage
  ; (env<-resolver-param {...} :my-param "My value")
  ; =>
  ; {:com.wsscode.pathom3.connect.planner/node {:com.wsscode.pathom3.connect.planner/params {:my-param "My value" ...} ...} ...}
  ;
  ; @return (map)
  ; {:com.wsscode.pathom3.connect.planner/node (map)
  ;   {:com.wsscode.pathom3.connect.planner/params (map)
  ;    ...}
  ;  ...}
  [env param-key param-value]
  (assoc-in env [:com.wsscode.pathom3.connect.planner/node :com.wsscode.pathom3.connect.planner/params param-key] param-value))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn env->mutation-dex
  ; @description
  ; Returns the index of a specific mutation within the processed query.
  ;
  ; @param (map) env
  ; {:com.wsscode.pathom3.connect.planner/graph (map)
  ;   {:com.wsscode.pathom3.connect.planner/mutations (maps in vector)
  ;     [(map) mutation
  ;       {:dispatch-key (symbol)
  ;        ...}]
  ;    ...}
  ;  ...}
  ; @param (keyword) mutation-name
  ;
  ; @usage
  ; (env->mutation-dex {...} :my-mutation)
  ; =>
  ; 0
  ;
  ; @usage
  ; (env->mutation-dex {:com.wsscode.pathom3.connect.planner/graph {:com.wsscode.pathom3.connect.planner/mutations [{:dispatch-key my-mutation ...} {...} ...] ...} ...} :my-mutation)
  ; =>
  ; 0
  ;
  ; @return (integer)
  [env mutation-name]
  (letfn [(f0 [%] (-> % :dispatch-key (= (-> mutation-name name symbol))))]
         (-> env (:com.wsscode.pathom3.connect.planner/graph)
                 (:com.wsscode.pathom3.connect.planner/mutations)
                 (vector/first-dex-by f0))))

(defn env->mutation-params
  ; @note
  ; The 'p.eql/process' function associates the mutation parameters to the environment map provided to mutation functions.
  ;
  ; @note
  ; The environment map (provided to mutation functions) contains details of all mutations of the processed query.
  ; Therefore, the mutation name must be provided to specify which mutation's parameters should be returned.
  ;
  ; @description
  ; Returns the mutation parameters from the given environment map.
  ; If the mutation name is not provided it returns the parameters of the first mutation of the processed query.
  ;
  ; @param (map) env
  ; {:com.wsscode.pathom3.connect.planner/graph (map)
  ;   {:com.wsscode.pathom3.connect.planner/mutations (maps in vector)
  ;     [(map) mutation
  ;       {:dispatch-key (symbol)
  ;        :params (map)(opt)
  ;        ...}]
  ;    ...}
  ;  ...}
  ; @param (keyword)(opt) mutation-name
  ;
  ; @usage
  ; (env->mutation-params {...})
  ; =>
  ; {:my-param "My value"}
  ;
  ; @usage
  ; (env->mutation-params {...} :my-mutation)
  ; =>
  ; {:my-param "My value"}
  ;
  ; @usage
  ; (env->mutation-params {:com.wsscode.pathom3.connect.planner/graph {:com.wsscode.pathom3.connect.planner/mutations [{:dispatch-key my-mutation :params {:my-param "My value"} ...} ...] ...} ...} :my-mutation)
  ; =>
  ; {:my-param "My value"}
  ;
  ; @return (map)
  ([env]
   (env->mutation-params env nil))

  ([env mutation-name]
   ; The 'env->resolver-params' function always returns a map. Therefore, the 'env->mutation-params' always returns a map as well.
   (letfn [(f0 [%] ((if mutation-name f1 f2) %))
           (f1 [_] (env->mutation-dex env mutation-name))
           (f2 [%] (seqable/first-dex %))]
          (map/get-by env [:com.wsscode.pathom3.connect.planner/graph :com.wsscode.pathom3.connect.planner/mutations f0 :params]))))

(defn env->mutation-param
  ; @note
  ; The 'p.eql/process' function associates the mutation parameters to the environment map provided to mutation functions.
  ;
  ; @note
  ; The environment map (provided to mutation functions) contains details of all mutations of the processed query.
  ; Therefore, the mutation name must be provided to specify which mutation's parameters should be returned.
  ;
  ; @description
  ; Returns a specific mutation parameter from the given environment map.
  ; If the mutation name is not provided it returns the parameter of the first mutation of the processed query.
  ;
  ; @param (map) env
  ; {:com.wsscode.pathom3.connect.planner/graph (map)
  ;   {:com.wsscode.pathom3.connect.planner/mutations (maps in vector)
  ;     [(map) mutation
  ;       {:dispatch-key (symbol)
  ;        :params (map)(opt)
  ;        ...}]
  ;    ...}
  ;  ...}
  ; @param (keyword)(opt) mutation-name
  ; @param (*) param-key
  ;
  ; @usage
  ; (env->mutation-param {...} :my-param)
  ; =>
  ; "My value"
  ;
  ; @usage
  ; (env->mutation-param {...} :my-mutation :my-param)
  ; =>
  ; "My value"
  ;
  ; @usage
  ; (env->mutation-param {:com.wsscode.pathom3.connect.planner/graph {:com.wsscode.pathom3.connect.planner/mutations [{:dispatch-key my-mutation :params {:my-param "My value"} ...} ...] ...} ...} :my-mutation :my-param)
  ; =>
  ; "My value"
  ;
  ; @return (*)
  ([env param-key]
   (-> env (env->mutation-params)
           (get param-key)))

  ([env mutation-name param-key]
   (-> env (env->mutation-params mutation-name)
           (get param-key))))

(defn env<-mutation-param
  ; @note
  ; The environment map (provided to mutation functions) contains details of all mutations of the processed query.
  ; Therefore, the mutation name must be provided to specify which mutation's parameters should be updated.
  ;
  ; @description
  ; Associates the given value to the given environment map as a mutation parameter.
  ; If the mutation name is not provided it associates the value to the first mutation of the processed query.
  ;
  ; @param (map) env
  ; @param (keyword)(opt) mutation-name
  ; @param (*) param-key
  ; @param (*) param-value
  ;
  ; @usage
  ; (env<-mutation-param {...} :my-param "My value")
  ; =>
  ; {}
  ;
  ; @usage
  ; (env<-mutation-param {...} :my-mutation :my-param "My value")
  ; =>
  ; {}
  ;
  ; @return (map)
  ; {}
  ([env param-key param-value]
   (env<-mutation-param env nil param-key param-value))

  ([env mutation-name param-key param-value]
   (letfn [(f0 [%] ((if mutation-name f1 f2) %))
           (f1 [_] (env->mutation-dex env mutation-name))
           (f2 [%] (seqable/first-dex %))]
          (map/update-by env [:com.wsscode.pathom3.connect.planner/graph :com.wsscode.pathom3.connect.planner/mutations f0 :params]
                             assoc param-key param-value))))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn env->params_DEPRECATED
  ; @important
  ; Deprecated due to the changes of the 'env->mutation-params' function that now requires the mutation name to be provided.
  ;
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

(defn env->param_DEPRECATED
  ; @important
  ; Deprecated due to the changes of the 'env->mutation-params' function that now requires the mutation name to be provided.
  ;
  ; @param (map) env
  ; @param (keyword) param-key
  ;
  ; @usage
  ; (env->param {...} :my-param)
  ;
  ; @return (*)
  [env param-key]
  (-> env env->params_DEPRECATED param-key))

(defn env<-param_DEPRECATED
  ; @important
  ; Deprecated due to the introduction of the 'env<-mutation-param' function that requires the mutation name to be provided.
  ;
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
