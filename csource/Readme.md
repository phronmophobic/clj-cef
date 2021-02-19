


## Building libcljcef.dylib

1. Grab a copy of cef binary
```
mkdir build
cd build
cmake -G "Xcode" -DPROJECT_ARCH="x86_64" ..
```

2. Compile the project using XCode

3. Update `compile_macosx.sh` with correct paths

4. Run `./compile_macosx.sh`


## Generate cef.json

Python's clang binding should be installed.

For example:

```
sudo port install py38-clang
```

Update base path in `parse_cef_api.py`

Run `parse_cef_api.py`

    $ python parse_cef_api.py
    
    
clj-cef/resources/cef.json should be updated.

# Sandbox

Chromium's sandbox requires the sandboxed subprocesses to be in the bundle directory of the main app. Since the "main app" is java, this is a problem. As a workaround, we swizzle NSBundle's `mainBundle` method with a new one that points to the target bundle dir for our app. By default, the target dir is `/tmp/com.phronemophobic.cef/`. See [getdir.mm](getdir.mm)

See below for the sandbox policy.

https://github.com/chromium/chromium/blob/master/sandbox/policy/mac/common.sb
