package com.phronemophobic.cljcef;

import com.phronemophobic.cljcef.*;

import com.sun.jna.Structure;

import com.sun.jna.Callback;

import com.sun.jna.Pointer;

import java.util.List;

import java.util.Arrays;

public class CefLoadHandler extends Structure{
public CefLoadHandler(){
base.size.setValue(this.size());
}

public static interface OnLoadingStateChangeFunc extends Callback {

void on_loading_state_change(CefLoadHandler x0, CefBrowser x1, int x2, int x3, int x4); 
}

public static interface OnLoadStartFunc extends Callback {

void on_load_start(CefLoadHandler x0, CefBrowser x1, CefFrame x2, int x3); 
}

public static interface OnLoadEndFunc extends Callback {

void on_load_end(CefLoadHandler x0, CefBrowser x1, CefFrame x2, int x3); 
}

public static interface OnLoadErrorFunc extends Callback {

void on_load_error(CefLoadHandler x0, CefBrowser x1, CefFrame x2, int x3, Pointer x4, Pointer x5); 
}

public CefBaseRefCounted base;

public OnLoadingStateChangeFunc on_loading_state_change;

public OnLoadStartFunc on_load_start;

public OnLoadEndFunc on_load_end;

public OnLoadErrorFunc on_load_error;

protected List getFieldOrder() {
                                            return Arrays.asList("base", "on_loading_state_change", "on_load_start", "on_load_end", "on_load_error");
 }}