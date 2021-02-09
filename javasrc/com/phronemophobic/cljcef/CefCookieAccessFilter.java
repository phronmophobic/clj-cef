package com.phronemophobic.cljcef;

import com.phronemophobic.cljcef.*;

import com.sun.jna.Structure;

import com.sun.jna.Callback;

import com.sun.jna.Pointer;

import java.util.List;

import java.util.Arrays;

public class CefCookieAccessFilter extends Structure{
public CefCookieAccessFilter(){
base.size.setValue(this.size());
}

public static interface CanSendCookieFunc extends Callback {

int can_send_cookie(CefCookieAccessFilter x0, CefBrowser x1, CefFrame x2, CefRequest x3, Pointer x4); 
}

public static interface CanSaveCookieFunc extends Callback {

int can_save_cookie(CefCookieAccessFilter x0, CefBrowser x1, CefFrame x2, CefRequest x3, CefResponse x4, Pointer x5); 
}

public CefBaseRefCounted base;

public CanSendCookieFunc can_send_cookie;

public CanSaveCookieFunc can_save_cookie;

protected List getFieldOrder() {
                                            return Arrays.asList("base", "can_send_cookie", "can_save_cookie");
 }}