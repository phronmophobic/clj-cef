package com.phronemophobic.cljcef;

import com.phronemophobic.cljcef.*;

import com.sun.jna.Structure;

import com.sun.jna.Callback;

import com.sun.jna.Pointer;

import java.util.List;

import java.util.Arrays;

public class CefResponseFilter extends Structure{
public CefResponseFilter(){
base.size.setValue(this.size());
}

public static interface InitFilterFunc extends Callback {

int init_filter(CefResponseFilter x0); 
}

public static interface FilterFunc extends Callback {

int filter(CefResponseFilter x0, Pointer x1, SizeT x2, Pointer x3, Pointer x4, SizeT x5, Pointer x6); 
}

public CefBaseRefCounted base;

public InitFilterFunc init_filter;

public FilterFunc filter;

protected List getFieldOrder() {
                                            return Arrays.asList("base", "init_filter", "filter");
 }}