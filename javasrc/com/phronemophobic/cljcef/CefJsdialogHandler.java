package com.phronemophobic.cljcef;

import com.phronemophobic.cljcef.*;

import com.sun.jna.Structure;

import com.sun.jna.Callback;

import com.sun.jna.Pointer;

import java.util.List;

import java.util.Arrays;

public class CefJsdialogHandler extends Structure{
public CefJsdialogHandler(){
base.size.setValue(this.size());
}

public static interface OnJsdialogFunc extends Callback {

int on_jsdialog(CefJsdialogHandler x0, CefBrowser x1, Pointer x2, int x3, Pointer x4, Pointer x5, CefJsdialogCallback x6, Pointer x7); 
}

public static interface OnBeforeUnloadDialogFunc extends Callback {

int on_before_unload_dialog(CefJsdialogHandler x0, CefBrowser x1, Pointer x2, int x3, CefJsdialogCallback x4); 
}

public static interface OnResetDialogStateFunc extends Callback {

void on_reset_dialog_state(CefJsdialogHandler x0, CefBrowser x1); 
}

public static interface OnDialogClosedFunc extends Callback {

void on_dialog_closed(CefJsdialogHandler x0, CefBrowser x1); 
}

public CefBaseRefCounted base;

public OnJsdialogFunc on_jsdialog;

public OnBeforeUnloadDialogFunc on_before_unload_dialog;

public OnResetDialogStateFunc on_reset_dialog_state;

public OnDialogClosedFunc on_dialog_closed;

protected List getFieldOrder() {
                                            return Arrays.asList("base", "on_jsdialog", "on_before_unload_dialog", "on_reset_dialog_state", "on_dialog_closed");
 }}