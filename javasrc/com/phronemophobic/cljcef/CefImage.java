package com.phronemophobic.cljcef;

import com.phronemophobic.cljcef.*;

import com.sun.jna.Structure;

import com.sun.jna.Callback;

import com.sun.jna.Pointer;

import java.util.List;

import java.util.Arrays;

public class CefImage extends Structure{
public CefImage(){
base.size.setValue(this.size());
}

public static interface IsEmptyFunc extends Callback {

int is_empty(CefImage x0); 
}

public static interface IsSameFunc extends Callback {

int is_same(CefImage x0, CefImage x1); 
}

public static interface AddBitmapFunc extends Callback {

int add_bitmap(CefImage x0, float x1, int x2, int x3, int x4, int x5, Pointer x6, SizeT x7); 
}

public static interface AddPngFunc extends Callback {

int add_png(CefImage x0, float x1, Pointer x2, SizeT x3); 
}

public static interface AddJpegFunc extends Callback {

int add_jpeg(CefImage x0, float x1, Pointer x2, SizeT x3); 
}

public static interface GetWidthFunc extends Callback {

SizeT get_width(CefImage x0); 
}

public static interface GetHeightFunc extends Callback {

SizeT get_height(CefImage x0); 
}

public static interface HasRepresentationFunc extends Callback {

int has_representation(CefImage x0, float x1); 
}

public static interface RemoveRepresentationFunc extends Callback {

int remove_representation(CefImage x0, float x1); 
}

public static interface GetRepresentationInfoFunc extends Callback {

int get_representation_info(CefImage x0, float x1, Pointer x2, Pointer x3, Pointer x4); 
}

public static interface GetAsBitmapFunc extends Callback {

CefBinaryValue get_as_bitmap(CefImage x0, float x1, int x2, int x3, Pointer x4, Pointer x5); 
}

public static interface GetAsPngFunc extends Callback {

CefBinaryValue get_as_png(CefImage x0, float x1, int x2, Pointer x3, Pointer x4); 
}

public static interface GetAsJpegFunc extends Callback {

CefBinaryValue get_as_jpeg(CefImage x0, float x1, int x2, Pointer x3, Pointer x4); 
}

public CefBaseRefCounted base;

public IsEmptyFunc is_empty;

public IsSameFunc is_same;

public AddBitmapFunc add_bitmap;

public AddPngFunc add_png;

public AddJpegFunc add_jpeg;

public GetWidthFunc get_width;

public GetHeightFunc get_height;

public HasRepresentationFunc has_representation;

public RemoveRepresentationFunc remove_representation;

public GetRepresentationInfoFunc get_representation_info;

public GetAsBitmapFunc get_as_bitmap;

public GetAsPngFunc get_as_png;

public GetAsJpegFunc get_as_jpeg;

protected List getFieldOrder() {
                                            return Arrays.asList("base", "is_empty", "is_same", "add_bitmap", "add_png", "add_jpeg", "get_width", "get_height", "has_representation", "remove_representation", "get_representation_info", "get_as_bitmap", "get_as_png", "get_as_jpeg");
 }}