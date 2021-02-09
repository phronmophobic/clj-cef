package com.phronemophobic.cljcef;

import com.phronemophobic.cljcef.*;

import com.sun.jna.Structure;

import com.sun.jna.Callback;

import com.sun.jna.Pointer;

import java.util.List;

import java.util.Arrays;

public class CefSize extends Structure{




public int width;

public int height;

protected List getFieldOrder() {
                                            return Arrays.asList("width", "height");
 }}