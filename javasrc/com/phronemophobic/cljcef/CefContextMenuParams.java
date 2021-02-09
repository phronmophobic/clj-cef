package com.phronemophobic.cljcef;

import com.phronemophobic.cljcef.*;

import com.sun.jna.Structure;

import com.sun.jna.Callback;

import com.sun.jna.Pointer;

import java.util.List;

import java.util.Arrays;

public class CefContextMenuParams extends Structure{
public CefContextMenuParams(){
base.size.setValue(this.size());
}

public static interface GetXcoordFunc extends Callback {

int get_xcoord(CefContextMenuParams x0); 
}

public static interface GetYcoordFunc extends Callback {

int get_ycoord(CefContextMenuParams x0); 
}

public static interface GetTypeFlagsFunc extends Callback {

int get_type_flags(CefContextMenuParams x0); 
}

public static interface GetLinkUrlFunc extends Callback {

CefStringUtf16 get_link_url(CefContextMenuParams x0); 
}

public static interface GetUnfilteredLinkUrlFunc extends Callback {

CefStringUtf16 get_unfiltered_link_url(CefContextMenuParams x0); 
}

public static interface GetSourceUrlFunc extends Callback {

CefStringUtf16 get_source_url(CefContextMenuParams x0); 
}

public static interface HasImageContentsFunc extends Callback {

int has_image_contents(CefContextMenuParams x0); 
}

public static interface GetTitleTextFunc extends Callback {

CefStringUtf16 get_title_text(CefContextMenuParams x0); 
}

public static interface GetPageUrlFunc extends Callback {

CefStringUtf16 get_page_url(CefContextMenuParams x0); 
}

public static interface GetFrameUrlFunc extends Callback {

CefStringUtf16 get_frame_url(CefContextMenuParams x0); 
}

public static interface GetFrameCharsetFunc extends Callback {

CefStringUtf16 get_frame_charset(CefContextMenuParams x0); 
}

public static interface GetMediaTypeFunc extends Callback {

int get_media_type(CefContextMenuParams x0); 
}

public static interface GetMediaStateFlagsFunc extends Callback {

int get_media_state_flags(CefContextMenuParams x0); 
}

public static interface GetSelectionTextFunc extends Callback {

CefStringUtf16 get_selection_text(CefContextMenuParams x0); 
}

public static interface GetMisspelledWordFunc extends Callback {

CefStringUtf16 get_misspelled_word(CefContextMenuParams x0); 
}

public static interface GetDictionarySuggestionsFunc extends Callback {

int get_dictionary_suggestions(CefContextMenuParams x0, Pointer x1); 
}

public static interface IsEditableFunc extends Callback {

int is_editable(CefContextMenuParams x0); 
}

public static interface IsSpellCheckEnabledFunc extends Callback {

int is_spell_check_enabled(CefContextMenuParams x0); 
}

public static interface GetEditStateFlagsFunc extends Callback {

int get_edit_state_flags(CefContextMenuParams x0); 
}

public static interface IsCustomMenuFunc extends Callback {

int is_custom_menu(CefContextMenuParams x0); 
}

public static interface IsPepperMenuFunc extends Callback {

int is_pepper_menu(CefContextMenuParams x0); 
}

public CefBaseRefCounted base;

public GetXcoordFunc get_xcoord;

public GetYcoordFunc get_ycoord;

public GetTypeFlagsFunc get_type_flags;

public GetLinkUrlFunc get_link_url;

public GetUnfilteredLinkUrlFunc get_unfiltered_link_url;

public GetSourceUrlFunc get_source_url;

public HasImageContentsFunc has_image_contents;

public GetTitleTextFunc get_title_text;

public GetPageUrlFunc get_page_url;

public GetFrameUrlFunc get_frame_url;

public GetFrameCharsetFunc get_frame_charset;

public GetMediaTypeFunc get_media_type;

public GetMediaStateFlagsFunc get_media_state_flags;

public GetSelectionTextFunc get_selection_text;

public GetMisspelledWordFunc get_misspelled_word;

public GetDictionarySuggestionsFunc get_dictionary_suggestions;

public IsEditableFunc is_editable;

public IsSpellCheckEnabledFunc is_spell_check_enabled;

public GetEditStateFlagsFunc get_edit_state_flags;

public IsCustomMenuFunc is_custom_menu;

public IsPepperMenuFunc is_pepper_menu;

protected List getFieldOrder() {
                                            return Arrays.asList("base", "get_xcoord", "get_ycoord", "get_type_flags", "get_link_url", "get_unfiltered_link_url", "get_source_url", "has_image_contents", "get_title_text", "get_page_url", "get_frame_url", "get_frame_charset", "get_media_type", "get_media_state_flags", "get_selection_text", "get_misspelled_word", "get_dictionary_suggestions", "is_editable", "is_spell_check_enabled", "get_edit_state_flags", "is_custom_menu", "is_pepper_menu");
 }}