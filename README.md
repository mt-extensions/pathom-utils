
# pathom-api

### Overview

The <strong>pathom-api</strong> is an EQL query handler based on the [wilkerlucio / pathom3]
library.

### deps.edn

```
{:deps {bithandshake/pathom-api {:git/url "https://github.com/bithandshake/pathom-api"
                                 :sha     "xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx"}}}
```

### Current version

Check out the latest commit on the [release branch](https://github.com/bithandshake/pathom-api/tree/release).

### Documentation

The <strong>pathom-api</strong> functional documentation is [available here](https://bithandshake.github.io/pathom-api).

### Changelog

You can track the changes of the <strong>pathom-api</strong> library [here](CHANGES.md).

# Usage

> Some parameters of the following functions and some further functions are not discussed in this file.
  To learn more about the available functionality, check out the [functional documentation](documentation/COVER.md)!

### Index

- [How to register server-side query handlers?](#how-to-register-server-side-query-handlers)


### How to register server-side query handlers?

The [`pathom.api/reg-handler!`](documentation/clj/pathom/API.md#reg-handler)
function registers a Pathom (mutation or resolver) handler.

```
(com.wsscode.pathom3.connect.operation/defmutation my-mutation [env] ...)
(reg-handler! my-mutation)
```

```
(com.wsscode.pathom3.connect.operation/defresolver my-resolver [env] ...)
(reg-handler! my-resolver)
```

> If you are using a code reloader development tool that reloads namespaces after
  they are being changed it's important to use unique IDs for the registered handlers
  because the `reg-handler!` function updates the handlers when the containing
  namespace reloads and not using a unique ID causes duplicated handlers in the
  Pathom environment when the `reg-handler!` function called repeatedly!

```
(com.wsscode.pathom3.connect.operation/defmutation my-mutation [env] ...)
(reg-handler! :my-mutation my-mutation)
```

```
(com.wsscode.pathom3.connect.operation/defresolver my-resolver [env] ...)
(reg-handler! :my-resolver my-resolver)
```

The [`pathom.api/reg-handlers!`](documentation/clj/pathom/API.md#reg-handlers)
function registers one or more Pathom (mutation or resolver) handlers.

```
(com.wsscode.pathom3.connect.operation/defmutation my-mutation [env] ...)
(com.wsscode.pathom3.connect.operation/defresolver my-resolver [env] ...)
(reg-handlers! [my-mutation my-resolver])
```

```
(com.wsscode.pathom3.connect.operation/defmutation my-mutation [env] ...)
(com.wsscode.pathom3.connect.operation/defresolver my-resolver [env] ...)
(reg-handlers! ::my-handlers [my-mutation my-resolver])
```
