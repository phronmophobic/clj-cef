# htmltoimage

Example of using clj-cef to convert a URL to an image.

## Usage

Basic usage:

    $ clj -X clj-cef.htmltoimage/-main :url '"https://blog.phronemophobic.com/what-is-a-user-interface.html"'
    
Extra options:

    $ clj -X clj-cef.htmltoimage/-main :url '"https://blog.phronemophobic.com/what-is-a-user-interface.html"' :size '[1000 300]' :out '"mysite.png"'

## License

Copyright © 2021 Adrian

Distributed under the Eclipse Public License version 1.0.
