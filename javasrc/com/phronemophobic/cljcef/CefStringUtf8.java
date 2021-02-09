package com.phronemophobic.cljcef;

import com.phronemophobic.cljcef.*;

import com.sun.jna.Structure;

import com.sun.jna.Callback;

import com.sun.jna.Pointer;

import java.util.List;

import java.util.Arrays;

public class CefStringUtf8 extends Structure{


public static interface DtorFunc extends Callback {

void dtor(Pointer x0); 
}

public Pointer str;

public SizeT length;

public DtorFunc dtor;

protected List getFieldOrder() {
                                            return Arrays.asList("str", "length", "dtor");
 }}