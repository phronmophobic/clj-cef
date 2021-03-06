// AUTO GENERATED BY gen2.clj!

package com.phronemophobic.cljcef;

import com.phronemophobic.cljcef.*;

import com.sun.jna.Structure;

import com.sun.jna.Callback;

import com.sun.jna.Pointer;

import java.util.List;

import java.util.Arrays;

public class CefMediaRoute extends Structure{
public CefMediaRoute(){
base.size.setValue(this.size());

ReferenceCountImpl.track(this.getPointer());

}

public static interface GetIdFunc extends Callback {

CefStringUtf16 get_id(CefMediaRoute x0); 
}

public static interface GetSourceFunc extends Callback {

CefMediaSource get_source(CefMediaRoute x0); 
}

public static interface GetSinkFunc extends Callback {

CefMediaSink get_sink(CefMediaRoute x0); 
}

public static interface SendRouteMessageFunc extends Callback {

void send_route_message(CefMediaRoute x0, Pointer x1, SizeT x2); 
}

public static interface TerminateFunc extends Callback {

void terminate(CefMediaRoute x0); 
}

public CefBaseRefCounted base;

public GetIdFunc get_id;

public GetSourceFunc get_source;

public GetSinkFunc get_sink;

public SendRouteMessageFunc send_route_message;

public TerminateFunc terminate;

protected List getFieldOrder() {
                                            return Arrays.asList("base", "get_id", "get_source", "get_sink", "send_route_message", "terminate");
 }

public CefStringUtf16 getId () {

return  this.get_id.get_id(this);

}

public CefMediaSource getSource () {

return  this.get_source.get_source(this);

}

public CefMediaSink getSink () {

return  this.get_sink.get_sink(this);

}

public void sendRouteMessage (Pointer x1, SizeT x2) {

  this.send_route_message.send_route_message(this,  x1,  x2);

}

public void terminate () {

  this.terminate.terminate(this);

}}