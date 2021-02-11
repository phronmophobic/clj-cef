#include "cefclj.h"

#include <string>
#include <sstream>

#include "include/base/cef_bind.h"

#include "include/cef_app.h"
#include "include/cef_browser.h"
#include "include/cef_command_line.h"
#include "include/cef_parser.h"

#include "include/capi/cef_app_capi.h"

#include "include/views/cef_browser_view.h"
#include "include/views/cef_window.h"

#include "include/wrapper/cef_closure_task.h"
#include "include/wrapper/cef_helpers.h"
#include "include/wrapper/cef_library_loader.h"

#include "thirdparty/backupsignalhandlers/signal_restore_posix.h"

void log(const char* s){

    FILE *fp = fopen("/tmp/test.txt", "w+");fprintf(fp, "%s\n", s); fclose(fp);
}


#if defined(HELPER)
#include "include/cef_sandbox_mac.h"
#endif




extern "C"{


    cef_string_t* cef_string_t_create(){
        return new cef_string_t();
    }
    void cef_string_t_destroy(cef_string_t* s){
        delete s;
    }

    int _cef_load_library(const char* path){
        return cef_load_library(path);
    }


    int _cef_initialize(cef_main_args_t* main_args, cef_settings_t* settings, cef_app_t* app, void* sandbox_info){ 

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





#if defined(TEST)
int main(int argc, char* argv[]) {

    return 0;
}
#endif


#if defined(HELPER)
    

// https://stackoverflow.com/questions/4770985/how-to-check-if-a-string-starts-with-another-string-in-c/4770992#4770992
bool prefix(const char *pre, const char *str)
{
    return strncmp(pre, str, strlen(pre)) == 0;
}


    int main(int argc, char* argv[]) {

        // char* framework_dir = NULL;
        char framework_path[255] = "";

        for ( int i = 0; i < argc; i++){
            char* arg = argv[i];
            if ( prefix("--framework-dir-path=", arg)){
                strncpy(framework_path, arg+strlen("--framework-dir-path="), sizeof(framework_path) - 1);

                // strncat(framework_path, "/Chromium Embedded Framework", sizeof(framework_p-strlen("/Chromium Embedded Framework"));

                const char* executable_path = "/Chromium Embedded Framework";

                if (strlen(executable_path) + 1 >
                    sizeof(framework_path) - strlen(framework_path)){

                    fprintf(stderr, "Framework Path is too long.\n");
                    return 1;
                }
                (void)strncat(framework_path, executable_path,
                              sizeof(framework_path) - strlen(framework_path) - 1);

                break;
            }
        }


        CefScopedSandboxContext sandbox_context;
        if (!sandbox_context.Initialize(argc, argv)){
            return 1;
        }

        if (!cef_load_library(framework_path)) {
            fprintf(stderr, "Failed to load the CEF framework.\n");
            return 1;
        }

        // Structure for passing command-line arguments.
        // The definition of this structure is platform-specific.
        CefMainArgs main_args(argc, argv);

        // Execute the sub-process logic. This will block until the sub-process should exit.
        return CefExecuteProcess(main_args, NULL, NULL);
    }


#endif
