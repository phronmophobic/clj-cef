package com.phronemophobic.cljcef;

import com.phronemophobic.cljcef.*;

import com.sun.jna.Structure;

import com.sun.jna.Callback;

import com.sun.jna.Pointer;

import java.util.List;

import java.util.Arrays;

public class CefSchemeHandlerFactory extends Structure{
public CefSchemeHandlerFactory(){
base.size.setValue(this.size());
}

public static interface CreateFunc extends Callback {

CefResourceHandler create(CefSchemeHandlerFactory x0, CefBrowser x1, CefFrame x2, Pointer x3, CefRequest x4); 
}

public CefBaseRefCounted base;

public CreateFunc create;

protected List getFieldOrder() {
                                            return Arrays.asList("base", "create");
 }}