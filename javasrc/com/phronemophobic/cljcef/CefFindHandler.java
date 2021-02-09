package com.phronemophobic.cljcef;

import com.phronemophobic.cljcef.*;

import com.sun.jna.Structure;

import com.sun.jna.Callback;

import com.sun.jna.Pointer;

import java.util.List;

import java.util.Arrays;

public class CefFindHandler extends Structure{
public CefFindHandler(){
base.size.setValue(this.size());
}

public static interface OnFindResultFunc extends Callback {

void on_find_result(CefFindHandler x0, CefBrowser x1, int x2, int x3, Pointer x4, int x5, int x6); 
}

public CefBaseRefCounted base;

public OnFindResultFunc on_find_result;

protected List getFieldOrder() {
                                            return Arrays.asList("base", "on_find_result");
 }}