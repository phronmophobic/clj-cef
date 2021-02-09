package com.phronemophobic.cljcef;

import com.phronemophobic.cljcef.*;

import com.sun.jna.Structure;

import com.sun.jna.Callback;

import com.sun.jna.Pointer;

import java.util.List;

import java.util.Arrays;

public class CefCursorInfo extends Structure{




public CefPoint hotspot;

public float image_scale_factor;

public Pointer buffer;

public CefSize size;

protected List getFieldOrder() {
                                            return Arrays.asList("hotspot", "image_scale_factor", "buffer", "size");
 }}