#!/bin/bash

set -e
set -x

DIR="$( cd "$( dirname "${BASH_SOURCE[0]}" )" >/dev/null 2>&1 && pwd )"
cd "$DIR"

export SDKROOT="/Library/Developer/CommandLineTools/SDKs/MacOSX.sdk"
# CEF_DIR="/Volumes/My Passport for Mac/backup/cef/cef_binary_88.1.6+g4fe33a1+chromium-88.0.4324.96_macosx64"
# CEF_DIR="/Users/adrian/Downloads/cef_binary_88.1.6+g4fe33a1+chromium-88.0.4324.96_macosarm64_minimal"
ARCH="$1"
PLATFORM="macos"
CEF_DIR="$2"
CEF_DLL="$CEF_DIR/libcef_dll_wrapper/libcef_dll_wrapper.a"

# # # test
# clang++ \
#     -I "$CEF_DIR" \
#     -framework OpenGL \
#     -framework Cocoa \
#     -framework IOKit \
#     -framework CoreFoundation \
#     -framework CoreVideo \
#     -framework AppKit \
#     -framework CoreGraphics \
#     -framework CoreServices \
#     -framework Foundation \
#     -mmacosx-version-min=10.11 \
#     "$CEF_DLL" \
#     -std=c++17 \
#     -DTEST \
#     -o ceftest \
#     thirdparty/backupsignalhandlers/signal_restore_posix.cpp \
#     cefclj.cpp


# ./compile_macosx.sh arm64 /tmp/com.phronemophobic.cef/cef_binary_88.1.6+g4fe33a1+chromium-88.0.4324.96_macosarm64_minimal/

ls /tmp/com.phronemophobic.cef
ls /private/tmp/com.phronemophobic.cef

pushd "$CEF_DIR"

cmake -DCMAKE_BUILD_TYPE=Release -G "Unix Makefiles" -DPROJECT_ARCH="$ARCH" .
make

popd

clang++ \
    -arch "$ARCH" \
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
    "$CEF_DLL" \
    "$CEF_DIR/Release/cef_sandbox.a" \
    -std=c++17 \
    -DHELPER \
    -o "ceflib Helper" \
    thirdparty/backupsignalhandlers/signal_restore_posix.cpp \
    getdir.mm \
    cefclj.cpp


#dylib

clang++ \
    -arch "$ARCH" \
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
    "$CEF_DLL" \
    -std=c++17 \
    -dynamiclib \
    -o libcljcef.dylib \
    thirdparty/backupsignalhandlers/signal_restore_posix.cpp \
    getdir.mm \
    cefclj.cpp


BUILD_DIR=build-"$PLATFORM"-"$ARCH"
mkdir -p "$BUILD_DIR"
cp libcljcef.dylib "$BUILD_DIR"
cp 'ceflib Helper' "$BUILD_DIR"

echo 'done'
