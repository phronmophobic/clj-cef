package com.phronemophobic.cljcef;

import com.phronemophobic.cljcef.*;

import com.sun.jna.Structure;

import com.sun.jna.Callback;

import com.sun.jna.Pointer;

import java.util.List;

import java.util.Arrays;

public class CefPdfPrintCallback extends Structure{
public CefPdfPrintCallback(){
base.size.setValue(this.size());
}

public static interface OnPdfPrintFinishedFunc extends Callback {

void on_pdf_print_finished(CefPdfPrintCallback x0, Pointer x1, int x2); 
}

public CefBaseRefCounted base;

public OnPdfPrintFinishedFunc on_pdf_print_finished;

protected List getFieldOrder() {
                                            return Arrays.asList("base", "on_pdf_print_finished");
 }}