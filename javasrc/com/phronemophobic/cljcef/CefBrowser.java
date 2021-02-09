package com.phronemophobic.cljcef;

import com.phronemophobic.cljcef.*;

import com.sun.jna.Structure;

import com.sun.jna.Callback;

import com.sun.jna.Pointer;

import java.util.List;

import java.util.Arrays;

public class CefBrowser extends Structure{
public CefBrowser(){
base.size.setValue(this.size());
}

public static interface GetHostFunc extends Callback {

CefBrowserHost get_host(CefBrowser x0); 
}

public static interface CanGoBackFunc extends Callback {

int can_go_back(CefBrowser x0); 
}

public static interface GoBackFunc extends Callback {

void go_back(CefBrowser x0); 
}

public static interface CanGoForwardFunc extends Callback {

int can_go_forward(CefBrowser x0); 
}

public static interface GoForwardFunc extends Callback {

void go_forward(CefBrowser x0); 
}

public static interface IsLoadingFunc extends Callback {

int is_loading(CefBrowser x0); 
}

public static interface ReloadFunc extends Callback {

void reload(CefBrowser x0); 
}

public static interface ReloadIgnoreCacheFunc extends Callback {

void reload_ignore_cache(CefBrowser x0); 
}

public static interface StopLoadFunc extends Callback {

void stop_load(CefBrowser x0); 
}

public static interface GetIdentifierFunc extends Callback {

int get_identifier(CefBrowser x0); 
}

public static interface IsSameFunc extends Callback {

int is_same(CefBrowser x0, CefBrowser x1); 
}

public static interface IsPopupFunc extends Callback {

int is_popup(CefBrowser x0); 
}

public static interface HasDocumentFunc extends Callback {

int has_document(CefBrowser x0); 
}

public static interface GetMainFrameFunc extends Callback {

CefFrame get_main_frame(CefBrowser x0); 
}

public static interface GetFocusedFrameFunc extends Callback {

CefFrame get_focused_frame(CefBrowser x0); 
}

public static interface GetFrameByidentFunc extends Callback {

CefFrame get_frame_byident(CefBrowser x0, long x1); 
}

public static interface GetFrameFunc extends Callback {

CefFrame get_frame(CefBrowser x0, Pointer x1); 
}

public static interface GetFrameCountFunc extends Callback {

SizeT get_frame_count(CefBrowser x0); 
}

public static interface GetFrameIdentifiersFunc extends Callback {

void get_frame_identifiers(CefBrowser x0, Pointer x1, Pointer x2); 
}

public static interface GetFrameNamesFunc extends Callback {

void get_frame_names(CefBrowser x0, Pointer x1); 
}

public CefBaseRefCounted base;

public GetHostFunc get_host;

public CanGoBackFunc can_go_back;

public GoBackFunc go_back;

public CanGoForwardFunc can_go_forward;

public GoForwardFunc go_forward;

public IsLoadingFunc is_loading;

public ReloadFunc reload;

public ReloadIgnoreCacheFunc reload_ignore_cache;

public StopLoadFunc stop_load;

public GetIdentifierFunc get_identifier;

public IsSameFunc is_same;

public IsPopupFunc is_popup;

public HasDocumentFunc has_document;

public GetMainFrameFunc get_main_frame;

public GetFocusedFrameFunc get_focused_frame;

public GetFrameByidentFunc get_frame_byident;

public GetFrameFunc get_frame;

public GetFrameCountFunc get_frame_count;

public GetFrameIdentifiersFunc get_frame_identifiers;

public GetFrameNamesFunc get_frame_names;

protected List getFieldOrder() {
                                            return Arrays.asList("base", "get_host", "can_go_back", "go_back", "can_go_forward", "go_forward", "is_loading", "reload", "reload_ignore_cache", "stop_load", "get_identifier", "is_same", "is_popup", "has_document", "get_main_frame", "get_focused_frame", "get_frame_byident", "get_frame", "get_frame_count", "get_frame_identifiers", "get_frame_names");
 }}