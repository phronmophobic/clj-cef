package com.phronemophobic.cljcef;

import com.phronemophobic.cljcef.*;

import com.sun.jna.Structure;

import com.sun.jna.Callback;

import com.sun.jna.Pointer;

import java.util.List;

import java.util.Arrays;

public class CefGetExtensionResourceCallback extends Structure{
public CefGetExtensionResourceCallback(){
base.size.setValue(this.size());
}

public static interface ContFunc extends Callback {

void cont(CefGetExtensionResourceCallback x0, CefStreamReader x1); 
}

public static interface CancelFunc extends Callback {

void cancel(CefGetExtensionResourceCallback x0); 
}

public CefBaseRefCounted base;

public ContFunc cont;

public CancelFunc cancel;

protected List getFieldOrder() {
                                            return Arrays.asList("base", "cont", "cancel");
 }}