package com.phronemophobic.cljcef;

import com.phronemophobic.cljcef.*;

import com.sun.jna.Structure;

import com.sun.jna.Callback;

import com.sun.jna.Pointer;

import java.util.List;

import java.util.Arrays;

public class CefAccessibilityHandler extends Structure{
public CefAccessibilityHandler(){
base.size.setValue(this.size());
}

public static interface OnAccessibilityTreeChangeFunc extends Callback {

void on_accessibility_tree_change(CefAccessibilityHandler x0, CefValue x1); 
}

public static interface OnAccessibilityLocationChangeFunc extends Callback {

void on_accessibility_location_change(CefAccessibilityHandler x0, CefValue x1); 
}

public CefBaseRefCounted base;

public OnAccessibilityTreeChangeFunc on_accessibility_tree_change;

public OnAccessibilityLocationChangeFunc on_accessibility_location_change;

protected List getFieldOrder() {
                                            return Arrays.asList("base", "on_accessibility_tree_change", "on_accessibility_location_change");
 }}