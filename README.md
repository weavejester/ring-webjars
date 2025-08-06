# Ring-WebJars [![Build Status](https://github.com/weavejester/ring-webjars/actions/workflows/test.yml/badge.svg)](https://github.com/weavejester/ring-webjars/actions/workflows/test.yml)

Ring middleware to serve static assets from [WebJars][].

[webjars]: http://www.webjars.org/

## Installation

Add the following dependency to your deps.edn file:

    ring-webjars/ring-webjars {:mvn/version "0.3.0"}

Or to your Leiningen project file:

    [ring-webjars "0.3.0"]

## Usage

Require the middleware and add it to your handler.

```clojure
(require '[ring.middleware.webjars :refer [wrap-webjars]])

(def app (wrap-webjars handler)
```

WebJar assets will then be served from the following paths:

    /assets/<webjar>/<asset path>
    /assets/<webjar>/<version>/<asset path>

For example, if you include version 5.3.3 of the `org.webjars/bootstrap`
dependency, then the minified bootstrap CSS will be available at both:

    /assets/bootstrap/css/bootstrap.min.css
    /assets/bootstrap/5.3.3/css/bootstrap.min.css

By default assets are placed on the `/assets` path. You can change
the path by specifying a second argument.

```clojure
(def app (wrap-webjars handler "/webjars"))
```

## License

Copyright © 2025 James Reeves

Released under the MIT license.
