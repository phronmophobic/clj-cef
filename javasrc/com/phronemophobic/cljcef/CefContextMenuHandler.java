package com.phronemophobic.cljcef;

import com.phronemophobic.cljcef.*;

import com.sun.jna.Structure;

import com.sun.jna.Callback;

import com.sun.jna.Pointer;

import java.util.List;

import java.util.Arrays;

public class CefContextMenuHandler extends Structure{
public CefContextMenuHandler(){
base.size.setValue(this.size());
}

public static interface OnBeforeContextMenuFunc extends Callback {

void on_before_context_menu(CefContextMenuHandler x0, CefBrowser x1, CefFrame x2, CefContextMenuParams x3, CefMenuModel x4); 
}

public static interface RunContextMenuFunc extends Callback {

int run_context_menu(CefContextMenuHandler x0, CefBrowser x1, CefFrame x2, CefContextMenuParams x3, CefMenuModel x4, CefRunContextMenuCallback x5); 
}

public static interface OnContextMenuCommandFunc extends Callback {

int on_context_menu_command(CefContextMenuHandler x0, CefBrowser x1, CefFrame x2, CefContextMenuParams x3, int x4, int x5); 
}

public static interface OnContextMenuDismissedFunc extends Callback {

void on_context_menu_dismissed(CefContextMenuHandler x0, CefBrowser x1, CefFrame x2); 
}

public CefBaseRefCounted base;

public OnBeforeContextMenuFunc on_before_context_menu;

public RunContextMenuFunc run_context_menu;

public OnContextMenuCommandFunc on_context_menu_command;

public OnContextMenuDismissedFunc on_context_menu_dismissed;

protected List getFieldOrder() {
                                            return Arrays.asList("base", "on_before_context_menu", "run_context_menu", "on_context_menu_command", "on_context_menu_dismissed");
 }}