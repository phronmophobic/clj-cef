package com.phronemophobic.cljcef;

import com.phronemophobic.cljcef.*;

import com.sun.jna.Structure;

import com.sun.jna.Callback;

import com.sun.jna.Pointer;

import java.util.List;

import java.util.Arrays;

public class CefRequest extends Structure{
public CefRequest(){
base.size.setValue(this.size());
}

public static interface IsReadOnlyFunc extends Callback {

int is_read_only(CefRequest x0); 
}

public static interface GetUrlFunc extends Callback {

CefStringUtf16 get_url(CefRequest x0); 
}

public static interface SetUrlFunc extends Callback {

void set_url(CefRequest x0, Pointer x1); 
}

public static interface GetMethodFunc extends Callback {

CefStringUtf16 get_method(CefRequest x0); 
}

public static interface SetMethodFunc extends Callback {

void set_method(CefRequest x0, Pointer x1); 
}

public static interface SetReferrerFunc extends Callback {

void set_referrer(CefRequest x0, Pointer x1, int x2); 
}

public static interface GetReferrerUrlFunc extends Callback {

CefStringUtf16 get_referrer_url(CefRequest x0); 
}

public static interface GetReferrerPolicyFunc extends Callback {

int get_referrer_policy(CefRequest x0); 
}

public static interface GetPostDataFunc extends Callback {

CefPostData get_post_data(CefRequest x0); 
}

public static interface SetPostDataFunc extends Callback {

void set_post_data(CefRequest x0, CefPostData x1); 
}

public static interface GetHeaderMapFunc extends Callback {

void get_header_map(CefRequest x0, Pointer x1); 
}

public static interface SetHeaderMapFunc extends Callback {

void set_header_map(CefRequest x0, Pointer x1); 
}

public static interface GetHeaderByNameFunc extends Callback {

CefStringUtf16 get_header_by_name(CefRequest x0, Pointer x1); 
}

public static interface SetHeaderByNameFunc extends Callback {

void set_header_by_name(CefRequest x0, Pointer x1, Pointer x2, int x3); 
}

public static interface SetFunc extends Callback {

void set(CefRequest x0, Pointer x1, Pointer x2, CefPostData x3, Pointer x4); 
}

public static interface GetFlagsFunc extends Callback {

int get_flags(CefRequest x0); 
}

public static interface SetFlagsFunc extends Callback {

void set_flags(CefRequest x0, int x1); 
}

public static interface GetFirstPartyForCookiesFunc extends Callback {

CefStringUtf16 get_first_party_for_cookies(CefRequest x0); 
}

public static interface SetFirstPartyForCookiesFunc extends Callback {

void set_first_party_for_cookies(CefRequest x0, Pointer x1); 
}

public static interface GetResourceTypeFunc extends Callback {

int get_resource_type(CefRequest x0); 
}

public static interface GetTransitionTypeFunc extends Callback {

int get_transition_type(CefRequest x0); 
}

public static interface GetIdentifierFunc extends Callback {

long get_identifier(CefRequest x0); 
}

public CefBaseRefCounted base;

public IsReadOnlyFunc is_read_only;

public GetUrlFunc get_url;

public SetUrlFunc set_url;

public GetMethodFunc get_method;

public SetMethodFunc set_method;

public SetReferrerFunc set_referrer;

public GetReferrerUrlFunc get_referrer_url;

public GetReferrerPolicyFunc get_referrer_policy;

public GetPostDataFunc get_post_data;

public SetPostDataFunc set_post_data;

public GetHeaderMapFunc get_header_map;

public SetHeaderMapFunc set_header_map;

public GetHeaderByNameFunc get_header_by_name;

public SetHeaderByNameFunc set_header_by_name;

public SetFunc set;

public GetFlagsFunc get_flags;

public SetFlagsFunc set_flags;

public GetFirstPartyForCookiesFunc get_first_party_for_cookies;

public SetFirstPartyForCookiesFunc set_first_party_for_cookies;

public GetResourceTypeFunc get_resource_type;

public GetTransitionTypeFunc get_transition_type;

public GetIdentifierFunc get_identifier;

protected List getFieldOrder() {
                                            return Arrays.asList("base", "is_read_only", "get_url", "set_url", "get_method", "set_method", "set_referrer", "get_referrer_url", "get_referrer_policy", "get_post_data", "set_post_data", "get_header_map", "set_header_map", "get_header_by_name", "set_header_by_name", "set", "get_flags", "set_flags", "get_first_party_for_cookies", "set_first_party_for_cookies", "get_resource_type", "get_transition_type", "get_identifier");
 }}