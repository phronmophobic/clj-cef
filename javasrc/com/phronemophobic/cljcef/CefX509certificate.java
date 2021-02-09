package com.phronemophobic.cljcef;

import com.phronemophobic.cljcef.*;

import com.sun.jna.Structure;

import com.sun.jna.Callback;

import com.sun.jna.Pointer;

import java.util.List;

import java.util.Arrays;

public class CefX509certificate extends Structure{
public CefX509certificate(){
base.size.setValue(this.size());
}

public static interface GetSubjectFunc extends Callback {

CefX509certPrincipal get_subject(CefX509certificate x0); 
}

public static interface GetIssuerFunc extends Callback {

CefX509certPrincipal get_issuer(CefX509certificate x0); 
}

public static interface GetSerialNumberFunc extends Callback {

CefBinaryValue get_serial_number(CefX509certificate x0); 
}

public static interface GetValidStartFunc extends Callback {

CefTime get_valid_start(CefX509certificate x0); 
}

public static interface GetValidExpiryFunc extends Callback {

CefTime get_valid_expiry(CefX509certificate x0); 
}

public static interface GetDerencodedFunc extends Callback {

CefBinaryValue get_derencoded(CefX509certificate x0); 
}

public static interface GetPemencodedFunc extends Callback {

CefBinaryValue get_pemencoded(CefX509certificate x0); 
}

public static interface GetIssuerChainSizeFunc extends Callback {

SizeT get_issuer_chain_size(CefX509certificate x0); 
}

public static interface GetDerencodedIssuerChainFunc extends Callback {

void get_derencoded_issuer_chain(CefX509certificate x0, Pointer x1, Pointer x2); 
}

public static interface GetPemencodedIssuerChainFunc extends Callback {

void get_pemencoded_issuer_chain(CefX509certificate x0, Pointer x1, Pointer x2); 
}

public CefBaseRefCounted base;

public GetSubjectFunc get_subject;

public GetIssuerFunc get_issuer;

public GetSerialNumberFunc get_serial_number;

public GetValidStartFunc get_valid_start;

public GetValidExpiryFunc get_valid_expiry;

public GetDerencodedFunc get_derencoded;

public GetPemencodedFunc get_pemencoded;

public GetIssuerChainSizeFunc get_issuer_chain_size;

public GetDerencodedIssuerChainFunc get_derencoded_issuer_chain;

public GetPemencodedIssuerChainFunc get_pemencoded_issuer_chain;

protected List getFieldOrder() {
                                            return Arrays.asList("base", "get_subject", "get_issuer", "get_serial_number", "get_valid_start", "get_valid_expiry", "get_derencoded", "get_pemencoded", "get_issuer_chain_size", "get_derencoded_issuer_chain", "get_pemencoded_issuer_chain");
 }}