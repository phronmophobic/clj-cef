#include "cefclj.h"

#include <string>
#include <sstream>

#include "include/base/cef_bind.h"

#include "include/cef_app.h"
#include "include/cef_browser.h"
#include "include/cef_command_line.h"
#include "include/cef_parser.h"


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

#include "capi-wrappers.h"


namespace {

// When using the Views framework this object provides the delegate
// implementation for the CefWindow that hosts the Views-based browser.
class SimpleWindowDelegate : public CefWindowDelegate {
 public:
  explicit SimpleWindowDelegate(CefRefPtr<CefBrowserView> browser_view)
      : browser_view_(browser_view) {}

  void OnWindowCreated(CefRefPtr<CefWindow> window) OVERRIDE {
    // Add the browser view and show the window.
    window->AddChildView(browser_view_);
    window->Show();

    // Give keyboard focus to the browser view.
    browser_view_->RequestFocus();

    log("window created");
  }

  void OnWindowDestroyed(CefRefPtr<CefWindow> window) OVERRIDE {
    browser_view_ = nullptr;
  }

  bool CanClose(CefRefPtr<CefWindow> window) OVERRIDE {
    // Allow the window to close if the browser says it's OK.
    CefRefPtr<CefBrowser> browser = browser_view_->GetBrowser();
    if (browser)
      return browser->GetHost()->TryCloseBrowser();
    return true;
  }

  CefSize GetPreferredSize(CefRefPtr<CefView> view) OVERRIDE {
    return CefSize(800, 600);
  }

 private:
  CefRefPtr<CefBrowserView> browser_view_;

  IMPLEMENT_REFCOUNTING(SimpleWindowDelegate);
  DISALLOW_COPY_AND_ASSIGN(SimpleWindowDelegate);
};

class SimpleBrowserViewDelegate : public CefBrowserViewDelegate {
 public:
  SimpleBrowserViewDelegate() {}

  bool OnPopupBrowserViewCreated(CefRefPtr<CefBrowserView> browser_view,
                                 CefRefPtr<CefBrowserView> popup_browser_view,
                                 bool is_devtools) OVERRIDE {
    // Create a new top-level Window for the popup. It will show itself after
    // creation.
    CefWindow::CreateTopLevelWindow(
        new SimpleWindowDelegate(popup_browser_view));

    // We created the Window.
    return true;
  }

 private:
  IMPLEMENT_REFCOUNTING(SimpleBrowserViewDelegate);
  DISALLOW_COPY_AND_ASSIGN(SimpleBrowserViewDelegate);
};

}  // namespace

SimpleApp::SimpleApp(render_handler_t _render_handler): render_handler(_render_handler) {}

void SimpleApp::OnContextInitialized() {
    log("context inialized");

  CEF_REQUIRE_UI_THREAD();

  CefRefPtr<CefCommandLine> command_line =
      CefCommandLine::GetGlobalCommandLine();

  const bool enable_chrome_runtime =
      command_line->HasSwitch("enable-chrome-runtime");

  // SimpleHandler implements browser-level callbacks.
  CefRefPtr<SimpleHandler> handler(new SimpleHandler(render_handler));

  // Specify CEF browser settings here.
  CefBrowserSettings browser_settings;

  std::string url;

  // Check if a "--url=" value was provided via the command-line. If so, use
  // that instead of the default URL.
  url = command_line->GetSwitchValue("url");
  if (url.empty())
    url = "https://docs.oracle.com/javase/9/docs/api/java/awt/image/WritableRaster.html";

  CefWindowInfo window_info;
  cef_window_handle_t window_handle = NULL;
  window_info.SetAsWindowless(window_handle);

#if defined(OS_WIN)
    // On Windows we need to specify certain flags that will be passed to
    // CreateWindowEx().
    window_info.SetAsPopup(NULL, "cefsimple");
#endif

    log("creating brwoser window");

    // Create the first browser window.
    CefBrowserHost::CreateBrowser(window_info, handler, url, browser_settings,
                                  nullptr, nullptr);
  
}




namespace {

SimpleHandler* g_instance = nullptr;

// Returns a data: URI with the specified contents.
std::string GetDataURI(const std::string& data, const std::string& mime_type) {
  return "data:" + mime_type + ";base64," +
         CefURIEncode(CefBase64Encode(data.data(), data.size()), false)
             .ToString();
}

}  // namespace

SimpleHandler::SimpleHandler(render_handler_t _render_handler)
    :  is_closing_(false), render_handler(_render_handler)
{
  DCHECK(!g_instance);
  g_instance = this;
  this->renderHandler = new SimpleRenderHandler(render_handler);
}

SimpleHandler::~SimpleHandler() {
  g_instance = nullptr;
  this->renderHandler = nullptr;
}

// static
SimpleHandler* SimpleHandler::GetInstance() {
  return g_instance;
}


CefRefPtr<CefRenderHandler> SimpleHandler::GetRenderHandler(){
    return this->renderHandler;
}

void SimpleHandler::OnTitleChange(CefRefPtr<CefBrowser> browser,
                                  const CefString& title) {
  CEF_REQUIRE_UI_THREAD();

  log("setting browser title");


    PlatformTitleChange(browser, title);
}

void SimpleHandler::OnAfterCreated(CefRefPtr<CefBrowser> browser) {
  CEF_REQUIRE_UI_THREAD();

  // Add to the list of existing browsers.
  browser_list_.push_back(browser);
}

bool SimpleHandler::DoClose(CefRefPtr<CefBrowser> browser) {
  CEF_REQUIRE_UI_THREAD();

  // Closing the main window requires special handling. See the DoClose()
  // documentation in the CEF header for a detailed destription of this
  // process.
  if (browser_list_.size() == 1) {
    // Set a flag to indicate that the window close should be allowed.
    is_closing_ = true;
  }

  // Allow the close. For windowed browsers this will result in the OS close
  // event being sent.
  return false;
}

void SimpleHandler::OnBeforeClose(CefRefPtr<CefBrowser> browser) {
  CEF_REQUIRE_UI_THREAD();

  // Remove from the list of existing browsers.
  BrowserList::iterator bit = browser_list_.begin();
  for (; bit != browser_list_.end(); ++bit) {
    if ((*bit)->IsSame(browser)) {
      browser_list_.erase(bit);
      break;
    }
  }

  if (browser_list_.empty()) {
    // All browser windows have closed. Quit the application message loop.
    CefQuitMessageLoop();
  }
}

void SimpleHandler::OnLoadError(CefRefPtr<CefBrowser> browser,
                                CefRefPtr<CefFrame> frame,
                                ErrorCode errorCode,
                                const CefString& errorText,
                                const CefString& failedUrl) {
  CEF_REQUIRE_UI_THREAD();

  // Don't display an error for downloaded files.
  if (errorCode == ERR_ABORTED)
    return;

  // Display a load error message using a data: URI.
  std::stringstream ss;
  ss << "<html><body bgcolor=\"white\">"
        "<h2>Failed to load URL "
     << std::string(failedUrl) << " with error " << std::string(errorText)
     << " (" << errorCode << ").</h2></body></html>";

  frame->LoadURL(GetDataURI(ss.str(), "text/html"));
}

void SimpleHandler::CloseAllBrowsers(bool force_close) {
  if (!CefCurrentlyOn(TID_UI)) {
    // Execute on the UI thread.
    CefPostTask(TID_UI, base::Bind(&SimpleHandler::CloseAllBrowsers, this,
                                   force_close));
    return;
  }

  if (browser_list_.empty())
    return;

  BrowserList::const_iterator it = browser_list_.begin();
  for (; it != browser_list_.end(); ++it)
    (*it)->GetHost()->CloseBrowser(force_close);
}


void SimpleHandler::PlatformTitleChange(CefRefPtr<CefBrowser> browser,
                                        const CefString& title) {
  // NSView* view =
  //     CAST_CEF_WINDOW_HANDLE_TO_NSVIEW(browser->GetHost()->GetWindowHandle());
  // NSWindow* window = [view window];
  // std::string titleStr(title);
  // NSString* str = [NSString stringWithUTF8String:titleStr.c_str()];
  // [window setTitle:str];
}

void SimpleRenderHandler::GetViewRect(CefRefPtr<CefBrowser> browser, CefRect& rect){

    log("settings rect coords");

    rect.x = rect.y = 0;

    rect.width = 800;
    rect.height = 800;

}

void SimpleRenderHandler::OnPaint(CefRefPtr<CefBrowser> browser,
                                  PaintElementType type,
                                  const RectList& dirtyRects,
                                  const void* buffer,
                                  int width,
                                  int height){

    


    log("look at me paint!");
    if (render_handler){
        int* cRects = new int[dirtyRects.size()*4];
        for ( int i = 0; i < dirtyRects.size(); i ++){
            const CefRect &rect = dirtyRects[i];
            cRects[i*4 + 0] = rect.x;
            cRects[i*4 + 1] = rect.y;
            cRects[i*4 + 2] = rect.width;
            cRects[i*4 + 3] = rect.height;
        }

        render_handler((int)0, cRects, buffer, width, height);

        delete[] cRects;
    }
}






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
        int ret = cef_initialize(main_args, settings, app, NULL);

        // #if defined(OS_POSIX)
        RestoreSignalHandlers();
        // #endif

        return ret;

    }



    int start(render_handler_t render_handler){

        if (!cef_load_library("/Volumes/My Passport for Mac/backup/cef/cef_binary_88.1.6+g4fe33a1+chromium-88.0.4324.96_macosx64/Release/Chromium Embedded Framework.framework/Chromium Embedded Framework")) {

            fprintf(stderr, "Failed to load the CEF framework.\n");
            return 1;
        }

        cef_string_t* s = cef_string_t_create();
        char mys[] = "asdfasdf"; 
        cef_string_utf8_to_utf16(mys, strlen(mys), s);


        cef_app_t* app  = cef_app_t_create();
        cef_main_args_t* args = cef_main_args_t_create();

        cef_settings_t* settings = cef_settings_t_create();
        cef_settings_t_set_framework_dir_path(settings, "/Volumes/My Passport for Mac/backup/cef/cef_binary_88.1.6+g4fe33a1+chromium-88.0.4324.96_macosx64/Release/Chromium Embedded Framework.framework/");
        cef_settings_t_set_browser_subprocess_path(settings, "/Users/adrian/workspace/clj-cef/csource/ceftest Helper");
        cef_settings_t_set_main_bundle_path(settings, "/Users/adrian/workspace/clj-cef/");
        // cef_settings_t_set_no_sandbox(settings, true);

        cef_initialize( args, settings, app, NULL);

        return 0;

    }

    int start2( render_handler_t render_handler ){
    // Program entry-point function.

  // Load the CEF framework library at runtime instead of linking directly
  // as required by the macOS sandbox implementation.
  // CefScopedLibraryLoader library_loader;
  // if (!library_loader.LoadInMain())
  //   return 1;

  if (!cef_load_library("/Users/adrian/workspace/clj-cef/csource/ceftest.app/Contents/Frameworks/Chromium Embedded Framework.framework/Chromium Embedded Framework")) {
        // if (!cef_load_library("/Volumes/My Passport for Mac/backup/cef/cef_binary_88.1.6+g4fe33a1+chromium-88.0.4324.96_macosx64/Release/Chromium Embedded Framework.framework/Chromium Embedded Framework")) {
        // if (!cef_load_library("/Volumes/My Passport for Mac/backup/cef/cef_binary_88.1.6+g4fe33a1+chromium-88.0.4324.96_macosx64/build/tests/cefsimple/Debug/cefsimple.app/Contents/Frameworks/Chromium Embedded Framework.framework/Chromium Embedded Framework")) {

            fprintf(stderr, "Failed to load the CEF framework.\n");
            return 1;
        }


        log("loaded");

  // Structure for passing command-line arguments.
  // The definition of this structure is platform-specific.
  CefMainArgs main_args(0, NULL);

  // Optional implementation of the CefApp interface.
  CefRefPtr<SimpleApp> app(new SimpleApp(render_handler));


  // cef_settings_t* csettings = cef_settings_t_create();
  // char s[]= "Asdasdf";
  /* cef_string_ascii_to_utf16(s, strlen(s), &csettings->framework_dir_path); */
  // cef_settings_t_set_framework_dir_path(csettings, "Asdfasdf");
  

  // Populate this structure to customize CEF behavior.
  CefSettings settings;

  settings.no_sandbox = false;
  settings.windowless_rendering_enabled = true;

  // CefString(&settings.framework_dir_path).FromASCII("/Volumes/My Passport for Mac/backup/cef/cef_binary_88.1.6+g4fe33a1+chromium-88.0.4324.96_macosx64/Debug/Chromium Embedded Framework.framework/");
  // CefString(&settings.framework_dir_path).FromASCII("/Volumes/My Passport for Mac/backup/cef/cef_binary_88.1.6+g4fe33a1+chromium-88.0.4324.96_macosx64/build/tests/cefsimple/Debug/cefsimple.app/Contents/Frameworks/Chromium Embedded Framework.framework/");
  CefString(&settings.framework_dir_path).FromASCII("/Users/adrian/workspace/clj-cef/csource/Contents/Frameworks/Chromium Embedded Framework.framework/");

  // Specify the path for the sub-process executable.
  CefString(&settings.browser_subprocess_path).FromASCII("/Users/adrian/workspace/clj-cef/csource/ceftest Helper");

  // CefString(&settings.browser_subprocess_path).FromASCII("/Volumes/My Passport for Mac/backup/cef/cef_binary_88.1.6+g4fe33a1+chromium-88.0.4324.96_macosx64/build/tests/cefsimple/Debug/cefsimple.app/Contents/Frameworks/cefsimple Helper.app/Contents/MacOS/cefsimple Helper");



  CefString(&settings.main_bundle_path).FromASCII("/Users/adrian/workspace/clj-cef/");


  log("initializing");

  // #if defined(OS_POSIX)
  // CefInitialize will reset signal handlers. Backup/restore the original
  // signal handlers to avoid crashes in the JVM (see issue #41).
  BackupSignalHandlers();
// #endif


  // Initialize CEF in the main process.
  CefInitialize(main_args, settings, app.get(), NULL);

// #if defined(OS_POSIX)
  RestoreSignalHandlers();
// #endif

  // Run the CEF message loop. This will block until CefQuitMessageLoop() is called.
  CefRunMessageLoop();

  // Shut down CEF.
  CefShutdown();

  return 0;
}




}





#if defined(TEST)
int main(int argc, char* argv[]) {

    return start2(NULL);
}
#endif


#if defined(HELPER)
    
    int main(int argc, char* argv[]) {


        // {
        //     FILE *fp = fopen("/tmp/test.txt", "w+");
        //     for (int i = 0; i < argc; i ++){
        //         fprintf(fp, " \"%s\" " ,argv[i]); 
        //     }
        // fclose(fp);
        // log("");
        // }

        
        // log("trying to sandbox");
        // Initialize the macOS sandbox for this helper process.
        CefScopedSandboxContext sandbox_context;
        if (!sandbox_context.Initialize(argc, argv)){
            // FILE *fp = fopen("/tmp/test.txt", "w+");fprintf(fp, "sandbox failing to initialize...\n"); fclose(fp);
            return 1;
        }
        // log("sandbox complete!");


        // log( "still running helper...\n"); 


        if (!cef_load_library("/Users/adrian/workspace/clj-cef/csource/Contents/Frameworks/Chromium Embedded Framework.framework/Chromium Embedded Framework")) {
        // if (!cef_load_library("/Volumes/My Passport for Mac/backup/cef/cef_binary_88.1.6+g4fe33a1+chromium-88.0.4324.96_macosx64/Release/Chromium Embedded Framework.framework/Chromium Embedded Framework")) {

        // FILE *fp = fopen("/tmp/test.txt", "w+");fprintf(fp, "failed to load cef framework...\n"); fclose(fp);

            fprintf(stderr, "Failed to load the CEF framework.\n");
            return 1;
        }



        // Load the CEF framework library at runtime instead of linking directly
        // as required by the macOS sandbox implementation.
        // CefScopedLibraryLoader library_loader;
        // if (!library_loader.LoadInHelper())
        //   return 1;

        // Structure for passing command-line arguments.
        // The definition of this structure is platform-specific.
        CefMainArgs main_args(argc, argv);

        // Optional implementation of the CefApp interface.
        CefRefPtr<SimpleApp> app(new SimpleApp(NULL));

        // Execute the sub-process logic. This will block until the sub-process should exit.
        return CefExecuteProcess(main_args, app.get(), NULL);
    }


#endif
