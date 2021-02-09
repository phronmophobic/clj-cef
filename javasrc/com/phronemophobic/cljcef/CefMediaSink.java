package com.phronemophobic.cljcef;

import com.phronemophobic.cljcef.*;

import com.sun.jna.Structure;

import com.sun.jna.Callback;

import com.sun.jna.Pointer;

import java.util.List;

import java.util.Arrays;

public class CefMediaSink extends Structure{
public CefMediaSink(){
base.size.setValue(this.size());
}

public static interface GetIdFunc extends Callback {

CefStringUtf16 get_id(CefMediaSink x0); 
}

public static interface GetNameFunc extends Callback {

CefStringUtf16 get_name(CefMediaSink x0); 
}

public static interface GetDescriptionFunc extends Callback {

CefStringUtf16 get_description(CefMediaSink x0); 
}

public static interface GetIconTypeFunc extends Callback {

int get_icon_type(CefMediaSink x0); 
}

public static interface GetDeviceInfoFunc extends Callback {

void get_device_info(CefMediaSink x0, CefMediaSinkDeviceInfoCallback x1); 
}

public static interface IsCastSinkFunc extends Callback {

int is_cast_sink(CefMediaSink x0); 
}

public static interface IsDialSinkFunc extends Callback {

int is_dial_sink(CefMediaSink x0); 
}

public static interface IsCompatibleWithFunc extends Callback {

int is_compatible_with(CefMediaSink x0, CefMediaSource x1); 
}

public CefBaseRefCounted base;

public GetIdFunc get_id;

public GetNameFunc get_name;

public GetDescriptionFunc get_description;

public GetIconTypeFunc get_icon_type;

public GetDeviceInfoFunc get_device_info;

public IsCastSinkFunc is_cast_sink;

public IsDialSinkFunc is_dial_sink;

public IsCompatibleWithFunc is_compatible_with;

protected List getFieldOrder() {
                                            return Arrays.asList("base", "get_id", "get_name", "get_description", "get_icon_type", "get_device_info", "is_cast_sink", "is_dial_sink", "is_compatible_with");
 }}