package com.phronemophobic.cljcef;

import com.phronemophobic.cljcef.*;

import com.sun.jna.Structure;

import com.sun.jna.Callback;

import com.sun.jna.Pointer;

import java.util.List;

import java.util.Arrays;

public class CefTouchEvent extends Structure{




public int id;

public float x;

public float y;

public float radius_x;

public float radius_y;

public float rotation_angle;

public float pressure;

public int type;

public int modifiers;

public int pointer_type;

protected List getFieldOrder() {
                                            return Arrays.asList("id", "x", "y", "radius_x", "radius_y", "rotation_angle", "pressure", "type", "modifiers", "pointer_type");
 }}