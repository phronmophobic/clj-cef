package com.phronemophobic.cljcef;

import com.phronemophobic.cljcef.*;

import com.sun.jna.Structure;

import com.sun.jna.Callback;

import com.sun.jna.Pointer;

import java.util.List;

import java.util.Arrays;

public class CefValue extends Structure{
public CefValue(){
base.size.setValue(this.size());
}

public static interface IsValidFunc extends Callback {

int is_valid(CefValue x0); 
}

public static interface IsOwnedFunc extends Callback {

int is_owned(CefValue x0); 
}

public static interface IsReadOnlyFunc extends Callback {

int is_read_only(CefValue x0); 
}

public static interface IsSameFunc extends Callback {

int is_same(CefValue x0, CefValue x1); 
}

public static interface IsEqualFunc extends Callback {

int is_equal(CefValue x0, CefValue x1); 
}

public static interface CopyFunc extends Callback {

CefValue copy(CefValue x0); 
}

public static interface GetTypeFunc extends Callback {

int get_type(CefValue x0); 
}

public static interface GetBoolFunc extends Callback {

int get_bool(CefValue x0); 
}

public static interface GetIntFunc extends Callback {

int get_int(CefValue x0); 
}

public static interface GetDoubleFunc extends Callback {

double get_double(CefValue x0); 
}

public static interface GetStringFunc extends Callback {

CefStringUtf16 get_string(CefValue x0); 
}

public static interface GetBinaryFunc extends Callback {

CefBinaryValue get_binary(CefValue x0); 
}

public static interface GetDictionaryFunc extends Callback {

CefDictionaryValue get_dictionary(CefValue x0); 
}

public static interface GetListFunc extends Callback {

CefListValue get_list(CefValue x0); 
}

public static interface SetNullFunc extends Callback {

int set_null(CefValue x0); 
}

public static interface SetBoolFunc extends Callback {

int set_bool(CefValue x0, int x1); 
}

public static interface SetIntFunc extends Callback {

int set_int(CefValue x0, int x1); 
}

public static interface SetDoubleFunc extends Callback {

int set_double(CefValue x0, double x1); 
}

public static interface SetStringFunc extends Callback {

int set_string(CefValue x0, Pointer x1); 
}

public static interface SetBinaryFunc extends Callback {

int set_binary(CefValue x0, CefBinaryValue x1); 
}

public static interface SetDictionaryFunc extends Callback {

int set_dictionary(CefValue x0, CefDictionaryValue x1); 
}

public static interface SetListFunc extends Callback {

int set_list(CefValue x0, CefListValue x1); 
}

public CefBaseRefCounted base;

public IsValidFunc is_valid;

public IsOwnedFunc is_owned;

public IsReadOnlyFunc is_read_only;

public IsSameFunc is_same;

public IsEqualFunc is_equal;

public CopyFunc copy;

public GetTypeFunc get_type;

public GetBoolFunc get_bool;

public GetIntFunc get_int;

public GetDoubleFunc get_double;

public GetStringFunc get_string;

public GetBinaryFunc get_binary;

public GetDictionaryFunc get_dictionary;

public GetListFunc get_list;

public SetNullFunc set_null;

public SetBoolFunc set_bool;

public SetIntFunc set_int;

public SetDoubleFunc set_double;

public SetStringFunc set_string;

public SetBinaryFunc set_binary;

public SetDictionaryFunc set_dictionary;

public SetListFunc set_list;

protected List getFieldOrder() {
                                            return Arrays.asList("base", "is_valid", "is_owned", "is_read_only", "is_same", "is_equal", "copy", "get_type", "get_bool", "get_int", "get_double", "get_string", "get_binary", "get_dictionary", "get_list", "set_null", "set_bool", "set_int", "set_double", "set_string", "set_binary", "set_dictionary", "set_list");
 }}