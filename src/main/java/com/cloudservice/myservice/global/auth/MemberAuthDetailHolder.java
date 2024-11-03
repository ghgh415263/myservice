package com.cloudservice.myservice.global.auth;

import com.cloudservice.myservice.application.MemberAuthDetail;
import org.springframework.core.NamedThreadLocal;

public class MemberAuthDetailHolder {
    private static final ThreadLocal<MemberAuthDetail> local = new NamedThreadLocal<>("MemberAuthDetail ThreadLocal");

    public static void set(MemberAuthDetail memberAuthDetail){
        local.set(memberAuthDetail);
    }

    public static MemberAuthDetail get(){
        return local.get();
    }
    public static void clear(){
        if (get() == null)
            return;
        local.remove();
    }
}
