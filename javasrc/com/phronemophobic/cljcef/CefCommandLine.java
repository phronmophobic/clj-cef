package com.phronemophobic.cljcef;

import com.phronemophobic.cljcef.*;

import com.sun.jna.Structure;

import com.sun.jna.Callback;

import com.sun.jna.Pointer;

import java.util.List;

import java.util.Arrays;

public class CefCommandLine extends Structure{
public CefCommandLine(){
base.size.setValue(this.size());
}

public static interface IsValidFunc extends Callback {

int is_valid(CefCommandLine x0); 
}

public static interface IsReadOnlyFunc extends Callback {

int is_read_only(CefCommandLine x0); 
}

public static interface CopyFunc extends Callback {

CefCommandLine copy(CefCommandLine x0); 
}

public static interface InitFromArgvFunc extends Callback {

void init_from_argv(CefCommandLine x0, int x1, Pointer x2); 
}

public static interface InitFromStringFunc extends Callback {

void init_from_string(CefCommandLine x0, Pointer x1); 
}

public static interface ResetFunc extends Callback {

void reset(CefCommandLine x0); 
}

public static interface GetArgvFunc extends Callback {

void get_argv(CefCommandLine x0, Pointer x1); 
}

public static interface GetCommandLineStringFunc extends Callback {

CefStringUtf16 get_command_line_string(CefCommandLine x0); 
}

public static interface GetProgramFunc extends Callback {

CefStringUtf16 get_program(CefCommandLine x0); 
}

public static interface SetProgramFunc extends Callback {

void set_program(CefCommandLine x0, Pointer x1); 
}

public static interface HasSwitchesFunc extends Callback {

int has_switches(CefCommandLine x0); 
}

public static interface HasSwitchFunc extends Callback {

int has_switch(CefCommandLine x0, Pointer x1); 
}

public static interface GetSwitchValueFunc extends Callback {

CefStringUtf16 get_switch_value(CefCommandLine x0, Pointer x1); 
}

public static interface GetSwitchesFunc extends Callback {

void get_switches(CefCommandLine x0, Pointer x1); 
}

public static interface AppendSwitchFunc extends Callback {

void append_switch(CefCommandLine x0, Pointer x1); 
}

public static interface AppendSwitchWithValueFunc extends Callback {

void append_switch_with_value(CefCommandLine x0, Pointer x1, Pointer x2); 
}

public static interface HasArgumentsFunc extends Callback {

int has_arguments(CefCommandLine x0); 
}

public static interface GetArgumentsFunc extends Callback {

void get_arguments(CefCommandLine x0, Pointer x1); 
}

public static interface AppendArgumentFunc extends Callback {

void append_argument(CefCommandLine x0, Pointer x1); 
}

public static interface PrependWrapperFunc extends Callback {

void prepend_wrapper(CefCommandLine x0, Pointer x1); 
}

public CefBaseRefCounted base;

public IsValidFunc is_valid;

public IsReadOnlyFunc is_read_only;

public CopyFunc copy;

public InitFromArgvFunc init_from_argv;

public InitFromStringFunc init_from_string;

public ResetFunc reset;

public GetArgvFunc get_argv;

public GetCommandLineStringFunc get_command_line_string;

public GetProgramFunc get_program;

public SetProgramFunc set_program;

public HasSwitchesFunc has_switches;

public HasSwitchFunc has_switch;

public GetSwitchValueFunc get_switch_value;

public GetSwitchesFunc get_switches;

public AppendSwitchFunc append_switch;

public AppendSwitchWithValueFunc append_switch_with_value;

public HasArgumentsFunc has_arguments;

public GetArgumentsFunc get_arguments;

public AppendArgumentFunc append_argument;

public PrependWrapperFunc prepend_wrapper;

protected List getFieldOrder() {
                                            return Arrays.asList("base", "is_valid", "is_read_only", "copy", "init_from_argv", "init_from_string", "reset", "get_argv", "get_command_line_string", "get_program", "set_program", "has_switches", "has_switch", "get_switch_value", "get_switches", "append_switch", "append_switch_with_value", "has_arguments", "get_arguments", "append_argument", "prepend_wrapper");
 }}