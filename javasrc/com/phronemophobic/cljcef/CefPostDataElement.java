package com.phronemophobic.cljcef;

import com.phronemophobic.cljcef.*;

import com.sun.jna.Structure;

import com.sun.jna.Callback;

import com.sun.jna.Pointer;

import java.util.List;

import java.util.Arrays;

public class CefPostDataElement extends Structure{
public CefPostDataElement(){
base.size.setValue(this.size());
}

public static interface IsReadOnlyFunc extends Callback {

int is_read_only(CefPostDataElement x0); 
}

public static interface SetToEmptyFunc extends Callback {

void set_to_empty(CefPostDataElement x0); 
}

public static interface SetToFileFunc extends Callback {

void set_to_file(CefPostDataElement x0, Pointer x1); 
}

public static interface SetToBytesFunc extends Callback {

void set_to_bytes(CefPostDataElement x0, SizeT x1, Pointer x2); 
}

public static interface GetTypeFunc extends Callback {

int get_type(CefPostDataElement x0); 
}

public static interface GetFileFunc extends Callback {

CefStringUtf16 get_file(CefPostDataElement x0); 
}

public static interface GetBytesCountFunc extends Callback {

SizeT get_bytes_count(CefPostDataElement x0); 
}

public static interface GetBytesFunc extends Callback {

SizeT get_bytes(CefPostDataElement x0, SizeT x1, Pointer x2); 
}

public CefBaseRefCounted base;

public IsReadOnlyFunc is_read_only;

public SetToEmptyFunc set_to_empty;

public SetToFileFunc set_to_file;

public SetToBytesFunc set_to_bytes;

public GetTypeFunc get_type;

public GetFileFunc get_file;

public GetBytesCountFunc get_bytes_count;

public GetBytesFunc get_bytes;

protected List getFieldOrder() {
                                            return Arrays.asList("base", "is_read_only", "set_to_empty", "set_to_file", "set_to_bytes", "get_type", "get_file", "get_bytes_count", "get_bytes");
 }}