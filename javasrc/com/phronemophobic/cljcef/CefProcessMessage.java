package com.phronemophobic.cljcef;

import com.phronemophobic.cljcef.*;

import com.sun.jna.Structure;

import com.sun.jna.Callback;

import com.sun.jna.Pointer;

import java.util.List;

import java.util.Arrays;

public class CefProcessMessage extends Structure{
public CefProcessMessage(){
base.size.setValue(this.size());
}

public static interface IsValidFunc extends Callback {

int is_valid(CefProcessMessage x0); 
}

public static interface IsReadOnlyFunc extends Callback {

int is_read_only(CefProcessMessage x0); 
}

public static interface CopyFunc extends Callback {

CefProcessMessage copy(CefProcessMessage x0); 
}

public static interface GetNameFunc extends Callback {

CefStringUtf16 get_name(CefProcessMessage x0); 
}

public static interface GetArgumentListFunc extends Callback {

CefListValue get_argument_list(CefProcessMessage x0); 
}

public CefBaseRefCounted base;

public IsValidFunc is_valid;

public IsReadOnlyFunc is_read_only;

public CopyFunc copy;

public GetNameFunc get_name;

public GetArgumentListFunc get_argument_list;

protected List getFieldOrder() {
                                            return Arrays.asList("base", "is_valid", "is_read_only", "copy", "get_name", "get_argument_list");
 }}