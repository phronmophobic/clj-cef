package com.phronemophobic.cljcef;

import com.phronemophobic.cljcef.*;

import java.util.concurrent.ConcurrentLinkedQueue;

import com.sun.jna.Library;
import com.sun.jna.Native;
import com.sun.jna.Platform;
import com.sun.jna.WString;

public class CljCefLib {

    public interface CljCef extends Library {
        CljCef INSTANCE = (CljCef)
            Native.load("cljcef", CljCef.class);

        int cef_string_wide_to_utf16(WString wstr ,SizeT len, CefStringUtf16 cefstring );

    }

    static ConcurrentLinkedQueue notGarbage;

    static{
        notGarbage = new ConcurrentLinkedQueue<Object>();
    }

    public static CefStringUtf16 toCefString(String s){
        WString wstr = new WString(s);
        CefStringUtf16 cefstring = new CefStringUtf16();
        notGarbage.add(cefstring);
        CljCefLib.CljCef.INSTANCE.cef_string_wide_to_utf16(wstr, new SizeT(wstr.length()), cefstring );
        return cefstring;
    }

        
}
