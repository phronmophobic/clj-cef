package com.phronemophobic.cljcef;

import com.phronemophobic.cljcef.*;

import com.sun.jna.Structure;

import com.sun.jna.Callback;

import com.sun.jna.Pointer;

import java.util.List;

import java.util.Arrays;

public class CefTaskRunner extends Structure{
public CefTaskRunner(){
base.size.setValue(this.size());
}

public static interface IsSameFunc extends Callback {

int is_same(CefTaskRunner x0, CefTaskRunner x1); 
}

public static interface BelongsToCurrentThreadFunc extends Callback {

int belongs_to_current_thread(CefTaskRunner x0); 
}

public static interface BelongsToThreadFunc extends Callback {

int belongs_to_thread(CefTaskRunner x0, int x1); 
}

public static interface PostTaskFunc extends Callback {

int post_task(CefTaskRunner x0, CefTask x1); 
}

public static interface PostDelayedTaskFunc extends Callback {

int post_delayed_task(CefTaskRunner x0, CefTask x1, long x2); 
}

public CefBaseRefCounted base;

public IsSameFunc is_same;

public BelongsToCurrentThreadFunc belongs_to_current_thread;

public BelongsToThreadFunc belongs_to_thread;

public PostTaskFunc post_task;

public PostDelayedTaskFunc post_delayed_task;

protected List getFieldOrder() {
                                            return Arrays.asList("base", "is_same", "belongs_to_current_thread", "belongs_to_thread", "post_task", "post_delayed_task");
 }}