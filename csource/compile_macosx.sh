#!/bin/bash

set -e
set -x

export SDKROOT="/Library/Developer/CommandLineTools/SDKs/MacOSX.sdk"

    # -I ./libs/glfw-3.3.bin.MACOS/include \
    # -I ./libs/skia \
    # -I ./libs/skia/include/gpu \
    # -I ./libs/skia/include/gpu/gl \
    # -I ./libs/skia/include/core \
    # -I ./libs/skia/include/utils \
    # -I ./libs/skia/include/private \

# clang++ \
#     -framework OpenGL \
#     -framework Cocoa \
#     -framework IOKit \
#     -framework CoreFoundation \
#     -framework CoreVideo \
#     -framework AppKit \
#     -framework CoreGraphics \
#     -framework CoreServices \
#     -framework Foundation \
#     -mmacosx-version-min=10.10 \
#     -dynamiclib \
#     ./libs/skia/out/Static/libskia.a \
#     -std=c++17 \
#     -o libcefclj.dylib \
#     cefclj.cpp

# test
clang++ \
    -I '/Volumes/My Passport for Mac/backup/cef/cef_binary_88.1.6+g4fe33a1+chromium-88.0.4324.96_macosx64' \
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
    '/Volumes/My Passport for Mac/backup/cef/cef_binary_88.1.6+g4fe33a1+chromium-88.0.4324.96_macosx64/build/libcef_dll_wrapper/Debug/libcef_dll_wrapper.a' \
    -std=c++17 \
    -DTEST \
    -o ceftest \
    thirdparty/backupsignalhandlers/signal_restore_posix.cpp \
    cefclj.cpp

clang++ \
    -I '/Volumes/My Passport for Mac/backup/cef/cef_binary_88.1.6+g4fe33a1+chromium-88.0.4324.96_macosx64' \
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
    '/Volumes/My Passport for Mac/backup/cef/cef_binary_88.1.6+g4fe33a1+chromium-88.0.4324.96_macosx64/build/libcef_dll_wrapper/Debug/libcef_dll_wrapper.a' \
    '/Volumes/My Passport for Mac/backup/cef/cef_binary_88.1.6+g4fe33a1+chromium-88.0.4324.96_macosx64/Release/cef_sandbox.a' \
    -std=c++17 \
    -DHELPER \
    -o 'ceftest Helper' \
    thirdparty/backupsignalhandlers/signal_restore_posix.cpp \
    cefclj.cpp


#dylib

clang++ \
    -I '/Volumes/My Passport for Mac/backup/cef/cef_binary_88.1.6+g4fe33a1+chromium-88.0.4324.96_macosx64' \
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
    '/Volumes/My Passport for Mac/backup/cef/cef_binary_88.1.6+g4fe33a1+chromium-88.0.4324.96_macosx64/build/libcef_dll_wrapper/Debug/libcef_dll_wrapper.a' \
    -std=c++17 \
    -dynamiclib \
    -o libcljcef.dylib \
    thirdparty/backupsignalhandlers/signal_restore_posix.cpp \
    cefclj.cpp


cp libcljcef.dylib ../resources/darwin
# cp libmembraneskia.dylib ../resources/darwin

echo 'done'
