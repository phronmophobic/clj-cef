package com.phronemophobic.cljcef;

import com.phronemophobic.cljcef.*;

import com.sun.jna.Structure;

import com.sun.jna.Callback;

import com.sun.jna.Pointer;

import java.util.List;

import java.util.Arrays;

public class CefUrlrequestClient extends Structure{
public CefUrlrequestClient(){
base.size.setValue(this.size());
}

public static interface OnRequestCompleteFunc extends Callback {

void on_request_complete(CefUrlrequestClient x0, CefUrlrequest x1); 
}

public static interface OnUploadProgressFunc extends Callback {

void on_upload_progress(CefUrlrequestClient x0, CefUrlrequest x1, long x2, long x3); 
}

public static interface OnDownloadProgressFunc extends Callback {

void on_download_progress(CefUrlrequestClient x0, CefUrlrequest x1, long x2, long x3); 
}

public static interface OnDownloadDataFunc extends Callback {

void on_download_data(CefUrlrequestClient x0, CefUrlrequest x1, Pointer x2, SizeT x3); 
}

public static interface GetAuthCredentialsFunc extends Callback {

int get_auth_credentials(CefUrlrequestClient x0, int x1, Pointer x2, int x3, Pointer x4, Pointer x5, CefAuthCallback x6); 
}

public CefBaseRefCounted base;

public OnRequestCompleteFunc on_request_complete;

public OnUploadProgressFunc on_upload_progress;

public OnDownloadProgressFunc on_download_progress;

public OnDownloadDataFunc on_download_data;

public GetAuthCredentialsFunc get_auth_credentials;

protected List getFieldOrder() {
                                            return Arrays.asList("base", "on_request_complete", "on_upload_progress", "on_download_progress", "on_download_data", "get_auth_credentials");
 }}