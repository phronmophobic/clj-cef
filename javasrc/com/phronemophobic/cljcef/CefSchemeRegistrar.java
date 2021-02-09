package com.phronemophobic.cljcef;

import com.phronemophobic.cljcef.*;

import com.sun.jna.Structure;

import com.sun.jna.Callback;

import com.sun.jna.Pointer;

import java.util.List;

import java.util.Arrays;

public class CefSchemeRegistrar extends Structure{
public CefSchemeRegistrar(){
base.size.setValue(this.size());
}

public static interface AddCustomSchemeFunc extends Callback {

int add_custom_scheme(CefSchemeRegistrar x0, Pointer x1, int x2); 
}

public CefBaseScoped base;

public AddCustomSchemeFunc add_custom_scheme;

protected List getFieldOrder() {
                                            return Arrays.asList("base", "add_custom_scheme");
 }}