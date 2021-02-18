# cssinspect

Example of using clj-cef with core.async to find unused css.

## Usage

Print css coverage stats:

    $ clj -X clj-cef.cssinspect/print-stats :url '"http://clojure.org"' | head -n 11
    loading url: http://clojure.org
    waiting for page to load...
    loading complete.
    calculating coverage...
    -----------------------------
    source: https://clojure.org/css/clojureorg.webflow.css
    bytes covered: 5479
    bytes unused: 11638
    bytes total: 17117
    percent covered: 32.01%
    percent unused: 67.99%

Print unused css:

    $ clj -X clj-cef.cssinspect/print-unused :url '"http://blog.phronemophobic.com/ui-state.html"' > unused.css
    
## License

Copyright © 2021 Adrian Smith

Distributed under the Eclipse Public License version 1.0.
