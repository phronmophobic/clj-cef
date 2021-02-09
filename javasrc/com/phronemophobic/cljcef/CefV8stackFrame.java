package com.phronemophobic.cljcef;

import com.phronemophobic.cljcef.*;

import com.sun.jna.Structure;

import com.sun.jna.Callback;

import com.sun.jna.Pointer;

import java.util.List;

import java.util.Arrays;

public class CefV8stackFrame extends Structure{
public CefV8stackFrame(){
base.size.setValue(this.size());
}

public static interface IsValidFunc extends Callback {

int is_valid(CefV8stackFrame x0); 
}

public static interface GetScriptNameFunc extends Callback {

CefStringUtf16 get_script_name(CefV8stackFrame x0); 
}

public static interface GetScriptNameOrSourceUrlFunc extends Callback {

CefStringUtf16 get_script_name_or_source_url(CefV8stackFrame x0); 
}

public static interface GetFunctionNameFunc extends Callback {

CefStringUtf16 get_function_name(CefV8stackFrame x0); 
}

public static interface GetLineNumberFunc extends Callback {

int get_line_number(CefV8stackFrame x0); 
}

public static interface GetColumnFunc extends Callback {

int get_column(CefV8stackFrame x0); 
}

public static interface IsEvalFunc extends Callback {

int is_eval(CefV8stackFrame x0); 
}

public static interface IsConstructorFunc extends Callback {

int is_constructor(CefV8stackFrame x0); 
}

public CefBaseRefCounted base;

public IsValidFunc is_valid;

public GetScriptNameFunc get_script_name;

public GetScriptNameOrSourceUrlFunc get_script_name_or_source_url;

public GetFunctionNameFunc get_function_name;

public GetLineNumberFunc get_line_number;

public GetColumnFunc get_column;

public IsEvalFunc is_eval;

public IsConstructorFunc is_constructor;

protected List getFieldOrder() {
                                            return Arrays.asList("base", "is_valid", "get_script_name", "get_script_name_or_source_url", "get_function_name", "get_line_number", "get_column", "is_eval", "is_constructor");
 }}