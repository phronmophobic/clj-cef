package com.phronemophobic.cljcef;

import com.phronemophobic.cljcef.*;

import com.sun.jna.Structure;

import com.sun.jna.Callback;

import com.sun.jna.Pointer;

import java.util.List;

import java.util.Arrays;

public class CefRegisterCdmCallback extends Structure{
public CefRegisterCdmCallback(){
base.size.setValue(this.size());
}

public static interface OnCdmRegistrationCompleteFunc extends Callback {

void on_cdm_registration_complete(CefRegisterCdmCallback x0, int x1, Pointer x2); 
}

public CefBaseRefCounted base;

public OnCdmRegistrationCompleteFunc on_cdm_registration_complete;

protected List getFieldOrder() {
                                            return Arrays.asList("base", "on_cdm_registration_complete");
 }}