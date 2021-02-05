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

#include "capi.cpp"


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

    cef_main_args_t* cef_main_args_t_create(){
        return new cef_main_args_t();
    }
    void cef_main_args_t_destroy(cef_main_args_t* self){
        delete self;
    }


    cef_settings_t* cef_settings_t_create(){
        cef_settings_t* settings = new cef_settings_t();
        settings->size = sizeof(cef_settings_t);
        return settings;
    }
    void cef_settings_t_destroy(cef_settings_t *settings){
        delete settings;
    }

    // cef_color_t background_color;
    // cef_log_severity_t log_severity;
    void cef_settings_t_set_accept_language_list(cef_settings_t *settings, const char* s){
        //cef_string_t accept_language_list;
        CefString(&settings->accept_language_list).FromASCII(s);
    }
    void cef_settings_t_set_application_client_id_for_file_scanning(cef_settings_t *settings,const char* s){
        //cef_string_t application_client_id_for_file_scanning;
        CefString(&settings->application_client_id_for_file_scanning).FromASCII(s);
    }
    void cef_settings_t_set_browser_subprocess_path(cef_settings_t *settings,const char* s){
        //cef_string_t browser_subprocess_path;
        CefString(&settings->browser_subprocess_path).FromASCII(s);
    }
    void cef_settings_t_set_cache_path(cef_settings_t *settings,const char* s){
        //cef_string_t cache_path;
        CefString(&settings->cache_path).FromASCII(s);
    }

    void cef_settings_t_set_framework_dir_path(cef_settings_t *settings,const char* s){
        CefString(&settings->framework_dir_path).FromASCII(s);
    }
    void cef_settings_t_set_javascript_flags(cef_settings_t *settings,const char* s){
        //cef_string_t javascript_flags;
        CefString(&settings->javascript_flags).FromASCII(s);
    }
    void cef_settings_t_set_locale(cef_settings_t *settings,const char* s){
        //cef_string_t locale;
        CefString(&settings->locale).FromASCII(s);
    }
    void cef_settings_t_set_locales_dir_path(cef_settings_t *settings,const char* s){
        //cef_string_t locales_dir_path;
        CefString(&settings->locales_dir_path).FromASCII(s);
    }
    void cef_settings_t_set_log_file(cef_settings_t *settings,const char* s){
        //cef_string_t log_file;
        CefString(&settings->log_file).FromASCII(s);
    }
    void cef_settings_t_set_main_bundle_path(cef_settings_t *settings,const char* s){
        //cef_string_t main_bundle_path;
        CefString(&settings->main_bundle_path).FromASCII(s);
    }
    void cef_settings_t_set_product_version(cef_settings_t *settings,const char* s){
        //cef_string_t product_version;
        CefString(&settings->product_version).FromASCII(s);
    }
    void cef_settings_t_set_resources_dir_path(cef_settings_t *settings,const char* s){
        //cef_string_t resources_dir_path;
        CefString(&settings->resources_dir_path).FromASCII(s);
    }
    void cef_settings_t_set_root_cache_path(cef_settings_t *settings,const char* s){
        //cef_string_t root_cache_path;
        CefString(&settings->root_cache_path).FromASCII(s);
    }
    void cef_settings_t_set_user_agent(cef_settings_t *settings,const char* s){
        //cef_string_t user_agent;
        CefString(&settings->user_agent).FromASCII(s);
    }
    void cef_settings_t_set_user_data_path(cef_settings_t *settings,const char* s){
        //cef_string_t user_data_path;
        CefString(&settings->user_data_path).FromASCII(s);
    }
    void cef_settings_t_set_chrome_runtime(cef_settings_t *settings,int i){
        //int chrome_runtime;
        settings->chrome_runtime = i;
    }
    void cef_settings_t_set_command_line_args_disabled(cef_settings_t *settings,int i){
        //int command_line_args_disabled;
        settings->command_line_args_disabled = i;
    }
    void cef_settings_t_set_external_message_pump(cef_settings_t *settings,int i){
        //int external_message_pump;
        settings->external_message_pump = i;
    }
    void cef_settings_t_set_ignore_certificate_errors(cef_settings_t *settings,int i){
        //cef_settings_t *settings,int ignore_certificate_errors;
        settings->ignore_certificate_errors = i;
    }
    void cef_settings_t_set_multi_threaded_message_loop(cef_settings_t *settings,int i){
        //int multi_threaded_message_loop;
        settings->multi_threaded_message_loop = i;
    }
    void cef_settings_t_set_no_sandbox(cef_settings_t *settings,int i){
        //int no_sandbox;
        settings->no_sandbox = i;
    }
    void cef_settings_t_set_pack_loading_disabled(cef_settings_t *settings,int i){
        //int pack_loading_disabled;
        settings->pack_loading_disabled = i;
    }
    void cef_settings_t_set_persist_session_cookies(cef_settings_t *settings,int i){
        //int persist_session_cookies;
        settings->persist_session_cookies = i;
    }
    void cef_settings_t_set_persist_user_preferences(cef_settings_t *settings,int i){
        //int persist_user_preferences;
        settings->persist_user_preferences = i;
    }
    void cef_settings_t_set_remote_debugging_port(cef_settings_t *settings,int i){
        //int remote_debugging_port;
        settings->remote_debugging_port = i;
    }
    void cef_settings_t_set_uncaught_exception_stack_size(cef_settings_t *settings,int i){
        //int uncaught_exception_stack_size;
        settings->uncaught_exception_stack_size = i;
    }
    void cef_settings_t_set_windowless_rendering_enabled(cef_settings_t *settings,int i){
        //int windowless_rendering_enabled;
        settings->windowless_rendering_enabled = i;
    }


    // browser _settings
  // cef_color_t background_color;
  // cef_state_t application_cache;
  // cef_state_t databases;
  // cef_state_t file_access_from_file_urls;
  // cef_state_t image_loading;
  // cef_state_t image_shrink_standalone_to_fit;
  // cef_state_t javascript;
  // cef_state_t javascript_access_clipboard;
  // cef_state_t javascript_close_windows;
  // cef_state_t javascript_dom_paste;
  // cef_state_t local_storage;
  // cef_state_t plugins;
  // cef_state_t remote_fonts;
  // cef_state_t tab_to_links;
  // cef_state_t text_area_resize;
  // cef_state_t universal_access_from_file_urls;
  // cef_state_t web_security;
  // cef_state_t webgl;

    cef_browser_settings_t* cef_browser_settings_t_create(){
        cef_browser_settings_t* settings = new cef_browser_settings_t();
        settings->size = sizeof(cef_browser_settings_t);
        return settings;
    }
    void cef_browser_settings_t_destroy(cef_browser_settings_t *settings){
        delete settings;
    }
void cef_browser_settings_t_set_accept_language_list(cef_browser_settings_t *settings,const char* s){
    CefString(&settings->accept_language_list).FromASCII(s);
}
void cef_browser_settings_t_set_cursive_font_family(cef_browser_settings_t *settings,const char* s){
    CefString(&settings->cursive_font_family).FromASCII(s);
}
void cef_browser_settings_t_set_default_encoding(cef_browser_settings_t *settings,const char* s){
    CefString(&settings->default_encoding).FromASCII(s);
}
void cef_browser_settings_t_set_fantasy_font_family(cef_browser_settings_t *settings,const char* s){
    CefString(&settings->fantasy_font_family).FromASCII(s);
}
void cef_browser_settings_t_set_fixed_font_family(cef_browser_settings_t *settings,const char* s){
    CefString(&settings->fixed_font_family).FromASCII(s);
}
void cef_browser_settings_t_set_sans_serif_font_family(cef_browser_settings_t *settings,const char* s){
    CefString(&settings->sans_serif_font_family).FromASCII(s);
}
void cef_browser_settings_t_set_serif_font_family(cef_browser_settings_t *settings,const char* s){
    CefString(&settings->serif_font_family).FromASCII(s);
}
void cef_browser_settings_t_set_standard_font_family(cef_browser_settings_t *settings,const char* s){
    CefString(&settings->standard_font_family).FromASCII(s);
}
void cef_browser_settings_t_set_default_fixed_font_size(cef_browser_settings_t *settings, int i){
    settings->default_fixed_font_size = i;
}
void cef_browser_settings_t_set_default_font_size(cef_browser_settings_t *settings, int i){
    settings->default_font_size = i;
}
void cef_browser_settings_t_set_minimum_font_size(cef_browser_settings_t *settings, int i){
    settings->minimum_font_size = i;
}
void cef_browser_settings_t_set_minimum_logical_font_size(cef_browser_settings_t *settings, int i){
    settings->minimum_logical_font_size = i;
}
void cef_browser_settings_t_set_windowless_frame_rate(cef_browser_settings_t *settings, int i){
    settings->windowless_frame_rate = i;
}

    cef_string_t* cef_string_t_create(){
        return new cef_string_t();
    }
    void cef_string_t_destroy(cef_string_t* s){
        delete s;
    }

    cef_window_info_t* cef_window_info_t_create(){
        return new cef_window_info_t();
    }
    void cef_window_info_t_destroy(cef_window_info_t* s){
        delete s;
    }

    int _cef_load_library(const char* path){
        return cef_load_library(path);
    }

    void cef_rect_t_set_x(cef_rect_t* self, int i){
        self->x = i;
    }
    void cef_rect_t_set_y(cef_rect_t* self, int i){
        self->y = i;
    }
    void cef_rect_t_set_width(cef_rect_t* self, int i){
        self->width = i;
    }
    void cef_rect_t_set_height(cef_rect_t* self, int i){
        self->height = i;
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
        cef_settings_t_set_no_sandbox(settings, true);

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

  // if (!cef_load_library("/Users/adrian/workspace/clj-cef/csource/ceftest.app/Contents/Frameworks/Chromium Embedded Framework.framework/Chromium Embedded Framework")) {
        if (!cef_load_library("/Volumes/My Passport for Mac/backup/cef/cef_binary_88.1.6+g4fe33a1+chromium-88.0.4324.96_macosx64/Release/Chromium Embedded Framework.framework/Chromium Embedded Framework")) {

            fprintf(stderr, "Failed to load the CEF framework.\n");
            return 1;
        }


  // Structure for passing command-line arguments.
  // The definition of this structure is platform-specific.
  CefMainArgs main_args(0, NULL);

  // Optional implementation of the CefApp interface.
  CefRefPtr<SimpleApp> app(new SimpleApp(render_handler));


  cef_settings_t* csettings = cef_settings_t_create();
  char s[]= "Asdasdf";
  /* cef_string_ascii_to_utf16(s, strlen(s), &csettings->framework_dir_path); */
  cef_settings_t_set_framework_dir_path(csettings, "Asdfasdf");
  

  // Populate this structure to customize CEF behavior.
  CefSettings settings;

  settings.no_sandbox = true;
  settings.windowless_rendering_enabled = true;

  CefString(&settings.framework_dir_path).FromASCII("/Volumes/My Passport for Mac/backup/cef/cef_binary_88.1.6+g4fe33a1+chromium-88.0.4324.96_macosx64/Release/Chromium Embedded Framework.framework/");

  // Specify the path for the sub-process executable.
  CefString(&settings.browser_subprocess_path).FromASCII("/Users/adrian/workspace/clj-cef/csource/ceftest Helper");

  CefString(&settings.main_bundle_path).FromASCII("/Users/adrian/workspace/clj-cef/");


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

    return start(NULL);
}
#endif


#if defined(HELPER)
    
    int main(int argc, char* argv[]) {



        for (int i = 0; i < argc; i ++){
            FILE *fp = fopen("/tmp/test.txt", "w+");fprintf(fp, "arg %d: %s\n" ,i, argv[i]); fclose(fp);
        }

        // Initialize the macOS sandbox for this helper process.
        CefScopedSandboxContext sandbox_context;
        if (!sandbox_context.Initialize(argc, argv))
            return 1;

        FILE *fp = fopen("/tmp/test.txt", "w+");fprintf(fp, "still running helper...\n"); fclose(fp);


        if (!cef_load_library("/Volumes/My Passport for Mac/backup/cef/cef_binary_88.1.6+g4fe33a1+chromium-88.0.4324.96_macosx64/Release/Chromium Embedded Framework.framework/Chromium Embedded Framework")) {

        FILE *fp = fopen("/tmp/test.txt", "w+");fprintf(fp, "failed to load cef framework...\n"); fclose(fp);

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
