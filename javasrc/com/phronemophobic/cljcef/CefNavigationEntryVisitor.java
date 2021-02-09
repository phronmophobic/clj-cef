package com.phronemophobic.cljcef;

import com.phronemophobic.cljcef.*;

import com.sun.jna.Structure;

import com.sun.jna.Callback;

import com.sun.jna.Pointer;

import java.util.List;

import java.util.Arrays;

public class CefNavigationEntryVisitor extends Structure{
public CefNavigationEntryVisitor(){
base.size.setValue(this.size());
}

public static interface VisitFunc extends Callback {

int visit(CefNavigationEntryVisitor x0, CefNavigationEntry x1, int x2, int x3, int x4); 
}

public CefBaseRefCounted base;

public VisitFunc visit;

protected List getFieldOrder() {
                                            return Arrays.asList("base", "visit");
 }}