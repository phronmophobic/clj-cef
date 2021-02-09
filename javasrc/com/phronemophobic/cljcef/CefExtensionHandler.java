package com.phronemophobic.cljcef;

import com.phronemophobic.cljcef.*;

import com.sun.jna.Structure;

import com.sun.jna.Callback;

import com.sun.jna.Pointer;

import java.util.List;

import java.util.Arrays;

public class CefExtensionHandler extends Structure{
public CefExtensionHandler(){
base.size.setValue(this.size());
}

public static interface OnExtensionLoadFailedFunc extends Callback {

void on_extension_load_failed(CefExtensionHandler x0, int x1); 
}

public static interface OnExtensionLoadedFunc extends Callback {

void on_extension_loaded(CefExtensionHandler x0, CefExtension x1); 
}

public static interface OnExtensionUnloadedFunc extends Callback {

void on_extension_unloaded(CefExtensionHandler x0, CefExtension x1); 
}

public static interface OnBeforeBackgroundBrowserFunc extends Callback {

int on_before_background_browser(CefExtensionHandler x0, CefExtension x1, Pointer x2, Pointer x3, CefBrowserSettings x4); 
}

public static interface OnBeforeBrowserFunc extends Callback {

int on_before_browser(CefExtensionHandler x0, CefExtension x1, CefBrowser x2, CefBrowser x3, int x4, Pointer x5, int x6, CefWindowInfo x7, Pointer x8, CefBrowserSettings x9); 
}

public static interface GetActiveBrowserFunc extends Callback {

CefBrowser get_active_browser(CefExtensionHandler x0, CefExtension x1, CefBrowser x2, int x3); 
}

public static interface CanAccessBrowserFunc extends Callback {

int can_access_browser(CefExtensionHandler x0, CefExtension x1, CefBrowser x2, int x3, CefBrowser x4); 
}

public static interface GetExtensionResourceFunc extends Callback {

int get_extension_resource(CefExtensionHandler x0, CefExtension x1, CefBrowser x2, Pointer x3, CefGetExtensionResourceCallback x4); 
}

public CefBaseRefCounted base;

public OnExtensionLoadFailedFunc on_extension_load_failed;

public OnExtensionLoadedFunc on_extension_loaded;

public OnExtensionUnloadedFunc on_extension_unloaded;

public OnBeforeBackgroundBrowserFunc on_before_background_browser;

public OnBeforeBrowserFunc on_before_browser;

public GetActiveBrowserFunc get_active_browser;

public CanAccessBrowserFunc can_access_browser;

public GetExtensionResourceFunc get_extension_resource;

protected List getFieldOrder() {
                                            return Arrays.asList("base", "on_extension_load_failed", "on_extension_loaded", "on_extension_unloaded", "on_before_background_browser", "on_before_browser", "get_active_browser", "can_access_browser", "get_extension_resource");
 }}