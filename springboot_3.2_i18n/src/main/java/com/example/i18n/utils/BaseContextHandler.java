package com.example.i18n.utils;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/**
 * @author created by shaos on 2020/8/18
 */
public class BaseContextHandler {

    public static ThreadLocal<Map<String, Object>> threadLocal = new ThreadLocal();
    public static ThreadLocal<Locale> threadLocal2 = new ThreadLocal();


    public static void setI18n(Map<String, Object> map) {
        if (map == null) {
            map = new HashMap<String, Object>();
        }
        threadLocal.set(map);
    }


    public static Map<String, Object> getI18n() {
        Map<String, Object> map = threadLocal.get();
        if (map == null) {
            map = new HashMap<String, Object>();
        }
        return map;
    }

    public static void setLocale(Locale locale) {
        threadLocal2.set(locale);
    }

    public static Locale getLocale() {
        return threadLocal2.get();
    }
}
