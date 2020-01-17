package com.geotmt.cacheprime.base.common;

public class CurrentHolder {

    private static ThreadLocal<SystemUser> threadLocal = new ThreadLocal<>();

    public static SystemUser getSystemUser(){
        return threadLocal.get();
    }

    public static void setSystemUser(SystemUser user){
        threadLocal.set(user);
    }
}
