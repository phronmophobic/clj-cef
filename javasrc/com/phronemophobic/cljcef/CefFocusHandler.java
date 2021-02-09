package com.phronemophobic.cljcef;

import com.phronemophobic.cljcef.*;

import com.sun.jna.Structure;

import com.sun.jna.Callback;

import com.sun.jna.Pointer;

import java.util.List;

import java.util.Arrays;

public class CefFocusHandler extends Structure{
public CefFocusHandler(){
base.size.setValue(this.size());
}

public static interface OnTakeFocusFunc extends Callback {

void on_take_focus(CefFocusHandler x0, CefBrowser x1, int x2); 
}

public static interface OnSetFocusFunc extends Callback {

int on_set_focus(CefFocusHandler x0, CefBrowser x1, int x2); 
}

public static interface OnGotFocusFunc extends Callback {

void on_got_focus(CefFocusHandler x0, CefBrowser x1); 
}

public CefBaseRefCounted base;

public OnTakeFocusFunc on_take_focus;

public OnSetFocusFunc on_set_focus;

public OnGotFocusFunc on_got_focus;

protected List getFieldOrder() {
                                            return Arrays.asList("base", "on_take_focus", "on_set_focus", "on_got_focus");
 }}