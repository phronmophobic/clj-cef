package com.phronemophobic.cljcef;

import com.phronemophobic.cljcef.*;

import com.sun.jna.Structure;

import com.sun.jna.Callback;

import com.sun.jna.Pointer;

import java.util.List;

import java.util.Arrays;

public class CefRenderHandler extends Structure{
public CefRenderHandler(){
base.size.setValue(this.size());
}

public static interface GetAccessibilityHandlerFunc extends Callback {

CefAccessibilityHandler get_accessibility_handler(CefRenderHandler x0); 
}

public static interface GetRootScreenRectFunc extends Callback {

int get_root_screen_rect(CefRenderHandler x0, CefBrowser x1, CefRect x2); 
}

public static interface GetViewRectFunc extends Callback {

void get_view_rect(CefRenderHandler x0, CefBrowser x1, CefRect x2); 
}

public static interface GetScreenPointFunc extends Callback {

int get_screen_point(CefRenderHandler x0, CefBrowser x1, int x2, int x3, Pointer x4, Pointer x5); 
}

public static interface GetScreenInfoFunc extends Callback {

int get_screen_info(CefRenderHandler x0, CefBrowser x1, CefScreenInfo x2); 
}

public static interface OnPopupShowFunc extends Callback {

void on_popup_show(CefRenderHandler x0, CefBrowser x1, int x2); 
}

public static interface OnPopupSizeFunc extends Callback {

void on_popup_size(CefRenderHandler x0, CefBrowser x1, Pointer x2); 
}

public static interface OnPaintFunc extends Callback {

void on_paint(CefRenderHandler x0, CefBrowser x1, int x2, SizeT x3, Pointer x4, Pointer x5, int x6, int x7); 
}

public static interface OnAcceleratedPaintFunc extends Callback {

void on_accelerated_paint(CefRenderHandler x0, CefBrowser x1, int x2, SizeT x3, Pointer x4, Pointer x5); 
}

public static interface StartDraggingFunc extends Callback {

int start_dragging(CefRenderHandler x0, CefBrowser x1, CefDragData x2, int x3, int x4, int x5); 
}

public static interface UpdateDragCursorFunc extends Callback {

void update_drag_cursor(CefRenderHandler x0, CefBrowser x1, int x2); 
}

public static interface OnScrollOffsetChangedFunc extends Callback {

void on_scroll_offset_changed(CefRenderHandler x0, CefBrowser x1, double x2, double x3); 
}

public static interface OnImeCompositionRangeChangedFunc extends Callback {

void on_ime_composition_range_changed(CefRenderHandler x0, CefBrowser x1, Pointer x2, SizeT x3, Pointer x4); 
}

public static interface OnTextSelectionChangedFunc extends Callback {

void on_text_selection_changed(CefRenderHandler x0, CefBrowser x1, Pointer x2, Pointer x3); 
}

public static interface OnVirtualKeyboardRequestedFunc extends Callback {

void on_virtual_keyboard_requested(CefRenderHandler x0, CefBrowser x1, int x2); 
}

public CefBaseRefCounted base;

public GetAccessibilityHandlerFunc get_accessibility_handler;

public GetRootScreenRectFunc get_root_screen_rect;

public GetViewRectFunc get_view_rect;

public GetScreenPointFunc get_screen_point;

public GetScreenInfoFunc get_screen_info;

public OnPopupShowFunc on_popup_show;

public OnPopupSizeFunc on_popup_size;

public OnPaintFunc on_paint;

public OnAcceleratedPaintFunc on_accelerated_paint;

public StartDraggingFunc start_dragging;

public UpdateDragCursorFunc update_drag_cursor;

public OnScrollOffsetChangedFunc on_scroll_offset_changed;

public OnImeCompositionRangeChangedFunc on_ime_composition_range_changed;

public OnTextSelectionChangedFunc on_text_selection_changed;

public OnVirtualKeyboardRequestedFunc on_virtual_keyboard_requested;

protected List getFieldOrder() {
                                            return Arrays.asList("base", "get_accessibility_handler", "get_root_screen_rect", "get_view_rect", "get_screen_point", "get_screen_info", "on_popup_show", "on_popup_size", "on_paint", "on_accelerated_paint", "start_dragging", "update_drag_cursor", "on_scroll_offset_changed", "on_ime_composition_range_changed", "on_text_selection_changed", "on_virtual_keyboard_requested");
 }}