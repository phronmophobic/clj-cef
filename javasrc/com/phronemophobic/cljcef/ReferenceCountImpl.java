package com.phronemophobic.cljcef;

import com.phronemophobic.cljcef.CefBaseRefCounted;
import com.sun.jna.Pointer;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.ConcurrentHashMap;
import java.lang.System;

public class ReferenceCountImpl{


    static ConcurrentHashMap<Pointer,AtomicInteger> notGarbage;
    static{
        notGarbage = new ConcurrentHashMap();
    }

    public static void track(Pointer ref){
        CefBaseRefCounted base = CefBaseRefCounted.newInstance(CefBaseRefCounted.class, ref);

        base.add_ref = new CefBaseRefCounted.AddRefFunc(){
                public void add_ref(Pointer x0){
                    AtomicInteger ai = notGarbage.get(x0);
                    if ( ai != null ){
                        ai.incrementAndGet();
                    }else{
                        System.err.println("Phantom retain! "+ x0);
                    }
                }
            };
        base.release = new CefBaseRefCounted.ReleaseFunc(){
                public int release(Pointer x0){
                    AtomicInteger ai = notGarbage.get(x0);
                    int count;
                    if ( ai != null ){
                        count = ai.decrementAndGet();

                        boolean should_delete = (count == 0);
                        if (should_delete){
                            notGarbage.remove(x0);
                            return 1;
                        } else{
                            return 0;
                        }
                    }else{
                        System.err.println("Phantom release! "+ x0);
                        return 0;
                    }
                }
            };

        base.has_one_ref = new CefBaseRefCounted.HasOneRefFunc(){
                public int has_one_ref(Pointer x0){
                    AtomicInteger ai = notGarbage.get(x0);
                    if ( ai != null){
                        int count = ai.get();
                        if ( count == 1){
                            return 1;
                        }else{
                            return 0;
                        }
                    }else{
                        System.err.println("Phantom has_one_ref! "+ x0);
                        return 0;
                    }
                }
            };

            base.has_at_least_one_ref = new CefBaseRefCounted.HasAtLeastOneRefFunc(){
                    public int has_at_least_one_ref(Pointer x0){
                        AtomicInteger ai = notGarbage.get(x0);
                        if ( ai != null ){
                            int count = ai.get();
                            if ( count >= 1){
                                return 1;
                            }else{
                                return 0;
                            }
                        }else{
                            System.err.println("Phantom has_one_ref! "+ x0);
                            return 0;
                        }
                    }
                };

        
        notGarbage.put(ref, new AtomicInteger(1));


    }

}
