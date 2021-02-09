package com.phronemophobic.cljcef;

import com.phronemophobic.cljcef.*;

import com.sun.jna.Structure;

import com.sun.jna.Callback;

import com.sun.jna.Pointer;

import java.util.List;

import java.util.Arrays;

public class CefDownloadItemCallback extends Structure{
public CefDownloadItemCallback(){
base.size.setValue(this.size());
}

public static interface CancelFunc extends Callback {

void cancel(CefDownloadItemCallback x0); 
}

public static interface PauseFunc extends Callback {

void pause(CefDownloadItemCallback x0); 
}

public static interface ResumeFunc extends Callback {

void resume(CefDownloadItemCallback x0); 
}

public CefBaseRefCounted base;

public CancelFunc cancel;

public PauseFunc pause;

public ResumeFunc resume;

protected List getFieldOrder() {
                                            return Arrays.asList("base", "cancel", "pause", "resume");
 }}