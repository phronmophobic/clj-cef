package com.phronemophobic.cljcef;

import com.phronemophobic.cljcef.*;

import com.sun.jna.Structure;

import com.sun.jna.Callback;

import com.sun.jna.Pointer;

import java.util.List;

import java.util.Arrays;

public class CefDevToolsMessageObserver extends Structure{
public CefDevToolsMessageObserver(){
base.size.setValue(this.size());
}

public static interface OnDevToolsMessageFunc extends Callback {

int on_dev_tools_message(CefDevToolsMessageObserver x0, CefBrowser x1, Pointer x2, SizeT x3); 
}

public static interface OnDevToolsMethodResultFunc extends Callback {

void on_dev_tools_method_result(CefDevToolsMessageObserver x0, CefBrowser x1, int x2, int x3, Pointer x4, SizeT x5); 
}

public static interface OnDevToolsEventFunc extends Callback {

void on_dev_tools_event(CefDevToolsMessageObserver x0, CefBrowser x1, Pointer x2, Pointer x3, SizeT x4); 
}

public static interface OnDevToolsAgentAttachedFunc extends Callback {

void on_dev_tools_agent_attached(CefDevToolsMessageObserver x0, CefBrowser x1); 
}

public static interface OnDevToolsAgentDetachedFunc extends Callback {

void on_dev_tools_agent_detached(CefDevToolsMessageObserver x0, CefBrowser x1); 
}

public CefBaseRefCounted base;

public OnDevToolsMessageFunc on_dev_tools_message;

public OnDevToolsMethodResultFunc on_dev_tools_method_result;

public OnDevToolsEventFunc on_dev_tools_event;

public OnDevToolsAgentAttachedFunc on_dev_tools_agent_attached;

public OnDevToolsAgentDetachedFunc on_dev_tools_agent_detached;

protected List getFieldOrder() {
                                            return Arrays.asList("base", "on_dev_tools_message", "on_dev_tools_method_result", "on_dev_tools_event", "on_dev_tools_agent_attached", "on_dev_tools_agent_detached");
 }}