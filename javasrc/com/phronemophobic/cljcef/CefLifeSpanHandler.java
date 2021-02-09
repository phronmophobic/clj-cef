package com.phronemophobic.cljcef;

import com.phronemophobic.cljcef.*;

import com.sun.jna.Structure;

import com.sun.jna.Callback;

import com.sun.jna.Pointer;

import java.util.List;

import java.util.Arrays;

public class CefLifeSpanHandler extends Structure{
public CefLifeSpanHandler(){
base.size.setValue(this.size());
}

public static interface OnBeforePopupFunc extends Callback {

int on_before_popup(CefLifeSpanHandler x0, CefBrowser x1, CefFrame x2, Pointer x3, Pointer x4, int x5, int x6, Pointer x7, CefWindowInfo x8, Pointer x9, CefBrowserSettings x10, Pointer x11, Pointer x12); 
}

public static interface OnAfterCreatedFunc extends Callback {

void on_after_created(CefLifeSpanHandler x0, CefBrowser x1); 
}

public static interface DoCloseFunc extends Callback {

int do_close(CefLifeSpanHandler x0, CefBrowser x1); 
}

public static interface OnBeforeCloseFunc extends Callback {

void on_before_close(CefLifeSpanHandler x0, CefBrowser x1); 
}

public CefBaseRefCounted base;

public OnBeforePopupFunc on_before_popup;

public OnAfterCreatedFunc on_after_created;

public DoCloseFunc do_close;

public OnBeforeCloseFunc on_before_close;

protected List getFieldOrder() {
                                            return Arrays.asList("base", "on_before_popup", "on_after_created", "do_close", "on_before_close");
 }}