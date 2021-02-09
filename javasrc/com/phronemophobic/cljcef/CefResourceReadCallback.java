package com.phronemophobic.cljcef;

import com.phronemophobic.cljcef.*;

import com.sun.jna.Structure;

import com.sun.jna.Callback;

import com.sun.jna.Pointer;

import java.util.List;

import java.util.Arrays;

public class CefResourceReadCallback extends Structure{
public CefResourceReadCallback(){
base.size.setValue(this.size());
}

public static interface ContFunc extends Callback {

void cont(CefResourceReadCallback x0, int x1); 
}

public CefBaseRefCounted base;

public ContFunc cont;

protected List getFieldOrder() {
                                            return Arrays.asList("base", "cont");
 }}