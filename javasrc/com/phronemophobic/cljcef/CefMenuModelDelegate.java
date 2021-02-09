package com.phronemophobic.cljcef;

import com.phronemophobic.cljcef.*;

import com.sun.jna.Structure;

import com.sun.jna.Callback;

import com.sun.jna.Pointer;

import java.util.List;

import java.util.Arrays;

public class CefMenuModelDelegate extends Structure{
public CefMenuModelDelegate(){
base.size.setValue(this.size());
}

public static interface ExecuteCommandFunc extends Callback {

void execute_command(CefMenuModelDelegate x0, CefMenuModel x1, int x2, int x3); 
}

public static interface MouseOutsideMenuFunc extends Callback {

void mouse_outside_menu(CefMenuModelDelegate x0, CefMenuModel x1, Pointer x2); 
}

public static interface UnhandledOpenSubmenuFunc extends Callback {

void unhandled_open_submenu(CefMenuModelDelegate x0, CefMenuModel x1, int x2); 
}

public static interface UnhandledCloseSubmenuFunc extends Callback {

void unhandled_close_submenu(CefMenuModelDelegate x0, CefMenuModel x1, int x2); 
}

public static interface MenuWillShowFunc extends Callback {

void menu_will_show(CefMenuModelDelegate x0, CefMenuModel x1); 
}

public static interface MenuClosedFunc extends Callback {

void menu_closed(CefMenuModelDelegate x0, CefMenuModel x1); 
}

public static interface FormatLabelFunc extends Callback {

int format_label(CefMenuModelDelegate x0, CefMenuModel x1, CefStringUtf16 x2); 
}

public CefBaseRefCounted base;

public ExecuteCommandFunc execute_command;

public MouseOutsideMenuFunc mouse_outside_menu;

public UnhandledOpenSubmenuFunc unhandled_open_submenu;

public UnhandledCloseSubmenuFunc unhandled_close_submenu;

public MenuWillShowFunc menu_will_show;

public MenuClosedFunc menu_closed;

public FormatLabelFunc format_label;

protected List getFieldOrder() {
                                            return Arrays.asList("base", "execute_command", "mouse_outside_menu", "unhandled_open_submenu", "unhandled_close_submenu", "menu_will_show", "menu_closed", "format_label");
 }}