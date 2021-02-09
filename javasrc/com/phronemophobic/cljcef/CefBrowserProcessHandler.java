package com.phronemophobic.cljcef;

import com.phronemophobic.cljcef.*;

import com.sun.jna.Structure;

import com.sun.jna.Callback;

import com.sun.jna.Pointer;

import java.util.List;

import java.util.Arrays;

public class CefBrowserProcessHandler extends Structure{
public CefBrowserProcessHandler(){
base.size.setValue(this.size());
}

public static interface GetCookieableSchemesFunc extends Callback {

void get_cookieable_schemes(CefBrowserProcessHandler x0, Pointer x1, Pointer x2); 
}

public static interface OnContextInitializedFunc extends Callback {

void on_context_initialized(CefBrowserProcessHandler x0); 
}

public static interface OnBeforeChildProcessLaunchFunc extends Callback {

void on_before_child_process_launch(CefBrowserProcessHandler x0, CefCommandLine x1); 
}

public static interface GetPrintHandlerFunc extends Callback {

CefPrintHandler get_print_handler(CefBrowserProcessHandler x0); 
}

public static interface OnScheduleMessagePumpWorkFunc extends Callback {

void on_schedule_message_pump_work(CefBrowserProcessHandler x0, long x1); 
}

public static interface GetDefaultClientFunc extends Callback {

CefClient get_default_client(CefBrowserProcessHandler x0); 
}

public CefBaseRefCounted base;

public GetCookieableSchemesFunc get_cookieable_schemes;

public OnContextInitializedFunc on_context_initialized;

public OnBeforeChildProcessLaunchFunc on_before_child_process_launch;

public GetPrintHandlerFunc get_print_handler;

public OnScheduleMessagePumpWorkFunc on_schedule_message_pump_work;

public GetDefaultClientFunc get_default_client;

protected List getFieldOrder() {
                                            return Arrays.asList("base", "get_cookieable_schemes", "on_context_initialized", "on_before_child_process_launch", "get_print_handler", "on_schedule_message_pump_work", "get_default_client");
 }}