package com.phronemophobic.cljcef;

import com.phronemophobic.cljcef.*;

import com.sun.jna.Structure;

import com.sun.jna.Callback;

import com.sun.jna.Pointer;

import java.util.List;

import java.util.Arrays;

public class CefDictionaryValue extends Structure{
public CefDictionaryValue(){
base.size.setValue(this.size());
}

public static interface IsValidFunc extends Callback {

int is_valid(CefDictionaryValue x0); 
}

public static interface IsOwnedFunc extends Callback {

int is_owned(CefDictionaryValue x0); 
}

public static interface IsReadOnlyFunc extends Callback {

int is_read_only(CefDictionaryValue x0); 
}

public static interface IsSameFunc extends Callback {

int is_same(CefDictionaryValue x0, CefDictionaryValue x1); 
}

public static interface IsEqualFunc extends Callback {

int is_equal(CefDictionaryValue x0, CefDictionaryValue x1); 
}

public static interface CopyFunc extends Callback {

CefDictionaryValue copy(CefDictionaryValue x0, int x1); 
}

public static interface GetSizeFunc extends Callback {

SizeT get_size(CefDictionaryValue x0); 
}

public static interface ClearFunc extends Callback {

int clear(CefDictionaryValue x0); 
}

public static interface HasKeyFunc extends Callback {

int has_key(CefDictionaryValue x0, Pointer x1); 
}

public static interface GetKeysFunc extends Callback {

int get_keys(CefDictionaryValue x0, Pointer x1); 
}

public static interface RemoveFunc extends Callback {

int remove(CefDictionaryValue x0, Pointer x1); 
}

public static interface GetTypeFunc extends Callback {

int get_type(CefDictionaryValue x0, Pointer x1); 
}

public static interface GetValueFunc extends Callback {

CefValue get_value(CefDictionaryValue x0, Pointer x1); 
}

public static interface GetBoolFunc extends Callback {

int get_bool(CefDictionaryValue x0, Pointer x1); 
}

public static interface GetIntFunc extends Callback {

int get_int(CefDictionaryValue x0, Pointer x1); 
}

public static interface GetDoubleFunc extends Callback {

double get_double(CefDictionaryValue x0, Pointer x1); 
}

public static interface GetStringFunc extends Callback {

CefStringUtf16 get_string(CefDictionaryValue x0, Pointer x1); 
}

public static interface GetBinaryFunc extends Callback {

CefBinaryValue get_binary(CefDictionaryValue x0, Pointer x1); 
}

public static interface GetDictionaryFunc extends Callback {

CefDictionaryValue get_dictionary(CefDictionaryValue x0, Pointer x1); 
}

public static interface GetListFunc extends Callback {

CefListValue get_list(CefDictionaryValue x0, Pointer x1); 
}

public static interface SetValueFunc extends Callback {

int set_value(CefDictionaryValue x0, Pointer x1, CefValue x2); 
}

public static interface SetNullFunc extends Callback {

int set_null(CefDictionaryValue x0, Pointer x1); 
}

public static interface SetBoolFunc extends Callback {

int set_bool(CefDictionaryValue x0, Pointer x1, int x2); 
}

public static interface SetIntFunc extends Callback {

int set_int(CefDictionaryValue x0, Pointer x1, int x2); 
}

public static interface SetDoubleFunc extends Callback {

int set_double(CefDictionaryValue x0, Pointer x1, double x2); 
}

public static interface SetStringFunc extends Callback {

int set_string(CefDictionaryValue x0, Pointer x1, Pointer x2); 
}

public static interface SetBinaryFunc extends Callback {

int set_binary(CefDictionaryValue x0, Pointer x1, CefBinaryValue x2); 
}

public static interface SetDictionaryFunc extends Callback {

int set_dictionary(CefDictionaryValue x0, Pointer x1, CefDictionaryValue x2); 
}

public static interface SetListFunc extends Callback {

int set_list(CefDictionaryValue x0, Pointer x1, CefListValue x2); 
}

public CefBaseRefCounted base;

public IsValidFunc is_valid;

public IsOwnedFunc is_owned;

public IsReadOnlyFunc is_read_only;

public IsSameFunc is_same;

public IsEqualFunc is_equal;

public CopyFunc copy;

public GetSizeFunc get_size;

public ClearFunc clear;

public HasKeyFunc has_key;

public GetKeysFunc get_keys;

public RemoveFunc remove;

public GetTypeFunc get_type;

public GetValueFunc get_value;

public GetBoolFunc get_bool;

public GetIntFunc get_int;

public GetDoubleFunc get_double;

public GetStringFunc get_string;

public GetBinaryFunc get_binary;

public GetDictionaryFunc get_dictionary;

public GetListFunc get_list;

public SetValueFunc set_value;

public SetNullFunc set_null;

public SetBoolFunc set_bool;

public SetIntFunc set_int;

public SetDoubleFunc set_double;

public SetStringFunc set_string;

public SetBinaryFunc set_binary;

public SetDictionaryFunc set_dictionary;

public SetListFunc set_list;

protected List getFieldOrder() {
                                            return Arrays.asList("base", "is_valid", "is_owned", "is_read_only", "is_same", "is_equal", "copy", "get_size", "clear", "has_key", "get_keys", "remove", "get_type", "get_value", "get_bool", "get_int", "get_double", "get_string", "get_binary", "get_dictionary", "get_list", "set_value", "set_null", "set_bool", "set_int", "set_double", "set_string", "set_binary", "set_dictionary", "set_list");
 }}