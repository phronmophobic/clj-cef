#!/bin/bash

find javasrc -name '*.java' -print0 | xargs -0 javac -target 1.8 -source 1.8 -d classes -cp `clj -Spath`
