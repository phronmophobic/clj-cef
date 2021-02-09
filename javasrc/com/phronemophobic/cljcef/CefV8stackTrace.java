package com.phronemophobic.cljcef;

import com.phronemophobic.cljcef.*;

import com.sun.jna.Structure;

import com.sun.jna.Callback;

import com.sun.jna.Pointer;

import java.util.List;

import java.util.Arrays;

public class CefV8stackTrace extends Structure{
public CefV8stackTrace(){
base.size.setValue(this.size());
}

public static interface IsValidFunc extends Callback {

int is_valid(CefV8stackTrace x0); 
}

public static interface GetFrameCountFunc extends Callback {

int get_frame_count(CefV8stackTrace x0); 
}

public static interface GetFrameFunc extends Callback {

CefV8stackFrame get_frame(CefV8stackTrace x0, int x1); 
}

public CefBaseRefCounted base;

public IsValidFunc is_valid;

public GetFrameCountFunc get_frame_count;

public GetFrameFunc get_frame;

protected List getFieldOrder() {
                                            return Arrays.asList("base", "is_valid", "get_frame_count", "get_frame");
 }}