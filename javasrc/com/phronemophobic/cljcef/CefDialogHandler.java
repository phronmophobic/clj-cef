package com.phronemophobic.cljcef;

import com.phronemophobic.cljcef.*;

import com.sun.jna.Structure;

import com.sun.jna.Callback;

import com.sun.jna.Pointer;

import java.util.List;

import java.util.Arrays;

public class CefDialogHandler extends Structure{
public CefDialogHandler(){
base.size.setValue(this.size());
}

public static interface OnFileDialogFunc extends Callback {

int on_file_dialog(CefDialogHandler x0, CefBrowser x1, int x2, Pointer x3, Pointer x4, Pointer x5, int x6, CefFileDialogCallback x7); 
}

public CefBaseRefCounted base;

public OnFileDialogFunc on_file_dialog;

protected List getFieldOrder() {
                                            return Arrays.asList("base", "on_file_dialog");
 }}