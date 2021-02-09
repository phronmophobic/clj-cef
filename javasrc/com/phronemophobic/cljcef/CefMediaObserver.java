package com.phronemophobic.cljcef;

import com.phronemophobic.cljcef.*;

import com.sun.jna.Structure;

import com.sun.jna.Callback;

import com.sun.jna.Pointer;

import java.util.List;

import java.util.Arrays;

public class CefMediaObserver extends Structure{
public CefMediaObserver(){
base.size.setValue(this.size());
}

public static interface OnSinksFunc extends Callback {

void on_sinks(CefMediaObserver x0, SizeT x1, Pointer x2); 
}

public static interface OnRoutesFunc extends Callback {

void on_routes(CefMediaObserver x0, SizeT x1, Pointer x2); 
}

public static interface OnRouteStateChangedFunc extends Callback {

void on_route_state_changed(CefMediaObserver x0, CefMediaRoute x1, int x2); 
}

public static interface OnRouteMessageReceivedFunc extends Callback {

void on_route_message_received(CefMediaObserver x0, CefMediaRoute x1, Pointer x2, SizeT x3); 
}

public CefBaseRefCounted base;

public OnSinksFunc on_sinks;

public OnRoutesFunc on_routes;

public OnRouteStateChangedFunc on_route_state_changed;

public OnRouteMessageReceivedFunc on_route_message_received;

protected List getFieldOrder() {
                                            return Arrays.asList("base", "on_sinks", "on_routes", "on_route_state_changed", "on_route_message_received");
 }}