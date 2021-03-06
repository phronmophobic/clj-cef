// Copyright (c) 2013 The Chromium Embedded Framework Authors. All rights
// reserved. Use of this source code is governed by a BSD-style license that
// can be found in the LICENSE file.

// #include "tests/cefsimple/simple_app.h"
#include "include/capi/cef_app_capi.h"
#include "thirdparty/backupsignalhandlers/signal_restore_posix.h"

#if defined(HELPER)

    // https://stackoverflow.com/questions/4770985/how-to-check-if-a-string-starts-with-another-string-in-c/4770992#4770992
bool prefix(const char *pre, const char *str)
{
    return strncmp(pre, str, strlen(pre)) == 0;
}


// Entry point function for all processes.
int main(int argc, char* argv[]) {

    // Main args.
    cef_main_args_t main_args = {};
    main_args.argc = argc;
    main_args.argv = argv;
    
    // cef_app_t app = {};
    // initialize_cef_app(&app);


    char empty_arg[] = "";

    for ( int i = 0; i < argc; i ++){
      printf("arg %d: %s\n", i, argv[i]);
      // if (prefix("--gpu-preferences", argv[i]) ||
      //     prefix("--use-gl", argv[i])){
        
      //   argv[i] = empty_arg;
      // }
    }


    
    // Execute subprocesses. It is also possible to have
    // a separate executable for subprocesses by setting
    // cef_settings_t.browser_subprocess_path. In such
    // case cef_execute_process should not be called here.
    // printf("cef_execute_process, argc=%d\n", argc);
    int code = cef_execute_process(&main_args, NULL, NULL);
    if (code >= 0) {
      exit(code);
    }

  return 0;
}

#endif

extern "C"{


    cef_string_t* cef_string_t_create(){
        return new cef_string_t();
    }
    void cef_string_t_destroy(cef_string_t* s){
        delete s;
    }

    int _cef_load_library(const char* path){
      return 1;
      // return cef_load_library(path);
    }

    void change_bundle_path(const char* bundle_path){
    }


    int _cef_initialize(cef_main_args_t* main_args, cef_settings_t* settings, cef_app_t* app, void* sandbox_info){ 

      for ( int i = 0; i < main_args->argc; i ++){
        printf("arg %d: %s\n", i, main_args->argv[i]);
        // if (prefix("--gpu-preferences", argv[i]) ||
        //     prefix("--use-gl", argv[i])){
        
        //   argv[i] = empty_arg;
        // }
      }

      printf("windowless: %d\n", settings->windowless_rendering_enabled);

        // #if defined(OS_POSIX)
        // CefInitialize will reset signal handlers. Backup/restore the original
        // signal handlers to avoid crashes in the JVM (see issue #41).
        BackupSignalHandlers();
        // #endif


        // Initialize CEF in the main process.
        int ret = cef_initialize(main_args, settings, app, sandbox_info);

        // #if defined(OS_POSIX)
        RestoreSignalHandlers();
        // #endif

        return ret;

    }
}
