package com.phronemophobic.cljcef;

import com.phronemophobic.cljcef.*;

import com.sun.jna.Structure;

import com.sun.jna.Callback;

import com.sun.jna.Pointer;

import java.util.List;

import java.util.Arrays;

public class CefSslinfo extends Structure{
public CefSslinfo(){
base.size.setValue(this.size());
}

public static interface GetCertStatusFunc extends Callback {

int get_cert_status(CefSslinfo x0); 
}

public static interface GetX509certificateFunc extends Callback {

CefX509certificate get_x509certificate(CefSslinfo x0); 
}

public CefBaseRefCounted base;

public GetCertStatusFunc get_cert_status;

public GetX509certificateFunc get_x509certificate;

protected List getFieldOrder() {
                                            return Arrays.asList("base", "get_cert_status", "get_x509certificate");
 }}