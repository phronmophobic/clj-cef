package com.phronemophobic.cljcef;

import com.phronemophobic.cljcef.*;

import com.sun.jna.Structure;

import com.sun.jna.Callback;

import com.sun.jna.Pointer;

import java.util.List;

import java.util.Arrays;

public class CefDownloadImageCallback extends Structure{
public CefDownloadImageCallback(){
base.size.setValue(this.size());
}

public static interface OnDownloadImageFinishedFunc extends Callback {

void on_download_image_finished(CefDownloadImageCallback x0, Pointer x1, int x2, CefImage x3); 
}

public CefBaseRefCounted base;

public OnDownloadImageFinishedFunc on_download_image_finished;

protected List getFieldOrder() {
                                            return Arrays.asList("base", "on_download_image_finished");
 }}