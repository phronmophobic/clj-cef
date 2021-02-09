package com.phronemophobic.cljcef;

import com.phronemophobic.cljcef.*;

import com.sun.jna.Structure;

import com.sun.jna.Callback;

import com.sun.jna.Pointer;

import java.util.List;

import java.util.Arrays;

public class CefKeyEvent extends Structure{




public int type;

public int modifiers;

public int windows_key_code;

public int native_key_code;

public int is_system_key;

public char character;

public char unmodified_character;

public int focus_on_editable_field;

protected List getFieldOrder() {
                                            return Arrays.asList("type", "modifiers", "windows_key_code", "native_key_code", "is_system_key", "character", "unmodified_character", "focus_on_editable_field");
 }}