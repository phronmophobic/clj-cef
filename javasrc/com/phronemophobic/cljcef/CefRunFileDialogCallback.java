package com.phronemophobic.cljcef;

import com.phronemophobic.cljcef.*;

import com.sun.jna.Structure;

import com.sun.jna.Callback;

import com.sun.jna.Pointer;

import java.util.List;

import java.util.Arrays;

public class CefRunFileDialogCallback extends Structure{
public CefRunFileDialogCallback(){
base.size.setValue(this.size());
}

public static interface OnFileDialogDismissedFunc extends Callback {

void on_file_dialog_dismissed(CefRunFileDialogCallback x0, int x1, Pointer x2); 
}

public CefBaseRefCounted base;

public OnFileDialogDismissedFunc on_file_dialog_dismissed;

protected List getFieldOrder() {
                                            return Arrays.asList("base", "on_file_dialog_dismissed");
 }}