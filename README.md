# Ring-WebJars

Ring middleware to serve static assets from [WebJars][].

[webjars]: http://www.webjars.org/

## Installation

Include the following dependency in your `project.clj` file:

    [ring-webjars "0.1.0-SNAPSHOT"]

## Usage

Require the middleware and add it to your handler.

```clojure
(require '[ring.middleware.webjars :refer [wrap-webjars]])

(def app (wrap-webjars handler)
```

WebJar assets will then be served from the following path:

    /assets/<webjar>/<asset path>

For example, if you include the `[org.webjars/bootstrap "3.3.1"]`
dependency, then the minified bootstrap CSS will be available at:

    /assets/bootstrap/css/bootstrap.min.css

By default assets are placed on the `/assets` path. You can change
the path by specifying a second argument.

```clojure
(def app (web-webjars handler "/webjars"))
```

## License

Copyright © 2014 James Reeves

Distributed under the Eclipse Public License either version 1.0 or (at
your option) any later version.
