package com.phronemophobic.cljcef;

import com.phronemophobic.cljcef.*;

import com.sun.jna.Structure;

import com.sun.jna.Callback;

import com.sun.jna.Pointer;

import java.util.List;

import java.util.Arrays;

public class CefResourceSkipCallback extends Structure{
public CefResourceSkipCallback(){
base.size.setValue(this.size());
}

public static interface ContFunc extends Callback {

void cont(CefResourceSkipCallback x0, long x1); 
}

public CefBaseRefCounted base;

public ContFunc cont;

protected List getFieldOrder() {
                                            return Arrays.asList("base", "cont");
 }}