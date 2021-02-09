package com.phronemophobic.cljcef;

import com.phronemophobic.cljcef.*;

import com.sun.jna.Structure;

import com.sun.jna.Callback;

import com.sun.jna.Pointer;

import java.util.List;

import java.util.Arrays;

public class CefV8accessor extends Structure{
public CefV8accessor(){
base.size.setValue(this.size());
}

public static interface GetFunc extends Callback {

int get(CefV8accessor x0, Pointer x1, CefV8value x2, Pointer x3, CefStringUtf16 x4); 
}

public static interface SetFunc extends Callback {

int set(CefV8accessor x0, Pointer x1, CefV8value x2, CefV8value x3, CefStringUtf16 x4); 
}

public CefBaseRefCounted base;

public GetFunc get;

public SetFunc set;

protected List getFieldOrder() {
                                            return Arrays.asList("base", "get", "set");
 }}