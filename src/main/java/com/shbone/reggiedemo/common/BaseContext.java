package com.shbone.reggiedemo.common;

/**
 * 基于ThreadLocal存储相应的属性值
 * @author sunhb
 */
public class BaseContext {
    private static ThreadLocal<Long> threadLocal = new ThreadLocal();

    public static void setThreadLocal(Long id){
        threadLocal.set(id);
    }

    public static Long getThreadLocal(){
        return threadLocal.get();
    }

}
