package com.phronemophobic.cljcef;

import com.phronemophobic.cljcef.*;

import com.sun.jna.Structure;

import com.sun.jna.Callback;

import com.sun.jna.Pointer;

import java.util.List;

import java.util.Arrays;

public class CefMenuModel extends Structure{
public CefMenuModel(){
base.size.setValue(this.size());
}

public static interface IsSubMenuFunc extends Callback {

int is_sub_menu(CefMenuModel x0); 
}

public static interface ClearFunc extends Callback {

int clear(CefMenuModel x0); 
}

public static interface GetCountFunc extends Callback {

int get_count(CefMenuModel x0); 
}

public static interface AddSeparatorFunc extends Callback {

int add_separator(CefMenuModel x0); 
}

public static interface AddItemFunc extends Callback {

int add_item(CefMenuModel x0, int x1, Pointer x2); 
}

public static interface AddCheckItemFunc extends Callback {

int add_check_item(CefMenuModel x0, int x1, Pointer x2); 
}

public static interface AddRadioItemFunc extends Callback {

int add_radio_item(CefMenuModel x0, int x1, Pointer x2, int x3); 
}

public static interface AddSubMenuFunc extends Callback {

CefMenuModel add_sub_menu(CefMenuModel x0, int x1, Pointer x2); 
}

public static interface InsertSeparatorAtFunc extends Callback {

int insert_separator_at(CefMenuModel x0, int x1); 
}

public static interface InsertItemAtFunc extends Callback {

int insert_item_at(CefMenuModel x0, int x1, int x2, Pointer x3); 
}

public static interface InsertCheckItemAtFunc extends Callback {

int insert_check_item_at(CefMenuModel x0, int x1, int x2, Pointer x3); 
}

public static interface InsertRadioItemAtFunc extends Callback {

int insert_radio_item_at(CefMenuModel x0, int x1, int x2, Pointer x3, int x4); 
}

public static interface InsertSubMenuAtFunc extends Callback {

CefMenuModel insert_sub_menu_at(CefMenuModel x0, int x1, int x2, Pointer x3); 
}

public static interface RemoveFunc extends Callback {

int remove(CefMenuModel x0, int x1); 
}

public static interface RemoveAtFunc extends Callback {

int remove_at(CefMenuModel x0, int x1); 
}

public static interface GetIndexOfFunc extends Callback {

int get_index_of(CefMenuModel x0, int x1); 
}

public static interface GetCommandIdAtFunc extends Callback {

int get_command_id_at(CefMenuModel x0, int x1); 
}

public static interface SetCommandIdAtFunc extends Callback {

int set_command_id_at(CefMenuModel x0, int x1, int x2); 
}

public static interface GetLabelFunc extends Callback {

CefStringUtf16 get_label(CefMenuModel x0, int x1); 
}

public static interface GetLabelAtFunc extends Callback {

CefStringUtf16 get_label_at(CefMenuModel x0, int x1); 
}

public static interface SetLabelFunc extends Callback {

int set_label(CefMenuModel x0, int x1, Pointer x2); 
}

public static interface SetLabelAtFunc extends Callback {

int set_label_at(CefMenuModel x0, int x1, Pointer x2); 
}

public static interface GetTypeFunc extends Callback {

int get_type(CefMenuModel x0, int x1); 
}

public static interface GetTypeAtFunc extends Callback {

int get_type_at(CefMenuModel x0, int x1); 
}

public static interface GetGroupIdFunc extends Callback {

int get_group_id(CefMenuModel x0, int x1); 
}

public static interface GetGroupIdAtFunc extends Callback {

int get_group_id_at(CefMenuModel x0, int x1); 
}

public static interface SetGroupIdFunc extends Callback {

int set_group_id(CefMenuModel x0, int x1, int x2); 
}

public static interface SetGroupIdAtFunc extends Callback {

int set_group_id_at(CefMenuModel x0, int x1, int x2); 
}

public static interface GetSubMenuFunc extends Callback {

CefMenuModel get_sub_menu(CefMenuModel x0, int x1); 
}

public static interface GetSubMenuAtFunc extends Callback {

CefMenuModel get_sub_menu_at(CefMenuModel x0, int x1); 
}

public static interface IsVisibleFunc extends Callback {

int is_visible(CefMenuModel x0, int x1); 
}

public static interface IsVisibleAtFunc extends Callback {

int is_visible_at(CefMenuModel x0, int x1); 
}

public static interface SetVisibleFunc extends Callback {

int set_visible(CefMenuModel x0, int x1, int x2); 
}

public static interface SetVisibleAtFunc extends Callback {

int set_visible_at(CefMenuModel x0, int x1, int x2); 
}

public static interface IsEnabledFunc extends Callback {

int is_enabled(CefMenuModel x0, int x1); 
}

public static interface IsEnabledAtFunc extends Callback {

int is_enabled_at(CefMenuModel x0, int x1); 
}

public static interface SetEnabledFunc extends Callback {

int set_enabled(CefMenuModel x0, int x1, int x2); 
}

public static interface SetEnabledAtFunc extends Callback {

int set_enabled_at(CefMenuModel x0, int x1, int x2); 
}

public static interface IsCheckedFunc extends Callback {

int is_checked(CefMenuModel x0, int x1); 
}

public static interface IsCheckedAtFunc extends Callback {

int is_checked_at(CefMenuModel x0, int x1); 
}

public static interface SetCheckedFunc extends Callback {

int set_checked(CefMenuModel x0, int x1, int x2); 
}

public static interface SetCheckedAtFunc extends Callback {

int set_checked_at(CefMenuModel x0, int x1, int x2); 
}

public static interface HasAcceleratorFunc extends Callback {

int has_accelerator(CefMenuModel x0, int x1); 
}

public static interface HasAcceleratorAtFunc extends Callback {

int has_accelerator_at(CefMenuModel x0, int x1); 
}

public static interface SetAcceleratorFunc extends Callback {

int set_accelerator(CefMenuModel x0, int x1, int x2, int x3, int x4, int x5); 
}

public static interface SetAcceleratorAtFunc extends Callback {

int set_accelerator_at(CefMenuModel x0, int x1, int x2, int x3, int x4, int x5); 
}

public static interface RemoveAcceleratorFunc extends Callback {

int remove_accelerator(CefMenuModel x0, int x1); 
}

public static interface RemoveAcceleratorAtFunc extends Callback {

int remove_accelerator_at(CefMenuModel x0, int x1); 
}

public static interface GetAcceleratorFunc extends Callback {

int get_accelerator(CefMenuModel x0, int x1, Pointer x2, Pointer x3, Pointer x4, Pointer x5); 
}

public static interface GetAcceleratorAtFunc extends Callback {

int get_accelerator_at(CefMenuModel x0, int x1, Pointer x2, Pointer x3, Pointer x4, Pointer x5); 
}

public static interface SetColorFunc extends Callback {

int set_color(CefMenuModel x0, int x1, int x2, int x3); 
}

public static interface SetColorAtFunc extends Callback {

int set_color_at(CefMenuModel x0, int x1, int x2, int x3); 
}

public static interface GetColorFunc extends Callback {

int get_color(CefMenuModel x0, int x1, int x2, int x3); 
}

public static interface GetColorAtFunc extends Callback {

int get_color_at(CefMenuModel x0, int x1, int x2, int x3); 
}

public static interface SetFontListFunc extends Callback {

int set_font_list(CefMenuModel x0, int x1, Pointer x2); 
}

public static interface SetFontListAtFunc extends Callback {

int set_font_list_at(CefMenuModel x0, int x1, Pointer x2); 
}

public CefBaseRefCounted base;

public IsSubMenuFunc is_sub_menu;

public ClearFunc clear;

public GetCountFunc get_count;

public AddSeparatorFunc add_separator;

public AddItemFunc add_item;

public AddCheckItemFunc add_check_item;

public AddRadioItemFunc add_radio_item;

public AddSubMenuFunc add_sub_menu;

public InsertSeparatorAtFunc insert_separator_at;

public InsertItemAtFunc insert_item_at;

public InsertCheckItemAtFunc insert_check_item_at;

public InsertRadioItemAtFunc insert_radio_item_at;

public InsertSubMenuAtFunc insert_sub_menu_at;

public RemoveFunc remove;

public RemoveAtFunc remove_at;

public GetIndexOfFunc get_index_of;

public GetCommandIdAtFunc get_command_id_at;

public SetCommandIdAtFunc set_command_id_at;

public GetLabelFunc get_label;

public GetLabelAtFunc get_label_at;

public SetLabelFunc set_label;

public SetLabelAtFunc set_label_at;

public GetTypeFunc get_type;

public GetTypeAtFunc get_type_at;

public GetGroupIdFunc get_group_id;

public GetGroupIdAtFunc get_group_id_at;

public SetGroupIdFunc set_group_id;

public SetGroupIdAtFunc set_group_id_at;

public GetSubMenuFunc get_sub_menu;

public GetSubMenuAtFunc get_sub_menu_at;

public IsVisibleFunc is_visible;

public IsVisibleAtFunc is_visible_at;

public SetVisibleFunc set_visible;

public SetVisibleAtFunc set_visible_at;

public IsEnabledFunc is_enabled;

public IsEnabledAtFunc is_enabled_at;

public SetEnabledFunc set_enabled;

public SetEnabledAtFunc set_enabled_at;

public IsCheckedFunc is_checked;

public IsCheckedAtFunc is_checked_at;

public SetCheckedFunc set_checked;

public SetCheckedAtFunc set_checked_at;

public HasAcceleratorFunc has_accelerator;

public HasAcceleratorAtFunc has_accelerator_at;

public SetAcceleratorFunc set_accelerator;

public SetAcceleratorAtFunc set_accelerator_at;

public RemoveAcceleratorFunc remove_accelerator;

public RemoveAcceleratorAtFunc remove_accelerator_at;

public GetAcceleratorFunc get_accelerator;

public GetAcceleratorAtFunc get_accelerator_at;

public SetColorFunc set_color;

public SetColorAtFunc set_color_at;

public GetColorFunc get_color;

public GetColorAtFunc get_color_at;

public SetFontListFunc set_font_list;

public SetFontListAtFunc set_font_list_at;

protected List getFieldOrder() {
                                            return Arrays.asList("base", "is_sub_menu", "clear", "get_count", "add_separator", "add_item", "add_check_item", "add_radio_item", "add_sub_menu", "insert_separator_at", "insert_item_at", "insert_check_item_at", "insert_radio_item_at", "insert_sub_menu_at", "remove", "remove_at", "get_index_of", "get_command_id_at", "set_command_id_at", "get_label", "get_label_at", "set_label", "set_label_at", "get_type", "get_type_at", "get_group_id", "get_group_id_at", "set_group_id", "set_group_id_at", "get_sub_menu", "get_sub_menu_at", "is_visible", "is_visible_at", "set_visible", "set_visible_at", "is_enabled", "is_enabled_at", "set_enabled", "set_enabled_at", "is_checked", "is_checked_at", "set_checked", "set_checked_at", "has_accelerator", "has_accelerator_at", "set_accelerator", "set_accelerator_at", "remove_accelerator", "remove_accelerator_at", "get_accelerator", "get_accelerator_at", "set_color", "set_color_at", "get_color", "get_color_at", "set_font_list", "set_font_list_at");
 }}