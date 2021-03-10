# htmltoimage

Example of using clj-cef to convert a URL to an image.

## Linux Dependencies

```sh
apt install xvfb libatk1.0-dev libatk-bridge2.0-dev libxkbcommon-dev libxcomposite-dev libxrandr-dev libgbm-dev
```

## Framework dependencies

The cef framework is about 80MB compressed (~230MB uncompressed) which makes it a poor fit including within the library jar. On linux, the CEF framework is 300MB compressed and 1GB uncompressed.

Download the framework. This may take a while.

```sh
clojure -Sdeps '{:deps {com.phronemophobic/clirun {:mvn/version "1.0.0"}}}' -M -m com.phronemophobic.clirun com.phronemophobic.cef/download-and-extract-framework
```

## Usage

Note:If running on linux without a display, prefix commands with `xvfb-run`.

Basic usage:

    $ clojure -X clj-cef.htmltoimage/-main :url '"https://blog.phronemophobic.com/what-is-a-user-interface.html"'

With xvfb:

    $ xvfb-run clojure -X clj-cef.htmltoimage/-main :url '"https://blog.phronemophobic.com/what-is-a-user-interface.html"'
    
Extra options:

    $ clojure -X clj-cef.htmltoimage/-main :url '"https://blog.phronemophobic.com/what-is-a-user-interface.html"' :size '[1000 300]' :out '"mysite.png"'

## License

Copyright © 2021 Adrian

Distributed under the Eclipse Public License version 1.0.
