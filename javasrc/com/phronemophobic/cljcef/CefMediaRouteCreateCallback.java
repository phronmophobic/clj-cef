package com.phronemophobic.cljcef;

import com.phronemophobic.cljcef.*;

import com.sun.jna.Structure;

import com.sun.jna.Callback;

import com.sun.jna.Pointer;

import java.util.List;

import java.util.Arrays;

public class CefMediaRouteCreateCallback extends Structure{
public CefMediaRouteCreateCallback(){
base.size.setValue(this.size());
}

public static interface OnMediaRouteCreateFinishedFunc extends Callback {

void on_media_route_create_finished(CefMediaRouteCreateCallback x0, int x1, Pointer x2, CefMediaRoute x3); 
}

public CefBaseRefCounted base;

public OnMediaRouteCreateFinishedFunc on_media_route_create_finished;

protected List getFieldOrder() {
                                            return Arrays.asList("base", "on_media_route_create_finished");
 }}