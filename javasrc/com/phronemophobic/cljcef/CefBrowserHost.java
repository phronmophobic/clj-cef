package com.phronemophobic.cljcef;

import com.phronemophobic.cljcef.*;

import com.sun.jna.Structure;

import com.sun.jna.Callback;

import com.sun.jna.Pointer;

import java.util.List;

import java.util.Arrays;

public class CefBrowserHost extends Structure{
public CefBrowserHost(){
base.size.setValue(this.size());
}

public static interface GetBrowserFunc extends Callback {

CefBrowser get_browser(CefBrowserHost x0); 
}

public static interface CloseBrowserFunc extends Callback {

void close_browser(CefBrowserHost x0, int x1); 
}

public static interface TryCloseBrowserFunc extends Callback {

int try_close_browser(CefBrowserHost x0); 
}

public static interface SetFocusFunc extends Callback {

void set_focus(CefBrowserHost x0, int x1); 
}

public static interface GetWindowHandleFunc extends Callback {

Pointer get_window_handle(CefBrowserHost x0); 
}

public static interface GetOpenerWindowHandleFunc extends Callback {

Pointer get_opener_window_handle(CefBrowserHost x0); 
}

public static interface HasViewFunc extends Callback {

int has_view(CefBrowserHost x0); 
}

public static interface GetClientFunc extends Callback {

CefClient get_client(CefBrowserHost x0); 
}

public static interface GetRequestContextFunc extends Callback {

CefRequestContext get_request_context(CefBrowserHost x0); 
}

public static interface GetZoomLevelFunc extends Callback {

double get_zoom_level(CefBrowserHost x0); 
}

public static interface SetZoomLevelFunc extends Callback {

void set_zoom_level(CefBrowserHost x0, double x1); 
}

public static interface RunFileDialogFunc extends Callback {

void run_file_dialog(CefBrowserHost x0, int x1, Pointer x2, Pointer x3, Pointer x4, int x5, CefRunFileDialogCallback x6); 
}

public static interface StartDownloadFunc extends Callback {

void start_download(CefBrowserHost x0, Pointer x1); 
}

public static interface DownloadImageFunc extends Callback {

void download_image(CefBrowserHost x0, Pointer x1, int x2, int x3, int x4, CefDownloadImageCallback x5); 
}

public static interface PrintFunc extends Callback {

void print(CefBrowserHost x0); 
}

public static interface PrintToPdfFunc extends Callback {

void print_to_pdf(CefBrowserHost x0, Pointer x1, Pointer x2, CefPdfPrintCallback x3); 
}

public static interface FindFunc extends Callback {

void find(CefBrowserHost x0, int x1, Pointer x2, int x3, int x4, int x5); 
}

public static interface StopFindingFunc extends Callback {

void stop_finding(CefBrowserHost x0, int x1); 
}

public static interface ShowDevToolsFunc extends Callback {

void show_dev_tools(CefBrowserHost x0, Pointer x1, CefClient x2, Pointer x3, Pointer x4); 
}

public static interface CloseDevToolsFunc extends Callback {

void close_dev_tools(CefBrowserHost x0); 
}

public static interface HasDevToolsFunc extends Callback {

int has_dev_tools(CefBrowserHost x0); 
}

public static interface SendDevToolsMessageFunc extends Callback {

int send_dev_tools_message(CefBrowserHost x0, Pointer x1, SizeT x2); 
}

public static interface ExecuteDevToolsMethodFunc extends Callback {

int execute_dev_tools_method(CefBrowserHost x0, int x1, Pointer x2, CefDictionaryValue x3); 
}

public static interface AddDevToolsMessageObserverFunc extends Callback {

CefRegistration add_dev_tools_message_observer(CefBrowserHost x0, CefDevToolsMessageObserver x1); 
}

public static interface GetNavigationEntriesFunc extends Callback {

void get_navigation_entries(CefBrowserHost x0, CefNavigationEntryVisitor x1, int x2); 
}

public static interface ReplaceMisspellingFunc extends Callback {

void replace_misspelling(CefBrowserHost x0, Pointer x1); 
}

public static interface AddWordToDictionaryFunc extends Callback {

void add_word_to_dictionary(CefBrowserHost x0, Pointer x1); 
}

public static interface IsWindowRenderingDisabledFunc extends Callback {

int is_window_rendering_disabled(CefBrowserHost x0); 
}

public static interface WasResizedFunc extends Callback {

void was_resized(CefBrowserHost x0); 
}

public static interface WasHiddenFunc extends Callback {

void was_hidden(CefBrowserHost x0, int x1); 
}

public static interface NotifyScreenInfoChangedFunc extends Callback {

void notify_screen_info_changed(CefBrowserHost x0); 
}

public static interface InvalidateFunc extends Callback {

void invalidate(CefBrowserHost x0, int x1); 
}

public static interface SendExternalBeginFrameFunc extends Callback {

void send_external_begin_frame(CefBrowserHost x0); 
}

public static interface SendKeyEventFunc extends Callback {

void send_key_event(CefBrowserHost x0, Pointer x1); 
}

public static interface SendMouseClickEventFunc extends Callback {

void send_mouse_click_event(CefBrowserHost x0, Pointer x1, int x2, int x3, int x4); 
}

public static interface SendMouseMoveEventFunc extends Callback {

void send_mouse_move_event(CefBrowserHost x0, Pointer x1, int x2); 
}

public static interface SendMouseWheelEventFunc extends Callback {

void send_mouse_wheel_event(CefBrowserHost x0, Pointer x1, int x2, int x3); 
}

public static interface SendTouchEventFunc extends Callback {

void send_touch_event(CefBrowserHost x0, Pointer x1); 
}

public static interface SendFocusEventFunc extends Callback {

void send_focus_event(CefBrowserHost x0, int x1); 
}

public static interface SendCaptureLostEventFunc extends Callback {

void send_capture_lost_event(CefBrowserHost x0); 
}

public static interface NotifyMoveOrResizeStartedFunc extends Callback {

void notify_move_or_resize_started(CefBrowserHost x0); 
}

public static interface GetWindowlessFrameRateFunc extends Callback {

int get_windowless_frame_rate(CefBrowserHost x0); 
}

public static interface SetWindowlessFrameRateFunc extends Callback {

void set_windowless_frame_rate(CefBrowserHost x0, int x1); 
}

public static interface ImeSetCompositionFunc extends Callback {

void ime_set_composition(CefBrowserHost x0, Pointer x1, SizeT x2, Pointer x3, Pointer x4, Pointer x5); 
}

public static interface ImeCommitTextFunc extends Callback {

void ime_commit_text(CefBrowserHost x0, Pointer x1, Pointer x2, int x3); 
}

public static interface ImeFinishComposingTextFunc extends Callback {

void ime_finish_composing_text(CefBrowserHost x0, int x1); 
}

public static interface ImeCancelCompositionFunc extends Callback {

void ime_cancel_composition(CefBrowserHost x0); 
}

public static interface DragTargetDragEnterFunc extends Callback {

void drag_target_drag_enter(CefBrowserHost x0, CefDragData x1, Pointer x2, int x3); 
}

public static interface DragTargetDragOverFunc extends Callback {

void drag_target_drag_over(CefBrowserHost x0, Pointer x1, int x2); 
}

public static interface DragTargetDragLeaveFunc extends Callback {

void drag_target_drag_leave(CefBrowserHost x0); 
}

public static interface DragTargetDropFunc extends Callback {

void drag_target_drop(CefBrowserHost x0, Pointer x1); 
}

public static interface DragSourceEndedAtFunc extends Callback {

void drag_source_ended_at(CefBrowserHost x0, int x1, int x2, int x3); 
}

public static interface DragSourceSystemDragEndedFunc extends Callback {

void drag_source_system_drag_ended(CefBrowserHost x0); 
}

public static interface GetVisibleNavigationEntryFunc extends Callback {

CefNavigationEntry get_visible_navigation_entry(CefBrowserHost x0); 
}

public static interface SetAccessibilityStateFunc extends Callback {

void set_accessibility_state(CefBrowserHost x0, int x1); 
}

public static interface SetAutoResizeEnabledFunc extends Callback {

void set_auto_resize_enabled(CefBrowserHost x0, int x1, Pointer x2, Pointer x3); 
}

public static interface GetExtensionFunc extends Callback {

CefExtension get_extension(CefBrowserHost x0); 
}

public static interface IsBackgroundHostFunc extends Callback {

int is_background_host(CefBrowserHost x0); 
}

public static interface SetAudioMutedFunc extends Callback {

void set_audio_muted(CefBrowserHost x0, int x1); 
}

public static interface IsAudioMutedFunc extends Callback {

int is_audio_muted(CefBrowserHost x0); 
}

public CefBaseRefCounted base;

public GetBrowserFunc get_browser;

public CloseBrowserFunc close_browser;

public TryCloseBrowserFunc try_close_browser;

public SetFocusFunc set_focus;

public GetWindowHandleFunc get_window_handle;

public GetOpenerWindowHandleFunc get_opener_window_handle;

public HasViewFunc has_view;

public GetClientFunc get_client;

public GetRequestContextFunc get_request_context;

public GetZoomLevelFunc get_zoom_level;

public SetZoomLevelFunc set_zoom_level;

public RunFileDialogFunc run_file_dialog;

public StartDownloadFunc start_download;

public DownloadImageFunc download_image;

public PrintFunc print;

public PrintToPdfFunc print_to_pdf;

public FindFunc find;

public StopFindingFunc stop_finding;

public ShowDevToolsFunc show_dev_tools;

public CloseDevToolsFunc close_dev_tools;

public HasDevToolsFunc has_dev_tools;

public SendDevToolsMessageFunc send_dev_tools_message;

public ExecuteDevToolsMethodFunc execute_dev_tools_method;

public AddDevToolsMessageObserverFunc add_dev_tools_message_observer;

public GetNavigationEntriesFunc get_navigation_entries;

public ReplaceMisspellingFunc replace_misspelling;

public AddWordToDictionaryFunc add_word_to_dictionary;

public IsWindowRenderingDisabledFunc is_window_rendering_disabled;

public WasResizedFunc was_resized;

public WasHiddenFunc was_hidden;

public NotifyScreenInfoChangedFunc notify_screen_info_changed;

public InvalidateFunc invalidate;

public SendExternalBeginFrameFunc send_external_begin_frame;

public SendKeyEventFunc send_key_event;

public SendMouseClickEventFunc send_mouse_click_event;

public SendMouseMoveEventFunc send_mouse_move_event;

public SendMouseWheelEventFunc send_mouse_wheel_event;

public SendTouchEventFunc send_touch_event;

public SendFocusEventFunc send_focus_event;

public SendCaptureLostEventFunc send_capture_lost_event;

public NotifyMoveOrResizeStartedFunc notify_move_or_resize_started;

public GetWindowlessFrameRateFunc get_windowless_frame_rate;

public SetWindowlessFrameRateFunc set_windowless_frame_rate;

public ImeSetCompositionFunc ime_set_composition;

public ImeCommitTextFunc ime_commit_text;

public ImeFinishComposingTextFunc ime_finish_composing_text;

public ImeCancelCompositionFunc ime_cancel_composition;

public DragTargetDragEnterFunc drag_target_drag_enter;

public DragTargetDragOverFunc drag_target_drag_over;

public DragTargetDragLeaveFunc drag_target_drag_leave;

public DragTargetDropFunc drag_target_drop;

public DragSourceEndedAtFunc drag_source_ended_at;

public DragSourceSystemDragEndedFunc drag_source_system_drag_ended;

public GetVisibleNavigationEntryFunc get_visible_navigation_entry;

public SetAccessibilityStateFunc set_accessibility_state;

public SetAutoResizeEnabledFunc set_auto_resize_enabled;

public GetExtensionFunc get_extension;

public IsBackgroundHostFunc is_background_host;

public SetAudioMutedFunc set_audio_muted;

public IsAudioMutedFunc is_audio_muted;

protected List getFieldOrder() {
                                            return Arrays.asList("base", "get_browser", "close_browser", "try_close_browser", "set_focus", "get_window_handle", "get_opener_window_handle", "has_view", "get_client", "get_request_context", "get_zoom_level", "set_zoom_level", "run_file_dialog", "start_download", "download_image", "print", "print_to_pdf", "find", "stop_finding", "show_dev_tools", "close_dev_tools", "has_dev_tools", "send_dev_tools_message", "execute_dev_tools_method", "add_dev_tools_message_observer", "get_navigation_entries", "replace_misspelling", "add_word_to_dictionary", "is_window_rendering_disabled", "was_resized", "was_hidden", "notify_screen_info_changed", "invalidate", "send_external_begin_frame", "send_key_event", "send_mouse_click_event", "send_mouse_move_event", "send_mouse_wheel_event", "send_touch_event", "send_focus_event", "send_capture_lost_event", "notify_move_or_resize_started", "get_windowless_frame_rate", "set_windowless_frame_rate", "ime_set_composition", "ime_commit_text", "ime_finish_composing_text", "ime_cancel_composition", "drag_target_drag_enter", "drag_target_drag_over", "drag_target_drag_leave", "drag_target_drop", "drag_source_ended_at", "drag_source_system_drag_ended", "get_visible_navigation_entry", "set_accessibility_state", "set_auto_resize_enabled", "get_extension", "is_background_host", "set_audio_muted", "is_audio_muted");
 }}