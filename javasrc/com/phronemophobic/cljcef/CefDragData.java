package com.phronemophobic.cljcef;

import com.phronemophobic.cljcef.*;

import com.sun.jna.Structure;

import com.sun.jna.Callback;

import com.sun.jna.Pointer;

import java.util.List;

import java.util.Arrays;

public class CefDragData extends Structure{
public CefDragData(){
base.size.setValue(this.size());
}

public static interface CloneFunc extends Callback {

CefDragData clone(CefDragData x0); 
}

public static interface IsReadOnlyFunc extends Callback {

int is_read_only(CefDragData x0); 
}

public static interface IsLinkFunc extends Callback {

int is_link(CefDragData x0); 
}

public static interface IsFragmentFunc extends Callback {

int is_fragment(CefDragData x0); 
}

public static interface IsFileFunc extends Callback {

int is_file(CefDragData x0); 
}

public static interface GetLinkUrlFunc extends Callback {

CefStringUtf16 get_link_url(CefDragData x0); 
}

public static interface GetLinkTitleFunc extends Callback {

CefStringUtf16 get_link_title(CefDragData x0); 
}

public static interface GetLinkMetadataFunc extends Callback {

CefStringUtf16 get_link_metadata(CefDragData x0); 
}

public static interface GetFragmentTextFunc extends Callback {

CefStringUtf16 get_fragment_text(CefDragData x0); 
}

public static interface GetFragmentHtmlFunc extends Callback {

CefStringUtf16 get_fragment_html(CefDragData x0); 
}

public static interface GetFragmentBaseUrlFunc extends Callback {

CefStringUtf16 get_fragment_base_url(CefDragData x0); 
}

public static interface GetFileNameFunc extends Callback {

CefStringUtf16 get_file_name(CefDragData x0); 
}

public static interface GetFileContentsFunc extends Callback {

SizeT get_file_contents(CefDragData x0, CefStreamWriter x1); 
}

public static interface GetFileNamesFunc extends Callback {

int get_file_names(CefDragData x0, Pointer x1); 
}

public static interface SetLinkUrlFunc extends Callback {

void set_link_url(CefDragData x0, Pointer x1); 
}

public static interface SetLinkTitleFunc extends Callback {

void set_link_title(CefDragData x0, Pointer x1); 
}

public static interface SetLinkMetadataFunc extends Callback {

void set_link_metadata(CefDragData x0, Pointer x1); 
}

public static interface SetFragmentTextFunc extends Callback {

void set_fragment_text(CefDragData x0, Pointer x1); 
}

public static interface SetFragmentHtmlFunc extends Callback {

void set_fragment_html(CefDragData x0, Pointer x1); 
}

public static interface SetFragmentBaseUrlFunc extends Callback {

void set_fragment_base_url(CefDragData x0, Pointer x1); 
}

public static interface ResetFileContentsFunc extends Callback {

void reset_file_contents(CefDragData x0); 
}

public static interface AddFileFunc extends Callback {

void add_file(CefDragData x0, Pointer x1, Pointer x2); 
}

public static interface GetImageFunc extends Callback {

CefImage get_image(CefDragData x0); 
}

public static interface GetImageHotspotFunc extends Callback {

CefPoint get_image_hotspot(CefDragData x0); 
}

public static interface HasImageFunc extends Callback {

int has_image(CefDragData x0); 
}

public CefBaseRefCounted base;

public CloneFunc clone;

public IsReadOnlyFunc is_read_only;

public IsLinkFunc is_link;

public IsFragmentFunc is_fragment;

public IsFileFunc is_file;

public GetLinkUrlFunc get_link_url;

public GetLinkTitleFunc get_link_title;

public GetLinkMetadataFunc get_link_metadata;

public GetFragmentTextFunc get_fragment_text;

public GetFragmentHtmlFunc get_fragment_html;

public GetFragmentBaseUrlFunc get_fragment_base_url;

public GetFileNameFunc get_file_name;

public GetFileContentsFunc get_file_contents;

public GetFileNamesFunc get_file_names;

public SetLinkUrlFunc set_link_url;

public SetLinkTitleFunc set_link_title;

public SetLinkMetadataFunc set_link_metadata;

public SetFragmentTextFunc set_fragment_text;

public SetFragmentHtmlFunc set_fragment_html;

public SetFragmentBaseUrlFunc set_fragment_base_url;

public ResetFileContentsFunc reset_file_contents;

public AddFileFunc add_file;

public GetImageFunc get_image;

public GetImageHotspotFunc get_image_hotspot;

public HasImageFunc has_image;

protected List getFieldOrder() {
                                            return Arrays.asList("base", "clone", "is_read_only", "is_link", "is_fragment", "is_file", "get_link_url", "get_link_title", "get_link_metadata", "get_fragment_text", "get_fragment_html", "get_fragment_base_url", "get_file_name", "get_file_contents", "get_file_names", "set_link_url", "set_link_title", "set_link_metadata", "set_fragment_text", "set_fragment_html", "set_fragment_base_url", "reset_file_contents", "add_file", "get_image", "get_image_hotspot", "has_image");
 }}