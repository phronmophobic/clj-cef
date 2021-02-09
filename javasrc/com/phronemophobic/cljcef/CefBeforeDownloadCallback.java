package com.phronemophobic.cljcef;

import com.phronemophobic.cljcef.*;

import com.sun.jna.Structure;

import com.sun.jna.Callback;

import com.sun.jna.Pointer;

import java.util.List;

import java.util.Arrays;

public class CefBeforeDownloadCallback extends Structure{
public CefBeforeDownloadCallback(){
base.size.setValue(this.size());
}

public static interface ContFunc extends Callback {

void cont(CefBeforeDownloadCallback x0, Pointer x1, int x2); 
}

public CefBaseRefCounted base;

public ContFunc cont;

protected List getFieldOrder() {
                                            return Arrays.asList("base", "cont");
 }}