// AUTO GENERATED BY gen2.clj!

package com.phronemophobic.cljcef;

import com.phronemophobic.cljcef.*;

import com.sun.jna.Structure;

import com.sun.jna.Callback;

import com.sun.jna.Pointer;

import java.util.List;

import java.util.Arrays;

public class CefWebPluginUnstableCallback extends Structure{
public CefWebPluginUnstableCallback(){
base.size.setValue(this.size());

ReferenceCountImpl.track(this.getPointer());

}

public static interface IsUnstableFunc extends Callback {

void is_unstable(CefWebPluginUnstableCallback x0, CefStringUtf16 x1, int x2); 
}

public CefBaseRefCounted base;

public IsUnstableFunc is_unstable;

protected List getFieldOrder() {
                                            return Arrays.asList("base", "is_unstable");
 }

public void isUnstable (String x1, int x2) {

  this.is_unstable.is_unstable(this, CljCefLib.toCefString(x1),  x2);

}}