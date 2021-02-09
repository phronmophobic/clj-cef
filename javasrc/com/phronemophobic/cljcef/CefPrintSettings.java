package com.phronemophobic.cljcef;

import com.phronemophobic.cljcef.*;

import com.sun.jna.Structure;

import com.sun.jna.Callback;

import com.sun.jna.Pointer;

import java.util.List;

import java.util.Arrays;

public class CefPrintSettings extends Structure{
public CefPrintSettings(){
base.size.setValue(this.size());
}

public static interface IsValidFunc extends Callback {

int is_valid(CefPrintSettings x0); 
}

public static interface IsReadOnlyFunc extends Callback {

int is_read_only(CefPrintSettings x0); 
}

public static interface SetOrientationFunc extends Callback {

void set_orientation(CefPrintSettings x0, int x1); 
}

public static interface IsLandscapeFunc extends Callback {

int is_landscape(CefPrintSettings x0); 
}

public static interface SetPrinterPrintableAreaFunc extends Callback {

void set_printer_printable_area(CefPrintSettings x0, Pointer x1, Pointer x2, int x3); 
}

public static interface SetDeviceNameFunc extends Callback {

void set_device_name(CefPrintSettings x0, Pointer x1); 
}

public static interface GetDeviceNameFunc extends Callback {

CefStringUtf16 get_device_name(CefPrintSettings x0); 
}

public static interface SetDpiFunc extends Callback {

void set_dpi(CefPrintSettings x0, int x1); 
}

public static interface GetDpiFunc extends Callback {

int get_dpi(CefPrintSettings x0); 
}

public static interface SetPageRangesFunc extends Callback {

void set_page_ranges(CefPrintSettings x0, SizeT x1, Pointer x2); 
}

public static interface GetPageRangesCountFunc extends Callback {

SizeT get_page_ranges_count(CefPrintSettings x0); 
}

public static interface GetPageRangesFunc extends Callback {

void get_page_ranges(CefPrintSettings x0, Pointer x1, CefRange x2); 
}

public static interface SetSelectionOnlyFunc extends Callback {

void set_selection_only(CefPrintSettings x0, int x1); 
}

public static interface IsSelectionOnlyFunc extends Callback {

int is_selection_only(CefPrintSettings x0); 
}

public static interface SetCollateFunc extends Callback {

void set_collate(CefPrintSettings x0, int x1); 
}

public static interface WillCollateFunc extends Callback {

int will_collate(CefPrintSettings x0); 
}

public static interface SetColorModelFunc extends Callback {

void set_color_model(CefPrintSettings x0, int x1); 
}

public static interface GetColorModelFunc extends Callback {

int get_color_model(CefPrintSettings x0); 
}

public static interface SetCopiesFunc extends Callback {

void set_copies(CefPrintSettings x0, int x1); 
}

public static interface GetCopiesFunc extends Callback {

int get_copies(CefPrintSettings x0); 
}

public static interface SetDuplexModeFunc extends Callback {

void set_duplex_mode(CefPrintSettings x0, int x1); 
}

public static interface GetDuplexModeFunc extends Callback {

int get_duplex_mode(CefPrintSettings x0); 
}

public CefBaseRefCounted base;

public IsValidFunc is_valid;

public IsReadOnlyFunc is_read_only;

public SetOrientationFunc set_orientation;

public IsLandscapeFunc is_landscape;

public SetPrinterPrintableAreaFunc set_printer_printable_area;

public SetDeviceNameFunc set_device_name;

public GetDeviceNameFunc get_device_name;

public SetDpiFunc set_dpi;

public GetDpiFunc get_dpi;

public SetPageRangesFunc set_page_ranges;

public GetPageRangesCountFunc get_page_ranges_count;

public GetPageRangesFunc get_page_ranges;

public SetSelectionOnlyFunc set_selection_only;

public IsSelectionOnlyFunc is_selection_only;

public SetCollateFunc set_collate;

public WillCollateFunc will_collate;

public SetColorModelFunc set_color_model;

public GetColorModelFunc get_color_model;

public SetCopiesFunc set_copies;

public GetCopiesFunc get_copies;

public SetDuplexModeFunc set_duplex_mode;

public GetDuplexModeFunc get_duplex_mode;

protected List getFieldOrder() {
                                            return Arrays.asList("base", "is_valid", "is_read_only", "set_orientation", "is_landscape", "set_printer_printable_area", "set_device_name", "get_device_name", "set_dpi", "get_dpi", "set_page_ranges", "get_page_ranges_count", "get_page_ranges", "set_selection_only", "is_selection_only", "set_collate", "will_collate", "set_color_model", "get_color_model", "set_copies", "get_copies", "set_duplex_mode", "get_duplex_mode");
 }}