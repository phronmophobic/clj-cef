package com.phronemophobic.cljcef;

import com.phronemophobic.cljcef.*;

import com.sun.jna.Structure;

import com.sun.jna.Callback;

import com.sun.jna.Pointer;

import java.util.List;

import java.util.Arrays;

public class CefResourceHandler extends Structure{
public CefResourceHandler(){
base.size.setValue(this.size());
}

public static interface OpenFunc extends Callback {

int open(CefResourceHandler x0, CefRequest x1, Pointer x2, CefCallback x3); 
}

public static interface ProcessRequestFunc extends Callback {

int process_request(CefResourceHandler x0, CefRequest x1, CefCallback x2); 
}

public static interface GetResponseHeadersFunc extends Callback {

void get_response_headers(CefResourceHandler x0, CefResponse x1, Pointer x2, CefStringUtf16 x3); 
}

public static interface SkipFunc extends Callback {

int skip(CefResourceHandler x0, long x1, Pointer x2, CefResourceSkipCallback x3); 
}

public static interface ReadFunc extends Callback {

int read(CefResourceHandler x0, Pointer x1, int x2, Pointer x3, CefResourceReadCallback x4); 
}

public static interface ReadResponseFunc extends Callback {

int read_response(CefResourceHandler x0, Pointer x1, int x2, Pointer x3, CefCallback x4); 
}

public static interface CancelFunc extends Callback {

void cancel(CefResourceHandler x0); 
}

public CefBaseRefCounted base;

public OpenFunc open;

public ProcessRequestFunc process_request;

public GetResponseHeadersFunc get_response_headers;

public SkipFunc skip;

public ReadFunc read;

public ReadResponseFunc read_response;

public CancelFunc cancel;

protected List getFieldOrder() {
                                            return Arrays.asList("base", "open", "process_request", "get_response_headers", "skip", "read", "read_response", "cancel");
 }}