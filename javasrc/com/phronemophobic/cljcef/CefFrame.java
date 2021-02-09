package com.phronemophobic.cljcef;

import com.phronemophobic.cljcef.*;

import com.sun.jna.Structure;

import com.sun.jna.Callback;

import com.sun.jna.Pointer;

import java.util.List;

import java.util.Arrays;

public class CefFrame extends Structure{
public CefFrame(){
base.size.setValue(this.size());
}

public static interface IsValidFunc extends Callback {

int is_valid(CefFrame x0); 
}

public static interface UndoFunc extends Callback {

void undo(CefFrame x0); 
}

public static interface RedoFunc extends Callback {

void redo(CefFrame x0); 
}

public static interface CutFunc extends Callback {

void cut(CefFrame x0); 
}

public static interface CopyFunc extends Callback {

void copy(CefFrame x0); 
}

public static interface PasteFunc extends Callback {

void paste(CefFrame x0); 
}

public static interface DelFunc extends Callback {

void del(CefFrame x0); 
}

public static interface SelectAllFunc extends Callback {

void select_all(CefFrame x0); 
}

public static interface ViewSourceFunc extends Callback {

void view_source(CefFrame x0); 
}

public static interface GetSourceFunc extends Callback {

void get_source(CefFrame x0, CefStringVisitor x1); 
}

public static interface GetTextFunc extends Callback {

void get_text(CefFrame x0, CefStringVisitor x1); 
}

public static interface LoadRequestFunc extends Callback {

void load_request(CefFrame x0, CefRequest x1); 
}

public static interface LoadUrlFunc extends Callback {

void load_url(CefFrame x0, Pointer x1); 
}

public static interface ExecuteJavaScriptFunc extends Callback {

void execute_java_script(CefFrame x0, Pointer x1, Pointer x2, int x3); 
}

public static interface IsMainFunc extends Callback {

int is_main(CefFrame x0); 
}

public static interface IsFocusedFunc extends Callback {

int is_focused(CefFrame x0); 
}

public static interface GetNameFunc extends Callback {

CefStringUtf16 get_name(CefFrame x0); 
}

public static interface GetIdentifierFunc extends Callback {

long get_identifier(CefFrame x0); 
}

public static interface GetParentFunc extends Callback {

CefFrame get_parent(CefFrame x0); 
}

public static interface GetUrlFunc extends Callback {

CefStringUtf16 get_url(CefFrame x0); 
}

public static interface GetBrowserFunc extends Callback {

CefBrowser get_browser(CefFrame x0); 
}

public static interface GetV8contextFunc extends Callback {

CefV8context get_v8context(CefFrame x0); 
}

public static interface VisitDomFunc extends Callback {

void visit_dom(CefFrame x0, CefDomvisitor x1); 
}

public static interface CreateUrlrequestFunc extends Callback {

CefUrlrequest create_urlrequest(CefFrame x0, CefRequest x1, CefUrlrequestClient x2); 
}

public static interface SendProcessMessageFunc extends Callback {

void send_process_message(CefFrame x0, int x1, CefProcessMessage x2); 
}

public CefBaseRefCounted base;

public IsValidFunc is_valid;

public UndoFunc undo;

public RedoFunc redo;

public CutFunc cut;

public CopyFunc copy;

public PasteFunc paste;

public DelFunc del;

public SelectAllFunc select_all;

public ViewSourceFunc view_source;

public GetSourceFunc get_source;

public GetTextFunc get_text;

public LoadRequestFunc load_request;

public LoadUrlFunc load_url;

public ExecuteJavaScriptFunc execute_java_script;

public IsMainFunc is_main;

public IsFocusedFunc is_focused;

public GetNameFunc get_name;

public GetIdentifierFunc get_identifier;

public GetParentFunc get_parent;

public GetUrlFunc get_url;

public GetBrowserFunc get_browser;

public GetV8contextFunc get_v8context;

public VisitDomFunc visit_dom;

public CreateUrlrequestFunc create_urlrequest;

public SendProcessMessageFunc send_process_message;

protected List getFieldOrder() {
                                            return Arrays.asList("base", "is_valid", "undo", "redo", "cut", "copy", "paste", "del", "select_all", "view_source", "get_source", "get_text", "load_request", "load_url", "execute_java_script", "is_main", "is_focused", "get_name", "get_identifier", "get_parent", "get_url", "get_browser", "get_v8context", "visit_dom", "create_urlrequest", "send_process_message");
 }}