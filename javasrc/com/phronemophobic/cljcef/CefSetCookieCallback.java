package com.phronemophobic.cljcef;

import com.phronemophobic.cljcef.*;

import com.sun.jna.Structure;

import com.sun.jna.Callback;

import com.sun.jna.Pointer;

import java.util.List;

import java.util.Arrays;

public class CefSetCookieCallback extends Structure{
public CefSetCookieCallback(){
base.size.setValue(this.size());
}

public static interface OnCompleteFunc extends Callback {

void on_complete(CefSetCookieCallback x0, int x1); 
}

public CefBaseRefCounted base;

public OnCompleteFunc on_complete;

protected List getFieldOrder() {
                                            return Arrays.asList("base", "on_complete");
 }}