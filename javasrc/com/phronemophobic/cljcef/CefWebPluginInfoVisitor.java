package com.phronemophobic.cljcef;

import com.phronemophobic.cljcef.*;

import com.sun.jna.Structure;

import com.sun.jna.Callback;

import com.sun.jna.Pointer;

import java.util.List;

import java.util.Arrays;

public class CefWebPluginInfoVisitor extends Structure{
public CefWebPluginInfoVisitor(){
base.size.setValue(this.size());
}

public static interface VisitFunc extends Callback {

int visit(CefWebPluginInfoVisitor x0, CefWebPluginInfo x1, int x2, int x3); 
}

public CefBaseRefCounted base;

public VisitFunc visit;

protected List getFieldOrder() {
                                            return Arrays.asList("base", "visit");
 }}