package com.phronemophobic.cljcef;

import com.phronemophobic.cljcef.*;

import com.sun.jna.Structure;

import com.sun.jna.Callback;

import com.sun.jna.Pointer;

import java.util.List;

import java.util.Arrays;

public class CefWebPluginInfo extends Structure{
public CefWebPluginInfo(){
base.size.setValue(this.size());
}

public static interface GetNameFunc extends Callback {

CefStringUtf16 get_name(CefWebPluginInfo x0); 
}

public static interface GetPathFunc extends Callback {

CefStringUtf16 get_path(CefWebPluginInfo x0); 
}

public static interface GetVersionFunc extends Callback {

CefStringUtf16 get_version(CefWebPluginInfo x0); 
}

public static interface GetDescriptionFunc extends Callback {

CefStringUtf16 get_description(CefWebPluginInfo x0); 
}

public CefBaseRefCounted base;

public GetNameFunc get_name;

public GetPathFunc get_path;

public GetVersionFunc get_version;

public GetDescriptionFunc get_description;

protected List getFieldOrder() {
                                            return Arrays.asList("base", "get_name", "get_path", "get_version", "get_description");
 }}