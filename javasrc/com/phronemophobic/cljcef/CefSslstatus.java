package com.phronemophobic.cljcef;

import com.phronemophobic.cljcef.*;

import com.sun.jna.Structure;

import com.sun.jna.Callback;

import com.sun.jna.Pointer;

import java.util.List;

import java.util.Arrays;

public class CefSslstatus extends Structure{
public CefSslstatus(){
base.size.setValue(this.size());
}

public static interface IsSecureConnectionFunc extends Callback {

int is_secure_connection(CefSslstatus x0); 
}

public static interface GetCertStatusFunc extends Callback {

int get_cert_status(CefSslstatus x0); 
}

public static interface GetSslversionFunc extends Callback {

int get_sslversion(CefSslstatus x0); 
}

public static interface GetContentStatusFunc extends Callback {

int get_content_status(CefSslstatus x0); 
}

public static interface GetX509certificateFunc extends Callback {

CefX509certificate get_x509certificate(CefSslstatus x0); 
}

public CefBaseRefCounted base;

public IsSecureConnectionFunc is_secure_connection;

public GetCertStatusFunc get_cert_status;

public GetSslversionFunc get_sslversion;

public GetContentStatusFunc get_content_status;

public GetX509certificateFunc get_x509certificate;

protected List getFieldOrder() {
                                            return Arrays.asList("base", "is_secure_connection", "get_cert_status", "get_sslversion", "get_content_status", "get_x509certificate");
 }}