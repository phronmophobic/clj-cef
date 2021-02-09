package com.phronemophobic.cljcef;

import com.phronemophobic.cljcef.*;

import com.sun.jna.Structure;

import com.sun.jna.Callback;

import com.sun.jna.Pointer;

import java.util.List;

import java.util.Arrays;

public class CefMediaRouter extends Structure{
public CefMediaRouter(){
base.size.setValue(this.size());
}

public static interface AddObserverFunc extends Callback {

CefRegistration add_observer(CefMediaRouter x0, CefMediaObserver x1); 
}

public static interface GetSourceFunc extends Callback {

CefMediaSource get_source(CefMediaRouter x0, Pointer x1); 
}

public static interface NotifyCurrentSinksFunc extends Callback {

void notify_current_sinks(CefMediaRouter x0); 
}

public static interface CreateRouteFunc extends Callback {

void create_route(CefMediaRouter x0, CefMediaSource x1, CefMediaSink x2, CefMediaRouteCreateCallback x3); 
}

public static interface NotifyCurrentRoutesFunc extends Callback {

void notify_current_routes(CefMediaRouter x0); 
}

public CefBaseRefCounted base;

public AddObserverFunc add_observer;

public GetSourceFunc get_source;

public NotifyCurrentSinksFunc notify_current_sinks;

public CreateRouteFunc create_route;

public NotifyCurrentRoutesFunc notify_current_routes;

protected List getFieldOrder() {
                                            return Arrays.asList("base", "add_observer", "get_source", "notify_current_sinks", "create_route", "notify_current_routes");
 }}