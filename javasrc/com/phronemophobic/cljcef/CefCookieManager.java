package com.phronemophobic.cljcef;

import com.phronemophobic.cljcef.*;

import com.sun.jna.Structure;

import com.sun.jna.Callback;

import com.sun.jna.Pointer;

import java.util.List;

import java.util.Arrays;

public class CefCookieManager extends Structure{
public CefCookieManager(){
base.size.setValue(this.size());
}

public static interface SetSupportedSchemesFunc extends Callback {

void set_supported_schemes(CefCookieManager x0, Pointer x1, int x2, CefCompletionCallback x3); 
}

public static interface VisitAllCookiesFunc extends Callback {

int visit_all_cookies(CefCookieManager x0, CefCookieVisitor x1); 
}

public static interface VisitUrlCookiesFunc extends Callback {

int visit_url_cookies(CefCookieManager x0, Pointer x1, int x2, CefCookieVisitor x3); 
}

public static interface SetCookieFunc extends Callback {

int set_cookie(CefCookieManager x0, Pointer x1, Pointer x2, CefSetCookieCallback x3); 
}

public static interface DeleteCookiesFunc extends Callback {

int delete_cookies(CefCookieManager x0, Pointer x1, Pointer x2, CefDeleteCookiesCallback x3); 
}

public static interface FlushStoreFunc extends Callback {

int flush_store(CefCookieManager x0, CefCompletionCallback x1); 
}

public CefBaseRefCounted base;

public SetSupportedSchemesFunc set_supported_schemes;

public VisitAllCookiesFunc visit_all_cookies;

public VisitUrlCookiesFunc visit_url_cookies;

public SetCookieFunc set_cookie;

public DeleteCookiesFunc delete_cookies;

public FlushStoreFunc flush_store;

protected List getFieldOrder() {
                                            return Arrays.asList("base", "set_supported_schemes", "visit_all_cookies", "visit_url_cookies", "set_cookie", "delete_cookies", "flush_store");
 }}