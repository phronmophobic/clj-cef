package com.phronemophobic.cljcef;

import com.phronemophobic.cljcef.*;

import com.sun.jna.Structure;

import com.sun.jna.Callback;

import com.sun.jna.Pointer;

import java.util.List;

import java.util.Arrays;

public class CefV8value extends Structure{
public CefV8value(){
base.size.setValue(this.size());
}

public static interface IsValidFunc extends Callback {

int is_valid(CefV8value x0); 
}

public static interface IsUndefinedFunc extends Callback {

int is_undefined(CefV8value x0); 
}

public static interface IsNullFunc extends Callback {

int is_null(CefV8value x0); 
}

public static interface IsBoolFunc extends Callback {

int is_bool(CefV8value x0); 
}

public static interface IsIntFunc extends Callback {

int is_int(CefV8value x0); 
}

public static interface IsUintFunc extends Callback {

int is_uint(CefV8value x0); 
}

public static interface IsDoubleFunc extends Callback {

int is_double(CefV8value x0); 
}

public static interface IsDateFunc extends Callback {

int is_date(CefV8value x0); 
}

public static interface IsStringFunc extends Callback {

int is_string(CefV8value x0); 
}

public static interface IsObjectFunc extends Callback {

int is_object(CefV8value x0); 
}

public static interface IsArrayFunc extends Callback {

int is_array(CefV8value x0); 
}

public static interface IsArrayBufferFunc extends Callback {

int is_array_buffer(CefV8value x0); 
}

public static interface IsFunctionFunc extends Callback {

int is_function(CefV8value x0); 
}

public static interface IsSameFunc extends Callback {

int is_same(CefV8value x0, CefV8value x1); 
}

public static interface GetBoolValueFunc extends Callback {

int get_bool_value(CefV8value x0); 
}

public static interface GetIntValueFunc extends Callback {

int get_int_value(CefV8value x0); 
}

public static interface GetUintValueFunc extends Callback {

int get_uint_value(CefV8value x0); 
}

public static interface GetDoubleValueFunc extends Callback {

double get_double_value(CefV8value x0); 
}

public static interface GetDateValueFunc extends Callback {

CefTime get_date_value(CefV8value x0); 
}

public static interface GetStringValueFunc extends Callback {

CefStringUtf16 get_string_value(CefV8value x0); 
}

public static interface IsUserCreatedFunc extends Callback {

int is_user_created(CefV8value x0); 
}

public static interface HasExceptionFunc extends Callback {

int has_exception(CefV8value x0); 
}

public static interface GetExceptionFunc extends Callback {

CefV8exception get_exception(CefV8value x0); 
}

public static interface ClearExceptionFunc extends Callback {

int clear_exception(CefV8value x0); 
}

public static interface WillRethrowExceptionsFunc extends Callback {

int will_rethrow_exceptions(CefV8value x0); 
}

public static interface SetRethrowExceptionsFunc extends Callback {

int set_rethrow_exceptions(CefV8value x0, int x1); 
}

public static interface HasValueBykeyFunc extends Callback {

int has_value_bykey(CefV8value x0, Pointer x1); 
}

public static interface HasValueByindexFunc extends Callback {

int has_value_byindex(CefV8value x0, int x1); 
}

public static interface DeleteValueBykeyFunc extends Callback {

int delete_value_bykey(CefV8value x0, Pointer x1); 
}

public static interface DeleteValueByindexFunc extends Callback {

int delete_value_byindex(CefV8value x0, int x1); 
}

public static interface GetValueBykeyFunc extends Callback {

CefV8value get_value_bykey(CefV8value x0, Pointer x1); 
}

public static interface GetValueByindexFunc extends Callback {

CefV8value get_value_byindex(CefV8value x0, int x1); 
}

public static interface SetValueBykeyFunc extends Callback {

int set_value_bykey(CefV8value x0, Pointer x1, CefV8value x2, int x3); 
}

public static interface SetValueByindexFunc extends Callback {

int set_value_byindex(CefV8value x0, int x1, CefV8value x2); 
}

public static interface SetValueByaccessorFunc extends Callback {

int set_value_byaccessor(CefV8value x0, Pointer x1, int x2, int x3); 
}

public static interface GetKeysFunc extends Callback {

int get_keys(CefV8value x0, Pointer x1); 
}

public static interface SetUserDataFunc extends Callback {

int set_user_data(CefV8value x0, CefBaseRefCounted x1); 
}

public static interface GetUserDataFunc extends Callback {

CefBaseRefCounted get_user_data(CefV8value x0); 
}

public static interface GetExternallyAllocatedMemoryFunc extends Callback {

int get_externally_allocated_memory(CefV8value x0); 
}

public static interface AdjustExternallyAllocatedMemoryFunc extends Callback {

int adjust_externally_allocated_memory(CefV8value x0, int x1); 
}

public static interface GetArrayLengthFunc extends Callback {

int get_array_length(CefV8value x0); 
}

public static interface GetArrayBufferReleaseCallbackFunc extends Callback {

CefV8arrayBufferReleaseCallback get_array_buffer_release_callback(CefV8value x0); 
}

public static interface NeuterArrayBufferFunc extends Callback {

int neuter_array_buffer(CefV8value x0); 
}

public static interface GetFunctionNameFunc extends Callback {

CefStringUtf16 get_function_name(CefV8value x0); 
}

public static interface GetFunctionHandlerFunc extends Callback {

CefV8handler get_function_handler(CefV8value x0); 
}

public static interface ExecuteFunctionFunc extends Callback {

CefV8value execute_function(CefV8value x0, CefV8value x1, SizeT x2, Pointer x3); 
}

public static interface ExecuteFunctionWithContextFunc extends Callback {

CefV8value execute_function_with_context(CefV8value x0, CefV8context x1, CefV8value x2, SizeT x3, Pointer x4); 
}

public CefBaseRefCounted base;

public IsValidFunc is_valid;

public IsUndefinedFunc is_undefined;

public IsNullFunc is_null;

public IsBoolFunc is_bool;

public IsIntFunc is_int;

public IsUintFunc is_uint;

public IsDoubleFunc is_double;

public IsDateFunc is_date;

public IsStringFunc is_string;

public IsObjectFunc is_object;

public IsArrayFunc is_array;

public IsArrayBufferFunc is_array_buffer;

public IsFunctionFunc is_function;

public IsSameFunc is_same;

public GetBoolValueFunc get_bool_value;

public GetIntValueFunc get_int_value;

public GetUintValueFunc get_uint_value;

public GetDoubleValueFunc get_double_value;

public GetDateValueFunc get_date_value;

public GetStringValueFunc get_string_value;

public IsUserCreatedFunc is_user_created;

public HasExceptionFunc has_exception;

public GetExceptionFunc get_exception;

public ClearExceptionFunc clear_exception;

public WillRethrowExceptionsFunc will_rethrow_exceptions;

public SetRethrowExceptionsFunc set_rethrow_exceptions;

public HasValueBykeyFunc has_value_bykey;

public HasValueByindexFunc has_value_byindex;

public DeleteValueBykeyFunc delete_value_bykey;

public DeleteValueByindexFunc delete_value_byindex;

public GetValueBykeyFunc get_value_bykey;

public GetValueByindexFunc get_value_byindex;

public SetValueBykeyFunc set_value_bykey;

public SetValueByindexFunc set_value_byindex;

public SetValueByaccessorFunc set_value_byaccessor;

public GetKeysFunc get_keys;

public SetUserDataFunc set_user_data;

public GetUserDataFunc get_user_data;

public GetExternallyAllocatedMemoryFunc get_externally_allocated_memory;

public AdjustExternallyAllocatedMemoryFunc adjust_externally_allocated_memory;

public GetArrayLengthFunc get_array_length;

public GetArrayBufferReleaseCallbackFunc get_array_buffer_release_callback;

public NeuterArrayBufferFunc neuter_array_buffer;

public GetFunctionNameFunc get_function_name;

public GetFunctionHandlerFunc get_function_handler;

public ExecuteFunctionFunc execute_function;

public ExecuteFunctionWithContextFunc execute_function_with_context;

protected List getFieldOrder() {
                                            return Arrays.asList("base", "is_valid", "is_undefined", "is_null", "is_bool", "is_int", "is_uint", "is_double", "is_date", "is_string", "is_object", "is_array", "is_array_buffer", "is_function", "is_same", "get_bool_value", "get_int_value", "get_uint_value", "get_double_value", "get_date_value", "get_string_value", "is_user_created", "has_exception", "get_exception", "clear_exception", "will_rethrow_exceptions", "set_rethrow_exceptions", "has_value_bykey", "has_value_byindex", "delete_value_bykey", "delete_value_byindex", "get_value_bykey", "get_value_byindex", "set_value_bykey", "set_value_byindex", "set_value_byaccessor", "get_keys", "set_user_data", "get_user_data", "get_externally_allocated_memory", "adjust_externally_allocated_memory", "get_array_length", "get_array_buffer_release_callback", "neuter_array_buffer", "get_function_name", "get_function_handler", "execute_function", "execute_function_with_context");
 }}