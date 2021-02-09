package com.phronemophobic.cljcef;

import com.phronemophobic.cljcef.*;

import com.sun.jna.Structure;

import com.sun.jna.Callback;

import com.sun.jna.Pointer;

import java.util.List;

import java.util.Arrays;

public class CefKeyboardHandler extends Structure{
public CefKeyboardHandler(){
base.size.setValue(this.size());
}

public static interface OnPreKeyEventFunc extends Callback {

int on_pre_key_event(CefKeyboardHandler x0, CefBrowser x1, Pointer x2, Pointer x3, Pointer x4); 
}

public static interface OnKeyEventFunc extends Callback {

int on_key_event(CefKeyboardHandler x0, CefBrowser x1, Pointer x2, Pointer x3); 
}

public CefBaseRefCounted base;

public OnPreKeyEventFunc on_pre_key_event;

public OnKeyEventFunc on_key_event;

protected List getFieldOrder() {
                                            return Arrays.asList("base", "on_pre_key_event", "on_key_event");
 }}