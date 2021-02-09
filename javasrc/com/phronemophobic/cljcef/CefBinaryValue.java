package com.phronemophobic.cljcef;

import com.phronemophobic.cljcef.*;

import com.sun.jna.Structure;

import com.sun.jna.Callback;

import com.sun.jna.Pointer;

import java.util.List;

import java.util.Arrays;

public class CefBinaryValue extends Structure{
public CefBinaryValue(){
base.size.setValue(this.size());
}

public static interface IsValidFunc extends Callback {

int is_valid(CefBinaryValue x0); 
}

public static interface IsOwnedFunc extends Callback {

int is_owned(CefBinaryValue x0); 
}

public static interface IsSameFunc extends Callback {

int is_same(CefBinaryValue x0, CefBinaryValue x1); 
}

public static interface IsEqualFunc extends Callback {

int is_equal(CefBinaryValue x0, CefBinaryValue x1); 
}

public static interface CopyFunc extends Callback {

CefBinaryValue copy(CefBinaryValue x0); 
}

public static interface GetSizeFunc extends Callback {

SizeT get_size(CefBinaryValue x0); 
}

public static interface GetDataFunc extends Callback {

SizeT get_data(CefBinaryValue x0, Pointer x1, SizeT x2, SizeT x3); 
}

public CefBaseRefCounted base;

public IsValidFunc is_valid;

public IsOwnedFunc is_owned;

public IsSameFunc is_same;

public IsEqualFunc is_equal;

public CopyFunc copy;

public GetSizeFunc get_size;

public GetDataFunc get_data;

protected List getFieldOrder() {
                                            return Arrays.asList("base", "is_valid", "is_owned", "is_same", "is_equal", "copy", "get_size", "get_data");
 }}