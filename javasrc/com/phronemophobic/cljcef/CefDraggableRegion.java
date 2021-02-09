package com.phronemophobic.cljcef;

import com.phronemophobic.cljcef.*;

import com.sun.jna.Structure;

import com.sun.jna.Callback;

import com.sun.jna.Pointer;

import java.util.List;

import java.util.Arrays;

public class CefDraggableRegion extends Structure{




public CefRect bounds;

public int draggable;

protected List getFieldOrder() {
                                            return Arrays.asList("bounds", "draggable");
 }}