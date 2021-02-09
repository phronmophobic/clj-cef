package com.phronemophobic.cljcef;

import com.phronemophobic.cljcef.*;

import com.sun.jna.Structure;

import com.sun.jna.Callback;

import com.sun.jna.Pointer;

import java.util.List;

import java.util.Arrays;

public class CefV8interceptor extends Structure{
public CefV8interceptor(){
base.size.setValue(this.size());
}

public static interface GetBynameFunc extends Callback {

int get_byname(CefV8interceptor x0, Pointer x1, CefV8value x2, Pointer x3, CefStringUtf16 x4); 
}

public static interface GetByindexFunc extends Callback {

int get_byindex(CefV8interceptor x0, int x1, CefV8value x2, Pointer x3, CefStringUtf16 x4); 
}

public static interface SetBynameFunc extends Callback {

int set_byname(CefV8interceptor x0, Pointer x1, CefV8value x2, CefV8value x3, CefStringUtf16 x4); 
}

public static interface SetByindexFunc extends Callback {

int set_byindex(CefV8interceptor x0, int x1, CefV8value x2, CefV8value x3, CefStringUtf16 x4); 
}

public CefBaseRefCounted base;

public GetBynameFunc get_byname;

public GetByindexFunc get_byindex;

public SetBynameFunc set_byname;

public SetByindexFunc set_byindex;

protected List getFieldOrder() {
                                            return Arrays.asList("base", "get_byname", "get_byindex", "set_byname", "set_byindex");
 }}