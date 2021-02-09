package com.phronemophobic.cljcef;

import com.phronemophobic.cljcef.*;

import com.sun.jna.Structure;

import com.sun.jna.Callback;

import com.sun.jna.Pointer;

import java.util.List;

import java.util.Arrays;

public class CefWebPluginUnstableCallback extends Structure{
public CefWebPluginUnstableCallback(){
base.size.setValue(this.size());
}

public static interface IsUnstableFunc extends Callback {

void is_unstable(CefWebPluginUnstableCallback x0, Pointer x1, int x2); 
}

public CefBaseRefCounted base;

public IsUnstableFunc is_unstable;

protected List getFieldOrder() {
                                            return Arrays.asList("base", "is_unstable");
 }}