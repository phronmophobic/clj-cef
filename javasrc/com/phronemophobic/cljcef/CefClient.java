package com.phronemophobic.cljcef;

import com.phronemophobic.cljcef.*;

import com.sun.jna.Structure;

import com.sun.jna.Callback;

import com.sun.jna.Pointer;

import java.util.List;

import java.util.Arrays;

public class CefClient extends Structure{
public CefClient(){
base.size.setValue(this.size());
}

public static interface GetAudioHandlerFunc extends Callback {

CefAudioHandler get_audio_handler(CefClient x0); 
}

public static interface GetContextMenuHandlerFunc extends Callback {

CefContextMenuHandler get_context_menu_handler(CefClient x0); 
}

public static interface GetDialogHandlerFunc extends Callback {

CefDialogHandler get_dialog_handler(CefClient x0); 
}

public static interface GetDisplayHandlerFunc extends Callback {

CefDisplayHandler get_display_handler(CefClient x0); 
}

public static interface GetDownloadHandlerFunc extends Callback {

CefDownloadHandler get_download_handler(CefClient x0); 
}

public static interface GetDragHandlerFunc extends Callback {

CefDragHandler get_drag_handler(CefClient x0); 
}

public static interface GetFindHandlerFunc extends Callback {

CefFindHandler get_find_handler(CefClient x0); 
}

public static interface GetFocusHandlerFunc extends Callback {

CefFocusHandler get_focus_handler(CefClient x0); 
}

public static interface GetJsdialogHandlerFunc extends Callback {

CefJsdialogHandler get_jsdialog_handler(CefClient x0); 
}

public static interface GetKeyboardHandlerFunc extends Callback {

CefKeyboardHandler get_keyboard_handler(CefClient x0); 
}

public static interface GetLifeSpanHandlerFunc extends Callback {

CefLifeSpanHandler get_life_span_handler(CefClient x0); 
}

public static interface GetLoadHandlerFunc extends Callback {

CefLoadHandler get_load_handler(CefClient x0); 
}

public static interface GetRenderHandlerFunc extends Callback {

CefRenderHandler get_render_handler(CefClient x0); 
}

public static interface GetRequestHandlerFunc extends Callback {

CefRequestHandler get_request_handler(CefClient x0); 
}

public static interface OnProcessMessageReceivedFunc extends Callback {

int on_process_message_received(CefClient x0, CefBrowser x1, CefFrame x2, int x3, CefProcessMessage x4); 
}

public CefBaseRefCounted base;

public GetAudioHandlerFunc get_audio_handler;

public GetContextMenuHandlerFunc get_context_menu_handler;

public GetDialogHandlerFunc get_dialog_handler;

public GetDisplayHandlerFunc get_display_handler;

public GetDownloadHandlerFunc get_download_handler;

public GetDragHandlerFunc get_drag_handler;

public GetFindHandlerFunc get_find_handler;

public GetFocusHandlerFunc get_focus_handler;

public GetJsdialogHandlerFunc get_jsdialog_handler;

public GetKeyboardHandlerFunc get_keyboard_handler;

public GetLifeSpanHandlerFunc get_life_span_handler;

public GetLoadHandlerFunc get_load_handler;

public GetRenderHandlerFunc get_render_handler;

public GetRequestHandlerFunc get_request_handler;

public OnProcessMessageReceivedFunc on_process_message_received;

protected List getFieldOrder() {
                                            return Arrays.asList("base", "get_audio_handler", "get_context_menu_handler", "get_dialog_handler", "get_display_handler", "get_download_handler", "get_drag_handler", "get_find_handler", "get_focus_handler", "get_jsdialog_handler", "get_keyboard_handler", "get_life_span_handler", "get_load_handler", "get_render_handler", "get_request_handler", "on_process_message_received");
 }}