package com.phronemophobic.cljcef;

import com.phronemophobic.cljcef.*;

import com.sun.jna.Structure;

import com.sun.jna.Callback;

import com.sun.jna.Pointer;

import java.util.List;

import java.util.Arrays;

public class CefDownloadItem extends Structure{
public CefDownloadItem(){
base.size.setValue(this.size());
}

public static interface IsValidFunc extends Callback {

int is_valid(CefDownloadItem x0); 
}

public static interface IsInProgressFunc extends Callback {

int is_in_progress(CefDownloadItem x0); 
}

public static interface IsCompleteFunc extends Callback {

int is_complete(CefDownloadItem x0); 
}

public static interface IsCanceledFunc extends Callback {

int is_canceled(CefDownloadItem x0); 
}

public static interface GetCurrentSpeedFunc extends Callback {

long get_current_speed(CefDownloadItem x0); 
}

public static interface GetPercentCompleteFunc extends Callback {

int get_percent_complete(CefDownloadItem x0); 
}

public static interface GetTotalBytesFunc extends Callback {

long get_total_bytes(CefDownloadItem x0); 
}

public static interface GetReceivedBytesFunc extends Callback {

long get_received_bytes(CefDownloadItem x0); 
}

public static interface GetStartTimeFunc extends Callback {

CefTime get_start_time(CefDownloadItem x0); 
}

public static interface GetEndTimeFunc extends Callback {

CefTime get_end_time(CefDownloadItem x0); 
}

public static interface GetFullPathFunc extends Callback {

CefStringUtf16 get_full_path(CefDownloadItem x0); 
}

public static interface GetIdFunc extends Callback {

int get_id(CefDownloadItem x0); 
}

public static interface GetUrlFunc extends Callback {

CefStringUtf16 get_url(CefDownloadItem x0); 
}

public static interface GetOriginalUrlFunc extends Callback {

CefStringUtf16 get_original_url(CefDownloadItem x0); 
}

public static interface GetSuggestedFileNameFunc extends Callback {

CefStringUtf16 get_suggested_file_name(CefDownloadItem x0); 
}

public static interface GetContentDispositionFunc extends Callback {

CefStringUtf16 get_content_disposition(CefDownloadItem x0); 
}

public static interface GetMimeTypeFunc extends Callback {

CefStringUtf16 get_mime_type(CefDownloadItem x0); 
}

public CefBaseRefCounted base;

public IsValidFunc is_valid;

public IsInProgressFunc is_in_progress;

public IsCompleteFunc is_complete;

public IsCanceledFunc is_canceled;

public GetCurrentSpeedFunc get_current_speed;

public GetPercentCompleteFunc get_percent_complete;

public GetTotalBytesFunc get_total_bytes;

public GetReceivedBytesFunc get_received_bytes;

public GetStartTimeFunc get_start_time;

public GetEndTimeFunc get_end_time;

public GetFullPathFunc get_full_path;

public GetIdFunc get_id;

public GetUrlFunc get_url;

public GetOriginalUrlFunc get_original_url;

public GetSuggestedFileNameFunc get_suggested_file_name;

public GetContentDispositionFunc get_content_disposition;

public GetMimeTypeFunc get_mime_type;

protected List getFieldOrder() {
                                            return Arrays.asList("base", "is_valid", "is_in_progress", "is_complete", "is_canceled", "get_current_speed", "get_percent_complete", "get_total_bytes", "get_received_bytes", "get_start_time", "get_end_time", "get_full_path", "get_id", "get_url", "get_original_url", "get_suggested_file_name", "get_content_disposition", "get_mime_type");
 }}