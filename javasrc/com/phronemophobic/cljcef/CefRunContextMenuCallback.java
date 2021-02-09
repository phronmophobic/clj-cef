package com.phronemophobic.cljcef;

import com.phronemophobic.cljcef.*;

import com.sun.jna.Structure;

import com.sun.jna.Callback;

import com.sun.jna.Pointer;

import java.util.List;

import java.util.Arrays;

public class CefRunContextMenuCallback extends Structure{
public CefRunContextMenuCallback(){
base.size.setValue(this.size());
}

public static interface ContFunc extends Callback {

void cont(CefRunContextMenuCallback x0, int x1, int x2); 
}

public static interface CancelFunc extends Callback {

void cancel(CefRunContextMenuCallback x0); 
}

public CefBaseRefCounted base;

public ContFunc cont;

public CancelFunc cancel;

protected List getFieldOrder() {
                                            return Arrays.asList("base", "cont", "cancel");
 }}