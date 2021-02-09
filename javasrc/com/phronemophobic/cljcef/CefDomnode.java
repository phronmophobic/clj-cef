package com.phronemophobic.cljcef;

import com.phronemophobic.cljcef.*;

import com.sun.jna.Structure;

import com.sun.jna.Callback;

import com.sun.jna.Pointer;

import java.util.List;

import java.util.Arrays;

public class CefDomnode extends Structure{
public CefDomnode(){
base.size.setValue(this.size());
}

public static interface GetTypeFunc extends Callback {

int get_type(CefDomnode x0); 
}

public static interface IsTextFunc extends Callback {

int is_text(CefDomnode x0); 
}

public static interface IsElementFunc extends Callback {

int is_element(CefDomnode x0); 
}

public static interface IsEditableFunc extends Callback {

int is_editable(CefDomnode x0); 
}

public static interface IsFormControlElementFunc extends Callback {

int is_form_control_element(CefDomnode x0); 
}

public static interface GetFormControlElementTypeFunc extends Callback {

CefStringUtf16 get_form_control_element_type(CefDomnode x0); 
}

public static interface IsSameFunc extends Callback {

int is_same(CefDomnode x0, CefDomnode x1); 
}

public static interface GetNameFunc extends Callback {

CefStringUtf16 get_name(CefDomnode x0); 
}

public static interface GetValueFunc extends Callback {

CefStringUtf16 get_value(CefDomnode x0); 
}

public static interface SetValueFunc extends Callback {

int set_value(CefDomnode x0, Pointer x1); 
}

public static interface GetAsMarkupFunc extends Callback {

CefStringUtf16 get_as_markup(CefDomnode x0); 
}

public static interface GetDocumentFunc extends Callback {

CefDomdocument get_document(CefDomnode x0); 
}

public static interface GetParentFunc extends Callback {

CefDomnode get_parent(CefDomnode x0); 
}

public static interface GetPreviousSiblingFunc extends Callback {

CefDomnode get_previous_sibling(CefDomnode x0); 
}

public static interface GetNextSiblingFunc extends Callback {

CefDomnode get_next_sibling(CefDomnode x0); 
}

public static interface HasChildrenFunc extends Callback {

int has_children(CefDomnode x0); 
}

public static interface GetFirstChildFunc extends Callback {

CefDomnode get_first_child(CefDomnode x0); 
}

public static interface GetLastChildFunc extends Callback {

CefDomnode get_last_child(CefDomnode x0); 
}

public static interface GetElementTagNameFunc extends Callback {

CefStringUtf16 get_element_tag_name(CefDomnode x0); 
}

public static interface HasElementAttributesFunc extends Callback {

int has_element_attributes(CefDomnode x0); 
}

public static interface HasElementAttributeFunc extends Callback {

int has_element_attribute(CefDomnode x0, Pointer x1); 
}

public static interface GetElementAttributeFunc extends Callback {

CefStringUtf16 get_element_attribute(CefDomnode x0, Pointer x1); 
}

public static interface GetElementAttributesFunc extends Callback {

void get_element_attributes(CefDomnode x0, Pointer x1); 
}

public static interface SetElementAttributeFunc extends Callback {

int set_element_attribute(CefDomnode x0, Pointer x1, Pointer x2); 
}

public static interface GetElementInnerTextFunc extends Callback {

CefStringUtf16 get_element_inner_text(CefDomnode x0); 
}

public static interface GetElementBoundsFunc extends Callback {

CefRect get_element_bounds(CefDomnode x0); 
}

public CefBaseRefCounted base;

public GetTypeFunc get_type;

public IsTextFunc is_text;

public IsElementFunc is_element;

public IsEditableFunc is_editable;

public IsFormControlElementFunc is_form_control_element;

public GetFormControlElementTypeFunc get_form_control_element_type;

public IsSameFunc is_same;

public GetNameFunc get_name;

public GetValueFunc get_value;

public SetValueFunc set_value;

public GetAsMarkupFunc get_as_markup;

public GetDocumentFunc get_document;

public GetParentFunc get_parent;

public GetPreviousSiblingFunc get_previous_sibling;

public GetNextSiblingFunc get_next_sibling;

public HasChildrenFunc has_children;

public GetFirstChildFunc get_first_child;

public GetLastChildFunc get_last_child;

public GetElementTagNameFunc get_element_tag_name;

public HasElementAttributesFunc has_element_attributes;

public HasElementAttributeFunc has_element_attribute;

public GetElementAttributeFunc get_element_attribute;

public GetElementAttributesFunc get_element_attributes;

public SetElementAttributeFunc set_element_attribute;

public GetElementInnerTextFunc get_element_inner_text;

public GetElementBoundsFunc get_element_bounds;

protected List getFieldOrder() {
                                            return Arrays.asList("base", "get_type", "is_text", "is_element", "is_editable", "is_form_control_element", "get_form_control_element_type", "is_same", "get_name", "get_value", "set_value", "get_as_markup", "get_document", "get_parent", "get_previous_sibling", "get_next_sibling", "has_children", "get_first_child", "get_last_child", "get_element_tag_name", "has_element_attributes", "has_element_attribute", "get_element_attribute", "get_element_attributes", "set_element_attribute", "get_element_inner_text", "get_element_bounds");
 }}