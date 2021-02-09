package com.phronemophobic.cljcef;

import com.phronemophobic.cljcef.*;

import com.sun.jna.Structure;

import com.sun.jna.Callback;

import com.sun.jna.Pointer;

import java.util.List;

import java.util.Arrays;

public class CefStreamReader extends Structure{
public CefStreamReader(){
base.size.setValue(this.size());
}

public static interface ReadFunc extends Callback {

SizeT read(CefStreamReader x0, Pointer x1, SizeT x2, SizeT x3); 
}

public static interface SeekFunc extends Callback {

int seek(CefStreamReader x0, long x1, int x2); 
}

public static interface TellFunc extends Callback {

long tell(CefStreamReader x0); 
}

public static interface EofFunc extends Callback {

int eof(CefStreamReader x0); 
}

public static interface MayBlockFunc extends Callback {

int may_block(CefStreamReader x0); 
}

public CefBaseRefCounted base;

public ReadFunc read;

public SeekFunc seek;

public TellFunc tell;

public EofFunc eof;

public MayBlockFunc may_block;

protected List getFieldOrder() {
                                            return Arrays.asList("base", "read", "seek", "tell", "eof", "may_block");
 }}