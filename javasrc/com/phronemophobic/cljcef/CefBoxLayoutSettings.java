package com.phronemophobic.cljcef;

import com.phronemophobic.cljcef.*;

import com.sun.jna.Structure;

import com.sun.jna.Callback;

import com.sun.jna.Pointer;

import java.util.List;

import java.util.Arrays;

public class CefBoxLayoutSettings extends Structure{




public int horizontal;

public int inside_border_horizontal_spacing;

public int inside_border_vertical_spacing;

public CefInsets inside_border_insets;

public int between_child_spacing;

public int main_axis_alignment;

public int cross_axis_alignment;

public int minimum_cross_axis_size;

public int default_flex;

protected List getFieldOrder() {
                                            return Arrays.asList("horizontal", "inside_border_horizontal_spacing", "inside_border_vertical_spacing", "inside_border_insets", "between_child_spacing", "main_axis_alignment", "cross_axis_alignment", "minimum_cross_axis_size", "default_flex");
 }}