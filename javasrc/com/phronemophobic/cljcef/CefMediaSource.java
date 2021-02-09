package com.phronemophobic.cljcef;

import com.phronemophobic.cljcef.*;

import com.sun.jna.Structure;

import com.sun.jna.Callback;

import com.sun.jna.Pointer;

import java.util.List;

import java.util.Arrays;

public class CefMediaSource extends Structure{
public CefMediaSource(){
base.size.setValue(this.size());
}

public static interface GetIdFunc extends Callback {

CefStringUtf16 get_id(CefMediaSource x0); 
}

public static interface IsCastSourceFunc extends Callback {

int is_cast_source(CefMediaSource x0); 
}

public static interface IsDialSourceFunc extends Callback {

int is_dial_source(CefMediaSource x0); 
}

public CefBaseRefCounted base;

public GetIdFunc get_id;

public IsCastSourceFunc is_cast_source;

public IsDialSourceFunc is_dial_source;

protected List getFieldOrder() {
                                            return Arrays.asList("base", "get_id", "is_cast_source", "is_dial_source");
 }}