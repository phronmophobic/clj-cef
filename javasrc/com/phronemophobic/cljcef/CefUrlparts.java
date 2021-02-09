package com.phronemophobic.cljcef;

import com.phronemophobic.cljcef.*;

import com.sun.jna.Structure;

import com.sun.jna.Callback;

import com.sun.jna.Pointer;

import java.util.List;

import java.util.Arrays;

public class CefUrlparts extends Structure{




public CefStringUtf16 spec;

public CefStringUtf16 scheme;

public CefStringUtf16 username;

public CefStringUtf16 password;

public CefStringUtf16 host;

public CefStringUtf16 port;

public CefStringUtf16 origin;

public CefStringUtf16 path;

public CefStringUtf16 query;

public CefStringUtf16 fragment;

protected List getFieldOrder() {
                                            return Arrays.asList("spec", "scheme", "username", "password", "host", "port", "origin", "path", "query", "fragment");
 }}