package com.phronemophobic.cljcef;

import com.phronemophobic.cljcef.*;

import com.sun.jna.Structure;

import com.sun.jna.Callback;

import com.sun.jna.Pointer;

import java.util.List;

import java.util.Arrays;

public class CefDragHandler extends Structure{
public CefDragHandler(){
base.size.setValue(this.size());
}

public static interface OnDragEnterFunc extends Callback {

int on_drag_enter(CefDragHandler x0, CefBrowser x1, CefDragData x2, int x3); 
}

public static interface OnDraggableRegionsChangedFunc extends Callback {

void on_draggable_regions_changed(CefDragHandler x0, CefBrowser x1, CefFrame x2, SizeT x3, Pointer x4); 
}

public CefBaseRefCounted base;

public OnDragEnterFunc on_drag_enter;

public OnDraggableRegionsChangedFunc on_draggable_regions_changed;

protected List getFieldOrder() {
                                            return Arrays.asList("base", "on_drag_enter", "on_draggable_regions_changed");
 }}