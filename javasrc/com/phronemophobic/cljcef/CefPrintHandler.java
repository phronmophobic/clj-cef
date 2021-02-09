package com.phronemophobic.cljcef;

import com.phronemophobic.cljcef.*;

import com.sun.jna.Structure;

import com.sun.jna.Callback;

import com.sun.jna.Pointer;

import java.util.List;

import java.util.Arrays;

public class CefPrintHandler extends Structure{
public CefPrintHandler(){
base.size.setValue(this.size());
}

public static interface OnPrintStartFunc extends Callback {

void on_print_start(CefPrintHandler x0, CefBrowser x1); 
}

public static interface OnPrintSettingsFunc extends Callback {

void on_print_settings(CefPrintHandler x0, CefBrowser x1, CefPrintSettings x2, int x3); 
}

public static interface OnPrintDialogFunc extends Callback {

int on_print_dialog(CefPrintHandler x0, CefBrowser x1, int x2, CefPrintDialogCallback x3); 
}

public static interface OnPrintJobFunc extends Callback {

int on_print_job(CefPrintHandler x0, CefBrowser x1, Pointer x2, Pointer x3, CefPrintJobCallback x4); 
}

public static interface OnPrintResetFunc extends Callback {

void on_print_reset(CefPrintHandler x0, CefBrowser x1); 
}

public static interface GetPdfPaperSizeFunc extends Callback {

CefSize get_pdf_paper_size(CefPrintHandler x0, int x1); 
}

public CefBaseRefCounted base;

public OnPrintStartFunc on_print_start;

public OnPrintSettingsFunc on_print_settings;

public OnPrintDialogFunc on_print_dialog;

public OnPrintJobFunc on_print_job;

public OnPrintResetFunc on_print_reset;

public GetPdfPaperSizeFunc get_pdf_paper_size;

protected List getFieldOrder() {
                                            return Arrays.asList("base", "on_print_start", "on_print_settings", "on_print_dialog", "on_print_job", "on_print_reset", "get_pdf_paper_size");
 }}