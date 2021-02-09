package com.phronemophobic.cljcef;

import com.phronemophobic.cljcef.*;

import com.sun.jna.Structure;

import com.sun.jna.Callback;

import com.sun.jna.Pointer;

import java.util.List;

import java.util.Arrays;

public class CefDownloadHandler extends Structure{
public CefDownloadHandler(){
base.size.setValue(this.size());
}

public static interface OnBeforeDownloadFunc extends Callback {

void on_before_download(CefDownloadHandler x0, CefBrowser x1, CefDownloadItem x2, Pointer x3, CefBeforeDownloadCallback x4); 
}

public static interface OnDownloadUpdatedFunc extends Callback {

void on_download_updated(CefDownloadHandler x0, CefBrowser x1, CefDownloadItem x2, CefDownloadItemCallback x3); 
}

public CefBaseRefCounted base;

public OnBeforeDownloadFunc on_before_download;

public OnDownloadUpdatedFunc on_download_updated;

protected List getFieldOrder() {
                                            return Arrays.asList("base", "on_before_download", "on_download_updated");
 }}