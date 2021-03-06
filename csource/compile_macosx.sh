#!/bin/bash

set -e
set -x

DIR="$( cd "$( dirname "${BASH_SOURCE[0]}" )" >/dev/null 2>&1 && pwd )"
cd "$DIR"

export SDKROOT="/Library/Developer/CommandLineTools/SDKs/MacOSX.sdk"
CEF_DIR="/Volumes/My Passport for Mac/backup/cef/cef_binary_88.1.6+g4fe33a1+chromium-88.0.4324.96_macosx64"

# test
clang++ \
    -I "$CEF_DIR" \
    -framework OpenGL \
    -framework Cocoa \
    -framework IOKit \
    -framework CoreFoundation \
    -framework CoreVideo \
    -framework AppKit \
    -framework CoreGraphics \
    -framework CoreServices \
    -framework Foundation \
    -mmacosx-version-min=10.11 \
    "$CEF_DIR/build/libcef_dll_wrapper/Debug/libcef_dll_wrapper.a" \
    -std=c++17 \
    -DTEST \
    -o ceftest \
    thirdparty/backupsignalhandlers/signal_restore_posix.cpp \
    cefclj.cpp

clang++ \
    -I "$CEF_DIR" \
    -framework OpenGL \
    -framework Cocoa \
    -framework IOKit \
    -framework CoreFoundation \
    -framework CoreVideo \
    -framework AppKit \
    -framework CoreGraphics \
    -framework CoreServices \
    -framework Foundation \
    -mmacosx-version-min=10.11 \
    "$CEF_DIR/build/libcef_dll_wrapper/Debug/libcef_dll_wrapper.a" \
    "$CEF_DIR/Debug/cef_sandbox.a" \
    -std=c++17 \
    -DHELPER \
    -o "ceflib Helper" \
    thirdparty/backupsignalhandlers/signal_restore_posix.cpp \
    getdir.mm \
    cefclj.cpp


#dylib

clang++ \
    -I "$CEF_DIR" \
    -framework OpenGL \
    -framework Cocoa \
    -framework IOKit \
    -framework CoreFoundation \
    -framework CoreVideo \
    -framework AppKit \
    -framework CoreGraphics \
    -framework CoreServices \
    -framework Foundation \
    -mmacosx-version-min=10.11 \
    "$CEF_DIR/build/libcef_dll_wrapper/Debug/libcef_dll_wrapper.a" \
    -std=c++17 \
    -dynamiclib \
    -o libcljcef.dylib \
    thirdparty/backupsignalhandlers/signal_restore_posix.cpp \
    getdir.mm \
    cefclj.cpp


cp libcljcef.dylib ../resources/darwin
cp 'ceflib Helper' ../resources/darwin

echo 'done'
