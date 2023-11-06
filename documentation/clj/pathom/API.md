
### pathom.api

Functional documentation of the pathom.api Clojure namespace

---

##### [README](../../../README.md) > [DOCUMENTATION](../../COVER.md) > pathom.api

### Index

- [env->mutation-params](#env-mutation-params)

- [env->param](#env-param)

- [env->params](#env-params)

- [env->request](#env-request)

- [env->resolver-params](#env-resolver-params)

- [env<-param](#env-param)

- [process-query!](#process-query)

- [process-request!](#process-request)

- [quit-debug-mode!](#quit-debug-mode)

- [reg-handler!](#reg-handler)

- [reg-handlers!](#reg-handlers)

- [set-debug-mode!](#set-debug-mode)

- [toggle-debug-mode!](#toggle-debug-mode)

---

### env->mutation-params

```
@param (map) env
```

```
@usage
(env->mutation-params {...})
```

```
@return (map)
```

<details>
<summary>Source code</summary>

```
(defn env->mutation-params
  [env]
  (or (-> env :com.wsscode.pathom3.connect.planner/graph :com.wsscode.pathom3.connect.planner/mutations first :params)
      (-> {})))
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [pathom.api :refer [env->mutation-params]]))

(pathom.api/env->mutation-params ...)
(env->mutation-params            ...)
```

</details>

---

### env->param

```
@param (map) env
@param (keyword) param-key
```

```
@usage
(env->param {...} :my-param)
```

```
@return (*)
```

<details>
<summary>Source code</summary>

```
(defn env->param
  [env param-key]
  (-> env env->params param-key))
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [pathom.api :refer [env->param]]))

(pathom.api/env->param ...)
(env->param            ...)
```

</details>

---

### env->params

```
@param (map) env
```

```
@usage
(env->params {...})
```

```
@return (map)
```

<details>
<summary>Source code</summary>

```
(defn env->params
  [env]
  (if (-> env env->resolver-params empty? not)
      (-> env env->resolver-params)
      (-> env env->mutation-params)))
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [pathom.api :refer [env->params]]))

(pathom.api/env->params ...)
(env->params            ...)
```

</details>

---

### env->request

```
@param (map) env
{:request (map)}
```

```
@usage
(env->request {...})
```

```
@return (map)
```

<details>
<summary>Source code</summary>

```
(defn env->request
  [env]
  (:request env))
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [pathom.api :refer [env->request]]))

(pathom.api/env->request ...)
(env->request            ...)
```

</details>

---

### env->resolver-params

```
@param (map) env
```

```
@usage
(env->resolver-params {...})
```

```
@return (map)
```

<details>
<summary>Source code</summary>

```
(defn env->resolver-params
  [env]
  (-> env pathom.co/params))
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [pathom.api :refer [env->resolver-params]]))

(pathom.api/env->resolver-params ...)
(env->resolver-params            ...)
```

</details>

---

### env<-param

```
@param (map) env
@param (keyword) param-key
@param (*) param-value
```

```
@usage
(env<-param {...} :my-param "My value")
```

```
@return (map)
```

<details>
<summary>Source code</summary>

```
(defn env<-param
  [env param-key param-value]
  (assoc-in env [:com.wsscode.pathom3.connect.planner/node :com.wsscode.pathom3.connect.planner/params param-key]
            param-value))
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [pathom.api :refer [env<-param]]))

(pathom.api/env<-param ...)
(env<-param            ...)
```

</details>

---

### process-query!

```
@param (map) environment
@param (*) query
```

```
@usage
(process-query! my-environment my-query)
```

```
@return (map)
```

<details>
<summary>Source code</summary>

```
(defn process-query!
  [environment query]
  (pathom.eql/process environment query))
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [pathom.api :refer [process-query!]]))

(pathom.api/process-query! ...)
(process-query!            ...)
```

</details>

---

### process-request!

```
@param (map) request
```

```
@usage
(process-request! {...})
```

```
@return (map)
```

<details>
<summary>Source code</summary>

```
(defn process-request!
  [request]
  (let [query       (query.utils/request->query request)
        environment (assoc @register.state/ENVIRONMENT :request request)]
       (process-query! environment query)))
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [pathom.api :refer [process-request!]]))

(pathom.api/process-request! ...)
(process-request!            ...)
```

</details>

---

### quit-debug-mode!

```
@description
Turns the debug mode off.
```

```
@usage
(quit-debug-mode!)
```

<details>
<summary>Source code</summary>

```
(defn quit-debug-mode!
  []
  (reset! debug.state/DEBUG-MODE? false))
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [pathom.api :refer [quit-debug-mode!]]))

(pathom.api/quit-debug-mode!)
(quit-debug-mode!)
```

</details>

---

### reg-handler!

```
@param (keyword)(opt) handler-id
@param (handler function) handler-f
```

```
@usage
(pco/defmutation my-mutation [env] ...)
(reg-handler! ::handler my-mutation)
```

```
@usage
(pco/defresolver my-resolver [env] ...)
(reg-handler! ::handler my-resolver)
```

<details>
<summary>Source code</summary>

```
(defn reg-handler!
  ([handler-f]
   (reg-handler! (random/generate-keyword) handler-f))

  ([handler-id handler-f]
   (swap! register.state/HANDLERS assoc handler-id handler-f)
   (reset-environment!)))
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [pathom.api :refer [reg-handler!]]))

(pathom.api/reg-handler! ...)
(reg-handler!            ...)
```

</details>

---

### reg-handlers!

```
@param (keyword)(opt) handlers-id
@param (handler functions in vector) handler-fs
```

```
@usage
(pco/defmutation my-mutation [env] ...)
(pco/defresolver my-resolver [env] ...)
(def HANDLERS [my-mutation my-resolver])
(reg-handlers! ::handlers HANDLERS)
```

<details>
<summary>Source code</summary>

```
(defn reg-handlers!
  ([handler-fs]
   (reg-handlers! (random/generate-keyword) handler-fs))

  ([handlers-id handler-fs]
   (swap! register.state/HANDLERS assoc handlers-id handler-fs)
   (reset-environment!)))
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [pathom.api :refer [reg-handlers!]]))

(pathom.api/reg-handlers! ...)
(reg-handlers!            ...)
```

</details>

---

### set-debug-mode!

```
@description
Turns the debug mode on.
```

```
@usage
(set-debug-mode!)
```

<details>
<summary>Source code</summary>

```
(defn set-debug-mode!
  []
  (reset! debug.state/DEBUG-MODE? true))
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [pathom.api :refer [set-debug-mode!]]))

(pathom.api/set-debug-mode!)
(set-debug-mode!)
```

</details>

---

### toggle-debug-mode!

```
@description
Toggles the debug mode on/off.
```

```
@usage
(toggle-debug-mode!)
```

<details>
<summary>Source code</summary>

```
(defn toggle-debug-mode!
  []
  (swap! debug.state/DEBUG-MODE? not))
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [pathom.api :refer [toggle-debug-mode!]]))

(pathom.api/toggle-debug-mode!)
(toggle-debug-mode!)
```

</details>

---

<sub>This documentation is generated with the [clj-docs-generator](https://github.com/bithandshake/clj-docs-generator) engine.</sub>

