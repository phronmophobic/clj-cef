package com.phronemophobic.cljcef;

import com.phronemophobic.cljcef.*;

import com.sun.jna.Structure;

import com.sun.jna.Callback;

import com.sun.jna.Pointer;

import java.util.List;

import java.util.Arrays;

public class CefWriteHandler extends Structure{
public CefWriteHandler(){
base.size.setValue(this.size());
}

public static interface WriteFunc extends Callback {

SizeT write(CefWriteHandler x0, Pointer x1, SizeT x2, SizeT x3); 
}

public static interface SeekFunc extends Callback {

int seek(CefWriteHandler x0, long x1, int x2); 
}

public static interface TellFunc extends Callback {

long tell(CefWriteHandler x0); 
}

public static interface FlushFunc extends Callback {

int flush(CefWriteHandler x0); 
}

public static interface MayBlockFunc extends Callback {

int may_block(CefWriteHandler x0); 
}

public CefBaseRefCounted base;

public WriteFunc write;

public SeekFunc seek;

public TellFunc tell;

public FlushFunc flush;

public MayBlockFunc may_block;

protected List getFieldOrder() {
                                            return Arrays.asList("base", "write", "seek", "tell", "flush", "may_block");
 }}