// AUTO GENERATED BY gen2.clj!

package com.phronemophobic.cljcef;

import com.phronemophobic.cljcef.*;

import com.sun.jna.Structure;

import com.sun.jna.Callback;

import com.sun.jna.Pointer;

import java.util.List;

import java.util.Arrays;

public class CefV8exception extends Structure{
public CefV8exception(){
base.size.setValue(this.size());

ReferenceCountImpl.track(this.getPointer());

}

public static interface GetMessageFunc extends Callback {

CefStringUtf16 get_message(CefV8exception x0); 
}

public static interface GetSourceLineFunc extends Callback {

CefStringUtf16 get_source_line(CefV8exception x0); 
}

public static interface GetScriptResourceNameFunc extends Callback {

CefStringUtf16 get_script_resource_name(CefV8exception x0); 
}

public static interface GetLineNumberFunc extends Callback {

int get_line_number(CefV8exception x0); 
}

public static interface GetStartPositionFunc extends Callback {

int get_start_position(CefV8exception x0); 
}

public static interface GetEndPositionFunc extends Callback {

int get_end_position(CefV8exception x0); 
}

public static interface GetStartColumnFunc extends Callback {

int get_start_column(CefV8exception x0); 
}

public static interface GetEndColumnFunc extends Callback {

int get_end_column(CefV8exception x0); 
}

public CefBaseRefCounted base;

public GetMessageFunc get_message;

public GetSourceLineFunc get_source_line;

public GetScriptResourceNameFunc get_script_resource_name;

public GetLineNumberFunc get_line_number;

public GetStartPositionFunc get_start_position;

public GetEndPositionFunc get_end_position;

public GetStartColumnFunc get_start_column;

public GetEndColumnFunc get_end_column;

protected List getFieldOrder() {
                                            return Arrays.asList("base", "get_message", "get_source_line", "get_script_resource_name", "get_line_number", "get_start_position", "get_end_position", "get_start_column", "get_end_column");
 }

public CefStringUtf16 getMessage () {

return  this.get_message.get_message(this);

}

public CefStringUtf16 getSourceLine () {

return  this.get_source_line.get_source_line(this);

}

public CefStringUtf16 getScriptResourceName () {

return  this.get_script_resource_name.get_script_resource_name(this);

}

public int getLineNumber () {

return  this.get_line_number.get_line_number(this);

}

public int getStartPosition () {

return  this.get_start_position.get_start_position(this);

}

public int getEndPosition () {

return  this.get_end_position.get_end_position(this);

}

public int getStartColumn () {

return  this.get_start_column.get_start_column(this);

}

public int getEndColumn () {

return  this.get_end_column.get_end_column(this);

}}