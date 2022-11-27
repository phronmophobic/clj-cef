# browser

Example of building a very simplistic browser using membrane+skija.

## Framework dependencies

The cef framework is about 80MB compressed (~230MB uncompressed) which makes it a poor fit including within the library jar. On linux, the CEF framework is 300MB compressed and 1GB uncompressed.

Download the framework. This may take a while.

```sh
clojure -Sdeps '{:deps {com.phronemophobic/clirun {:mvn/version "1.0.0"}}}' -M -m com.phronemophobic.clirun com.phronemophobic.cef/download-and-extract-framework
```

## Usage

    $ clj -M:run-m
