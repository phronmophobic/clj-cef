#!/bin/bash

find javasrc -name '*.java' -print0 | xargs -0 javac -d classes -cp `clj -Spath` 
