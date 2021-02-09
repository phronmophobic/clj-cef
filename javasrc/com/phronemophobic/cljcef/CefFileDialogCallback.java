package com.phronemophobic.cljcef;

import com.phronemophobic.cljcef.*;

import com.sun.jna.Structure;

import com.sun.jna.Callback;

import com.sun.jna.Pointer;

import java.util.List;

import java.util.Arrays;

public class CefFileDialogCallback extends Structure{
public CefFileDialogCallback(){
base.size.setValue(this.size());
}

public static interface ContFunc extends Callback {

void cont(CefFileDialogCallback x0, int x1, Pointer x2); 
}

public static interface CancelFunc extends Callback {

void cancel(CefFileDialogCallback x0); 
}

public CefBaseRefCounted base;

public ContFunc cont;

public CancelFunc cancel;

protected List getFieldOrder() {
                                            return Arrays.asList("base", "cont", "cancel");
 }}