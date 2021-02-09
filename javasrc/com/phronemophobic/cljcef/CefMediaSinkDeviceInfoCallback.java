package com.phronemophobic.cljcef;

import com.phronemophobic.cljcef.*;

import com.sun.jna.Structure;

import com.sun.jna.Callback;

import com.sun.jna.Pointer;

import java.util.List;

import java.util.Arrays;

public class CefMediaSinkDeviceInfoCallback extends Structure{
public CefMediaSinkDeviceInfoCallback(){
base.size.setValue(this.size());
}

public static interface OnMediaSinkDeviceInfoFunc extends Callback {

void on_media_sink_device_info(CefMediaSinkDeviceInfoCallback x0, Pointer x1); 
}

public CefBaseRefCounted base;

public OnMediaSinkDeviceInfoFunc on_media_sink_device_info;

protected List getFieldOrder() {
                                            return Arrays.asList("base", "on_media_sink_device_info");
 }}