#!/bin/bash

set -e
set -x

DIR="$( cd "$( dirname "${BASH_SOURCE[0]}" )" >/dev/null 2>&1 && pwd )"
cd "$DIR"

CEF_DIR='/home/adrian2/workspace/cef_binary_88.2.4+gf3c4ca9+chromium-88.0.4324.150_linux64'

    # "$CEF_DIR/Debug/cef_sandbox.a" \
clang++ \
    -I "$CEF_DIR" \
    -L "$CEF_DIR/Release" \
    -lcef \
    -std=c++17 \
    -DHELPER \
    "-Wl,-rpath,/tmp/com.phronemophobic.cef/" \
    -o "ceflib Helper" \
    thirdparty/backupsignalhandlers/signal_restore_posix.cpp \
    cef_linux.cpp


#dylib

clang++ \
    -fPIC \
    -I "$CEF_DIR" \
    -L "$CEF_DIR/Release" \
    -lcef \
    -std=c++17 \
    -shared \
    -o libcljcef.so \
    thirdparty/backupsignalhandlers/signal_restore_posix.cpp \
    cef_linux.cpp


cp libcljcef.so ../resources/extract/linux-x86-64
cp "ceflib Helper" ../resources/extract/linux-x86-64

echo "done"
