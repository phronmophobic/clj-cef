package com.phronemophobic.cljcef;

import com.phronemophobic.cljcef.*;

import com.sun.jna.Structure;

import com.sun.jna.Callback;

import com.sun.jna.Pointer;

import java.util.List;

import java.util.Arrays;

public class CefRenderProcessHandler extends Structure{
public CefRenderProcessHandler(){
base.size.setValue(this.size());
}

public static interface OnWebKitInitializedFunc extends Callback {

void on_web_kit_initialized(CefRenderProcessHandler x0); 
}

public static interface OnBrowserCreatedFunc extends Callback {

void on_browser_created(CefRenderProcessHandler x0, CefBrowser x1, CefDictionaryValue x2); 
}

public static interface OnBrowserDestroyedFunc extends Callback {

void on_browser_destroyed(CefRenderProcessHandler x0, CefBrowser x1); 
}

public static interface GetLoadHandlerFunc extends Callback {

CefLoadHandler get_load_handler(CefRenderProcessHandler x0); 
}

public static interface OnContextCreatedFunc extends Callback {

void on_context_created(CefRenderProcessHandler x0, CefBrowser x1, CefFrame x2, CefV8context x3); 
}

public static interface OnContextReleasedFunc extends Callback {

void on_context_released(CefRenderProcessHandler x0, CefBrowser x1, CefFrame x2, CefV8context x3); 
}

public static interface OnUncaughtExceptionFunc extends Callback {

void on_uncaught_exception(CefRenderProcessHandler x0, CefBrowser x1, CefFrame x2, CefV8context x3, CefV8exception x4, CefV8stackTrace x5); 
}

public static interface OnFocusedNodeChangedFunc extends Callback {

void on_focused_node_changed(CefRenderProcessHandler x0, CefBrowser x1, CefFrame x2, CefDomnode x3); 
}

public static interface OnProcessMessageReceivedFunc extends Callback {

int on_process_message_received(CefRenderProcessHandler x0, CefBrowser x1, CefFrame x2, int x3, CefProcessMessage x4); 
}

public CefBaseRefCounted base;

public OnWebKitInitializedFunc on_web_kit_initialized;

public OnBrowserCreatedFunc on_browser_created;

public OnBrowserDestroyedFunc on_browser_destroyed;

public GetLoadHandlerFunc get_load_handler;

public OnContextCreatedFunc on_context_created;

public OnContextReleasedFunc on_context_released;

public OnUncaughtExceptionFunc on_uncaught_exception;

public OnFocusedNodeChangedFunc on_focused_node_changed;

public OnProcessMessageReceivedFunc on_process_message_received;

protected List getFieldOrder() {
                                            return Arrays.asList("base", "on_web_kit_initialized", "on_browser_created", "on_browser_destroyed", "get_load_handler", "on_context_created", "on_context_released", "on_uncaught_exception", "on_focused_node_changed", "on_process_message_received");
 }}