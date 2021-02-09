package com.phronemophobic.cljcef;

import com.phronemophobic.cljcef.*;

import com.sun.jna.Structure;

import com.sun.jna.Callback;

import com.sun.jna.Pointer;

import java.util.List;

import java.util.Arrays;

public class CefDisplayHandler extends Structure{
public CefDisplayHandler(){
base.size.setValue(this.size());
}

public static interface OnAddressChangeFunc extends Callback {

void on_address_change(CefDisplayHandler x0, CefBrowser x1, CefFrame x2, Pointer x3); 
}

public static interface OnTitleChangeFunc extends Callback {

void on_title_change(CefDisplayHandler x0, CefBrowser x1, Pointer x2); 
}

public static interface OnFaviconUrlchangeFunc extends Callback {

void on_favicon_urlchange(CefDisplayHandler x0, CefBrowser x1, Pointer x2); 
}

public static interface OnFullscreenModeChangeFunc extends Callback {

void on_fullscreen_mode_change(CefDisplayHandler x0, CefBrowser x1, int x2); 
}

public static interface OnTooltipFunc extends Callback {

int on_tooltip(CefDisplayHandler x0, CefBrowser x1, CefStringUtf16 x2); 
}

public static interface OnStatusMessageFunc extends Callback {

void on_status_message(CefDisplayHandler x0, CefBrowser x1, Pointer x2); 
}

public static interface OnConsoleMessageFunc extends Callback {

int on_console_message(CefDisplayHandler x0, CefBrowser x1, int x2, Pointer x3, Pointer x4, int x5); 
}

public static interface OnAutoResizeFunc extends Callback {

int on_auto_resize(CefDisplayHandler x0, CefBrowser x1, Pointer x2); 
}

public static interface OnLoadingProgressChangeFunc extends Callback {

void on_loading_progress_change(CefDisplayHandler x0, CefBrowser x1, double x2); 
}

public static interface OnCursorChangeFunc extends Callback {

int on_cursor_change(CefDisplayHandler x0, CefBrowser x1, Pointer x2, int x3, Pointer x4); 
}

public CefBaseRefCounted base;

public OnAddressChangeFunc on_address_change;

public OnTitleChangeFunc on_title_change;

public OnFaviconUrlchangeFunc on_favicon_urlchange;

public OnFullscreenModeChangeFunc on_fullscreen_mode_change;

public OnTooltipFunc on_tooltip;

public OnStatusMessageFunc on_status_message;

public OnConsoleMessageFunc on_console_message;

public OnAutoResizeFunc on_auto_resize;

public OnLoadingProgressChangeFunc on_loading_progress_change;

public OnCursorChangeFunc on_cursor_change;

protected List getFieldOrder() {
                                            return Arrays.asList("base", "on_address_change", "on_title_change", "on_favicon_urlchange", "on_fullscreen_mode_change", "on_tooltip", "on_status_message", "on_console_message", "on_auto_resize", "on_loading_progress_change", "on_cursor_change");
 }}