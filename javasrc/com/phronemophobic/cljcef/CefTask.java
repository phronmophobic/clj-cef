package com.phronemophobic.cljcef;

import com.phronemophobic.cljcef.*;

import com.sun.jna.Structure;

import com.sun.jna.Callback;

import com.sun.jna.Pointer;

import java.util.List;

import java.util.Arrays;

public class CefTask extends Structure{
public CefTask(){
base.size.setValue(this.size());
}

public static interface ExecuteFunc extends Callback {

void execute(CefTask x0); 
}

public CefBaseRefCounted base;

public ExecuteFunc execute;

protected List getFieldOrder() {
                                            return Arrays.asList("base", "execute");
 }}