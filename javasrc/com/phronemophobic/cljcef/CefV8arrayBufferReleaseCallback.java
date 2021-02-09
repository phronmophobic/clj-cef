package com.phronemophobic.cljcef;

import com.phronemophobic.cljcef.*;

import com.sun.jna.Structure;

import com.sun.jna.Callback;

import com.sun.jna.Pointer;

import java.util.List;

import java.util.Arrays;

public class CefV8arrayBufferReleaseCallback extends Structure{
public CefV8arrayBufferReleaseCallback(){
base.size.setValue(this.size());
}

public static interface ReleaseBufferFunc extends Callback {

void release_buffer(CefV8arrayBufferReleaseCallback x0, Pointer x1); 
}

public CefBaseRefCounted base;

public ReleaseBufferFunc release_buffer;

protected List getFieldOrder() {
                                            return Arrays.asList("base", "release_buffer");
 }}