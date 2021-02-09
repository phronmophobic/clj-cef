package com.phronemophobic.cljcef;

import com.phronemophobic.cljcef.*;

import com.sun.jna.Structure;

import com.sun.jna.Callback;

import com.sun.jna.Pointer;

import java.util.List;

import java.util.Arrays;

public class CefPdfPrintSettings extends Structure{




public CefStringUtf16 header_footer_title;

public CefStringUtf16 header_footer_url;

public int page_width;

public int page_height;

public int scale_factor;

public int margin_top;

public int margin_right;

public int margin_bottom;

public int margin_left;

public int margin_type;

public int header_footer_enabled;

public int selection_only;

public int landscape;

public int backgrounds_enabled;

protected List getFieldOrder() {
                                            return Arrays.asList("header_footer_title", "header_footer_url", "page_width", "page_height", "scale_factor", "margin_top", "margin_right", "margin_bottom", "margin_left", "margin_type", "header_footer_enabled", "selection_only", "landscape", "backgrounds_enabled");
 }}