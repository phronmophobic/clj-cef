package com.phronemophobic.cljcef;

import com.phronemophobic.cljcef.*;

import com.sun.jna.Structure;

import com.sun.jna.Callback;

import com.sun.jna.Pointer;

import java.util.List;

import java.util.Arrays;

public class CefResponse extends Structure{
public CefResponse(){
base.size.setValue(this.size());
}

public static interface IsReadOnlyFunc extends Callback {

int is_read_only(CefResponse x0); 
}

public static interface GetErrorFunc extends Callback {

int get_error(CefResponse x0); 
}

public static interface SetErrorFunc extends Callback {

void set_error(CefResponse x0, int x1); 
}

public static interface GetStatusFunc extends Callback {

int get_status(CefResponse x0); 
}

public static interface SetStatusFunc extends Callback {

void set_status(CefResponse x0, int x1); 
}

public static interface GetStatusTextFunc extends Callback {

CefStringUtf16 get_status_text(CefResponse x0); 
}

public static interface SetStatusTextFunc extends Callback {

void set_status_text(CefResponse x0, Pointer x1); 
}

public static interface GetMimeTypeFunc extends Callback {

CefStringUtf16 get_mime_type(CefResponse x0); 
}

public static interface SetMimeTypeFunc extends Callback {

void set_mime_type(CefResponse x0, Pointer x1); 
}

public static interface GetCharsetFunc extends Callback {

CefStringUtf16 get_charset(CefResponse x0); 
}

public static interface SetCharsetFunc extends Callback {

void set_charset(CefResponse x0, Pointer x1); 
}

public static interface GetHeaderByNameFunc extends Callback {

CefStringUtf16 get_header_by_name(CefResponse x0, Pointer x1); 
}

public static interface SetHeaderByNameFunc extends Callback {

void set_header_by_name(CefResponse x0, Pointer x1, Pointer x2, int x3); 
}

public static interface GetHeaderMapFunc extends Callback {

void get_header_map(CefResponse x0, Pointer x1); 
}

public static interface SetHeaderMapFunc extends Callback {

void set_header_map(CefResponse x0, Pointer x1); 
}

public static interface GetUrlFunc extends Callback {

CefStringUtf16 get_url(CefResponse x0); 
}

public static interface SetUrlFunc extends Callback {

void set_url(CefResponse x0, Pointer x1); 
}

public CefBaseRefCounted base;

public IsReadOnlyFunc is_read_only;

public GetErrorFunc get_error;

public SetErrorFunc set_error;

public GetStatusFunc get_status;

public SetStatusFunc set_status;

public GetStatusTextFunc get_status_text;

public SetStatusTextFunc set_status_text;

public GetMimeTypeFunc get_mime_type;

public SetMimeTypeFunc set_mime_type;

public GetCharsetFunc get_charset;

public SetCharsetFunc set_charset;

public GetHeaderByNameFunc get_header_by_name;

public SetHeaderByNameFunc set_header_by_name;

public GetHeaderMapFunc get_header_map;

public SetHeaderMapFunc set_header_map;

public GetUrlFunc get_url;

public SetUrlFunc set_url;

protected List getFieldOrder() {
                                            return Arrays.asList("base", "is_read_only", "get_error", "set_error", "get_status", "set_status", "get_status_text", "set_status_text", "get_mime_type", "set_mime_type", "get_charset", "set_charset", "get_header_by_name", "set_header_by_name", "get_header_map", "set_header_map", "get_url", "set_url");
 }}