package com.phronemophobic.cljcef;

import com.phronemophobic.cljcef.*;

import com.sun.jna.Structure;

import com.sun.jna.Callback;

import com.sun.jna.Pointer;

import java.util.List;

import java.util.Arrays;

public class CefSelectClientCertificateCallback extends Structure{
public CefSelectClientCertificateCallback(){
base.size.setValue(this.size());
}

public static interface SelectFunc extends Callback {

void select(CefSelectClientCertificateCallback x0, CefX509certificate x1); 
}

public CefBaseRefCounted base;

public SelectFunc select;

protected List getFieldOrder() {
                                            return Arrays.asList("base", "select");
 }}