package com.phronemophobic.cljcef;

import com.phronemophobic.cljcef.*;

import com.sun.jna.Structure;

import com.sun.jna.Callback;

import com.sun.jna.Pointer;

import java.util.List;

import java.util.Arrays;

public class CefRequestContextHandler extends Structure{
public CefRequestContextHandler(){
base.size.setValue(this.size());
}

public static interface OnRequestContextInitializedFunc extends Callback {

void on_request_context_initialized(CefRequestContextHandler x0, CefRequestContext x1); 
}

public static interface OnBeforePluginLoadFunc extends Callback {

int on_before_plugin_load(CefRequestContextHandler x0, Pointer x1, Pointer x2, int x3, Pointer x4, CefWebPluginInfo x5, int x6); 
}

public static interface GetResourceRequestHandlerFunc extends Callback {

CefResourceRequestHandler get_resource_request_handler(CefRequestContextHandler x0, CefBrowser x1, CefFrame x2, CefRequest x3, int x4, int x5, Pointer x6, Pointer x7); 
}

public CefBaseRefCounted base;

public OnRequestContextInitializedFunc on_request_context_initialized;

public OnBeforePluginLoadFunc on_before_plugin_load;

public GetResourceRequestHandlerFunc get_resource_request_handler;

protected List getFieldOrder() {
                                            return Arrays.asList("base", "on_request_context_initialized", "on_before_plugin_load", "get_resource_request_handler");
 }}