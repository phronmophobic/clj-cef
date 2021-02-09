package com.phronemophobic.cljcef;

import com.phronemophobic.cljcef.*;

import com.sun.jna.Structure;

import com.sun.jna.Callback;

import com.sun.jna.Pointer;

import java.util.List;

import java.util.Arrays;

public class CefSettings extends Structure{




public SizeT size;

public int no_sandbox;

public CefStringUtf16 browser_subprocess_path;

public CefStringUtf16 framework_dir_path;

public CefStringUtf16 main_bundle_path;

public int chrome_runtime;

public int multi_threaded_message_loop;

public int external_message_pump;

public int windowless_rendering_enabled;

public int command_line_args_disabled;

public CefStringUtf16 cache_path;

public CefStringUtf16 root_cache_path;

public CefStringUtf16 user_data_path;

public int persist_session_cookies;

public int persist_user_preferences;

public CefStringUtf16 user_agent;

public CefStringUtf16 product_version;

public CefStringUtf16 locale;

public CefStringUtf16 log_file;

public int log_severity;

public CefStringUtf16 javascript_flags;

public CefStringUtf16 resources_dir_path;

public CefStringUtf16 locales_dir_path;

public int pack_loading_disabled;

public int remote_debugging_port;

public int uncaught_exception_stack_size;

public int ignore_certificate_errors;

public int background_color;

public CefStringUtf16 accept_language_list;

public CefStringUtf16 application_client_id_for_file_scanning;

protected List getFieldOrder() {
                                            return Arrays.asList("size", "no_sandbox", "browser_subprocess_path", "framework_dir_path", "main_bundle_path", "chrome_runtime", "multi_threaded_message_loop", "external_message_pump", "windowless_rendering_enabled", "command_line_args_disabled", "cache_path", "root_cache_path", "user_data_path", "persist_session_cookies", "persist_user_preferences", "user_agent", "product_version", "locale", "log_file", "log_severity", "javascript_flags", "resources_dir_path", "locales_dir_path", "pack_loading_disabled", "remote_debugging_port", "uncaught_exception_stack_size", "ignore_certificate_errors", "background_color", "accept_language_list", "application_client_id_for_file_scanning");
 }}