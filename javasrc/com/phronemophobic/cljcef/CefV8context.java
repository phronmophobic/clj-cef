package com.phronemophobic.cljcef;

import com.phronemophobic.cljcef.*;

import com.sun.jna.Structure;

import com.sun.jna.Callback;

import com.sun.jna.Pointer;

import java.util.List;

import java.util.Arrays;

public class CefV8context extends Structure{
public CefV8context(){
base.size.setValue(this.size());
}

public static interface GetTaskRunnerFunc extends Callback {

CefTaskRunner get_task_runner(CefV8context x0); 
}

public static interface IsValidFunc extends Callback {

int is_valid(CefV8context x0); 
}

public static interface GetBrowserFunc extends Callback {

CefBrowser get_browser(CefV8context x0); 
}

public static interface GetFrameFunc extends Callback {

CefFrame get_frame(CefV8context x0); 
}

public static interface GetGlobalFunc extends Callback {

CefV8value get_global(CefV8context x0); 
}

public static interface EnterFunc extends Callback {

int enter(CefV8context x0); 
}

public static interface ExitFunc extends Callback {

int exit(CefV8context x0); 
}

public static interface IsSameFunc extends Callback {

int is_same(CefV8context x0, CefV8context x1); 
}

public static interface EvalFunc extends Callback {

int eval(CefV8context x0, Pointer x1, Pointer x2, int x3, Pointer x4, Pointer x5); 
}

public CefBaseRefCounted base;

public GetTaskRunnerFunc get_task_runner;

public IsValidFunc is_valid;

public GetBrowserFunc get_browser;

public GetFrameFunc get_frame;

public GetGlobalFunc get_global;

public EnterFunc enter;

public ExitFunc exit;

public IsSameFunc is_same;

public EvalFunc eval;

protected List getFieldOrder() {
                                            return Arrays.asList("base", "get_task_runner", "is_valid", "get_browser", "get_frame", "get_global", "enter", "exit", "is_same", "eval");
 }}