// AUTO GENERATED BY gen2.clj!

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

ReferenceCountImpl.track(this.getPointer());

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

void on_load_error(CefLoadHandler x0, CefBrowser x1, CefFrame x2, int x3, CefStringUtf16 x4, CefStringUtf16 x5); 
}

public CefBaseRefCounted base;

public OnLoadingStateChangeFunc on_loading_state_change;

public OnLoadStartFunc on_load_start;

public OnLoadEndFunc on_load_end;

public OnLoadErrorFunc on_load_error;

protected List getFieldOrder() {
                                            return Arrays.asList("base", "on_loading_state_change", "on_load_start", "on_load_end", "on_load_error");
 }

public void onLoadingStateChange (CefBrowser x1, int x2, int x3, int x4) {

  this.on_loading_state_change.on_loading_state_change(this,  x1,  x2,  x3,  x4);

}

public void onLoadStart (CefBrowser x1, CefFrame x2, int x3) {

  this.on_load_start.on_load_start(this,  x1,  x2,  x3);

}

public void onLoadEnd (CefBrowser x1, CefFrame x2, int x3) {

  this.on_load_end.on_load_end(this,  x1,  x2,  x3);

}

public void onLoadError (CefBrowser x1, CefFrame x2, int x3, String x4, String x5) {

  this.on_load_error.on_load_error(this,  x1,  x2,  x3, CljCefLib.toCefString(x4), CljCefLib.toCefString(x5));

}}