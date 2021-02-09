package com.phronemophobic.cljcef;

import com.phronemophobic.cljcef.*;

import com.sun.jna.Structure;

import com.sun.jna.Callback;

import com.sun.jna.Pointer;

import java.util.List;

import java.util.Arrays;

public class CefV8handler extends Structure{
public CefV8handler(){
base.size.setValue(this.size());
}

public static interface ExecuteFunc extends Callback {

int execute(CefV8handler x0, Pointer x1, CefV8value x2, SizeT x3, Pointer x4, Pointer x5, CefStringUtf16 x6); 
}

public CefBaseRefCounted base;

public ExecuteFunc execute;

protected List getFieldOrder() {
                                            return Arrays.asList("base", "execute");
 }}