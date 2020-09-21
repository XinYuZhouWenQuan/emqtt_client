package com.example.util;

/**
 * @author zhouwenquan
 * @date 2020/9/16 15:34
 * @description: threadLocal
 */
public class ThreadLocalUtil {

    public static final ThreadLocal<Long> userThreadLocal = new ThreadLocal<>();

    public static void setUserId(Long userId){
        userThreadLocal.set(userId);
    }

    public static Long getUserId(){
        return userThreadLocal.get();
    }
}
