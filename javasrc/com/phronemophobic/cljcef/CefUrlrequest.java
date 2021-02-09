package com.phronemophobic.cljcef;

import com.phronemophobic.cljcef.*;

import com.sun.jna.Structure;

import com.sun.jna.Callback;

import com.sun.jna.Pointer;

import java.util.List;

import java.util.Arrays;

public class CefUrlrequest extends Structure{
public CefUrlrequest(){
base.size.setValue(this.size());
}

public static interface GetRequestFunc extends Callback {

CefRequest get_request(CefUrlrequest x0); 
}

public static interface GetClientFunc extends Callback {

CefUrlrequestClient get_client(CefUrlrequest x0); 
}

public static interface GetRequestStatusFunc extends Callback {

int get_request_status(CefUrlrequest x0); 
}

public static interface GetRequestErrorFunc extends Callback {

int get_request_error(CefUrlrequest x0); 
}

public static interface GetResponseFunc extends Callback {

CefResponse get_response(CefUrlrequest x0); 
}

public static interface ResponseWasCachedFunc extends Callback {

int response_was_cached(CefUrlrequest x0); 
}

public static interface CancelFunc extends Callback {

void cancel(CefUrlrequest x0); 
}

public CefBaseRefCounted base;

public GetRequestFunc get_request;

public GetClientFunc get_client;

public GetRequestStatusFunc get_request_status;

public GetRequestErrorFunc get_request_error;

public GetResponseFunc get_response;

public ResponseWasCachedFunc response_was_cached;

public CancelFunc cancel;

protected List getFieldOrder() {
                                            return Arrays.asList("base", "get_request", "get_client", "get_request_status", "get_request_error", "get_response", "response_was_cached", "cancel");
 }}