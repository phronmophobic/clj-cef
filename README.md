# clj-cef

Clojure bindings for the Chromium Embedded Framework

Dependency:

[![Clojars Project](https://img.shields.io/clojars/v/com.phronemophobic/clj-cef.svg)](https://clojars.org/com.phronemophobic/clj-cef)

## Rationale


From <https://bitbucket.org/chromiumembedded/cef/src/master/>

> Some use cases for CEF include:
> 
>- Embedding an HTML5-compliant Web browser control in an existing native application.
>- Creating a light-weight native “shell” application that hosts a user interface developed primarily using Web technologies.
>- Rendering Web content “off-screen” in applications that have their own custom drawing frameworks.
>- Acting as a host for automated testing of existing Web properties and applications.

The purpose of clj-cef is to make all of these features available from clojure. The current priority is to expose as much cef functionality as possible. As such, the current API tries to match the underlying cef c api as closely as possible. Future versions or projects may provide more idiomatic clojure wrapping.

We're typically spoiled by clojure libraries. Most libraries are made simple. They have worry free threading models. The JVM handles memory management. This is not one of those libraries. `clj-cef` is a thin wrapper around cef which has sharp edges. It also provides a huge amount of access and functionality that isn't really available otherwise. As much as possible, `clj-cef` tries to make correct code the path of least resistance while also providing as much access to the underlying functionality. If you have questions, drop by the #clj-cef channel on the clojurian's slack.

Currently, clj-cef will run on Mac OSX and linux.

## Naming

The clojure and Java wrappers are generated from the cef header files. Translation of names and callbacks are as follows:

| Struct                            | Naming               | Prefix  | Suffix | Example         | package/namespace         |
| --                                | --                   | --      | --     | --              | --                        |
| cef c struct                      | cef\_struct\_name\_t | cef_    | \_t    | cef\_browser\_t |                           |
| Java Classes wrapping cef Structs | CamelCase            | Cef     |        | CefBrowser      | com.phronemophobic.cljcef |
| clojure struct creation           | map->struct-name     | map->   |        | map->browser    | com.phronemophobic.cef    |
| clojure struct manipulation       | merge->struct-name   | merge-> |        | merge->browser  | com.phronemophobic.cef    |

| Callback                        | Naming    | Example   |
| --                              | --        | --        |
| cef c struct callback           | func_name | load\_url |
| Java methods wrapping callbacks | camelCase | loadUrl   |


## Threading

In general, most cef functions expect to be called on **the** main thread unless otherwise documented. On Mac OSX, the main thread is a very specific thread. If cef functions are called on the wrong thread, it will crash the jvm.

Tasks can be run on the main thread using `com.phronemophobic.cinterop/dispatch-sync` or `com.phronemophobic.cinterop/dispatch-async`.

```clojure

(require '[com.phronemophobic.cinterop
           :refer [dispatch-sync
                   dispatch-async]])

;; synchronous. will return when the task completes
(dispatch-sync
 (fn []
   (cef/prepare-environment!)))

;; asynchronous. will return immediately
(dispatch-async
 (fn []
   (cef/prepare-environment!)))
```

## Linux Dependencies

```sh
apt install xvfb libatk1.0-dev libatk-bridge2.0-dev libxkbcommon-dev libxcomposite-dev libxrandr-dev libgbm-dev
```

When running cef on linux without a display, use xvfb. The easiest way to use xvfb is to prefix command line commands with `xvfb-run`. See https://magpcss.org/ceforum/viewtopic.php?t=16993 for more information. 

## Memory Management

All Cef* structs are automatically reference counted and managed by `clj-cef`. However, non Cef* struct data received from callbacks or provided to cef are not.

To prevent from crashing the JVM, follow these rules:
- Retain references to any non Cef* struct data provided in calls to cef
- Make copies of any data received from cef callbacks that will outlast the callback call. 

## Usage

Note:If running on linux without a display, prefix cli commands with `xvfb-run`.

All the examples will use the following requires.

```clojure
(:require [com.phronemophobic.cef :as cef
             :refer [print-doc]]
            [com.phronemophobic.cinterop
             :refer [dispatch-sync
                     dispatch-async]])
```

The main steps for using `clj-cef` are:
1. Download and extract the Chromium Embedded Framework
2. Initialize cef.
3. Use the library

### 1. Download and extract the Chromium Embedded Framework

The cef framework is about 80MB compressed (~230MB uncompressed) which makes it a poor fit including within the library jar. On linux, 300MB compressed and 1GB uncompressed.

```clojure
;; will download and extract cef framework to /tmp/com.phronemophobic.cef/
(cef/dispatch-sync
 (fn []
   (cef/download-and-prepare-environment!)))

;; or 
;; provide target dir
;; (cef/dispatch-sync
;;  (fn []
;;    (cef/download-and-prepare-environment! target-dir)))
```

### 2. Initialize cef


```clojure
(cef/dispatch-sync
 (fn []
   (let [app (cef/map->app
              {:get-browser-process-handler
               (fn [this]
                 (cef/map->browser-process-handler
                  {:on-context-initialized
                   (fn [this]
                     (println "initialized!"))}))})]
     (cef/cef-initialize app))))
```

If you used a custom target directory for cef, pass the target dir to `cef/cef-initialize` like so: `(cef/cef-initialize app target-dir)`.

Note: clj-cef is running in the browser process (see [processes](https://bitbucket.org/chromiumembedded/cef/wiki/GeneralUsage#markdown-header-processes)). A generic render process is used automatically.

### 3. Use the library

The cef library has a huge amount of functionality. For examples check out:

[browser](examples/browser): A simple browser UI using membrane+skija.  
[htmltoimage](examples/htmltoimage): A cli for converting a url to an image.  
[cssinspect](examples/cssinspect): A cli for calculating css coverage and finding unused css for a url.  

## Cef message loop

The browser does its work in a message loop. There are two ways to run the message loop:

1. `cef-run-message-loop`

```clojure
(dispatch-async
 (fn []
   (cef/cef-run-message-loop)))
```

Calling `cef-run-message-loop` will take over the main thread until `cef-quit-message-loop` is called (`dispatch-sync` and `dispatch-async` tasks won't be run until the message loop quits).

Tasks can be posted to the cef message loop using `cef/post-task-to-main`:

```clojure
(cef/post-task-to-main
 (fn []
   (.loadUrl (.getMainFrame browser) url)))
```

```clojure
;; quit the message loop
(cef/post-task-to-main cef/cef-quit-message-loop)
```

Using `cef-run-message-loop` is the easiest way to make sure the message loop is being pumped, but it's less flexible. Additionally, since it takes over **the** main thread, other resources that require **the** main thread (eg. any UI code like skija, javafx, swing, etc.) will be starved.

2. `cef-do-message-loop-work`

The `cef-do-message-loop-work` function will perform a single iteration of CEF message loop work. This method is more flexible and makes it possible to integrate cef with other projects that also require running on **the** main thread.

```clojure
(dispatch-sync
 (fn []
   (.loadUrl (.getMainFrame browser) url)
   (doseq [i (range 15)
           :while (= 1 (.isLoading browser))]
     (cef/cef-do-message-loop-work)
     (Thread/sleep 500))))
```

For more info, check out the [examples](examples/) or cef docs.
[Message loop docs](https://bitbucket.org/chromiumembedded/cef/wiki/GeneralUsage#markdown-header-message-loop-integration)

## Documentation

All `com.phronemophobic.cef/map->`* and `com.phronemophobic.cef/merge->`* functions have the comments provided by their wrapped counter parts from the cef library. Additionally, all Cef* struct instances can have their documentation printed or retrieved using `com.phronemophobic.cef/print-doc` and `com.phronemophobic.cef/doc` respectively.

[CEF Project page](https://bitbucket.org/chromiumembedded/cef/src/master/)  
[Cef API docs](https://bitbucket.org/chromiumembedded/cef/src/master/)  
[clj-cef reference docs](https://phronmophobic.github.io/clj-cef/)  
[Examples](examples/)  

 If you have questions, drop by the #clj-cef channel on the clojurian's slack.

## Scary Keychain popup

> java wants to use your confidential information stored in "Chromium Safe Storage" in your keychain.

You can deny and things will continue to work, but cookies might not be encrypted. It's unclear based off the available documentation.

See https://bitbucket.org/chromiumembedded/cef/issues/2692/mac-networkservice-allow-custom-service.

Per the issue:
> This prompt can be disabled and cookies will not be encrypted if you pass the --use-mock-keychain command-line flag.

For example:
```clojure

;; assuming app created during setup

;; modify the CefApp
(cef/merge->app app
                {:on-before-command-line-processing
                 (fn [app s command-line]
                   (.appendSwitch command-line "--use-mock-keychain"))})

;; initialize as normal
(cef/cef-initialize app)

```

## FAQ

### Why aren't my callbacks being called?

You need to pump the cef event loop with either `cef/cef-run-message-loop` or `cef/cef-do-message-loop-work`.

### Why am I getting "Unable to open X display"?

Cef requires an X display on linux. Use xvfb. See linux docs above and https://magpcss.org/ceforum/viewtopic.php?t=16993 for more information.

### Why not java-cef?

* java-cef wraps cef which wraps the Chromium library. The c api provided by cef is fairly clojure friendly (thanks to JNA). 
* the java wrapper doesn't make accessing cef from clojure any easier.
* java-cef is hard to build, https://bitbucket.org/chromiumembedded/java-cef/wiki/BranchesAndBuilding
* java-cef enforces a rigid application structure, https://bitbucket.org/chromiumembedded/cef/wiki/GeneralUsage#markdown-header-mac-os-x
* java-cef is not repl friendly

### Why is `get-render-process-handler` never called?

the render process is a completely different OS process. While providing a render process handler using `clj-cef` is technically possible, it's not very straightforward. Fortuately, most interesting functionality can be accessed solely from the browser process. If you need a render process handler, please file an issue.

## Easy ways to crash the JVM

- Call Cef functions before preparing or initializing cef funcions
- Calling cef function on the wrong thread
- Passing data to cef that may be garbage collected before it's done being used
- Holding onto data that may be freed
- Pay careful attention to the expected return types of callbacks.

# License

Copyright 2021 Adrian Smith. clj-cef is licensed under Apache License v2.0.
