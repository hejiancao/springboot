package com.example.i18n.utils;

import org.springframework.util.StringUtils;

import java.util.Locale;

/**
 * @author created by shaos on 2020/8/19
 */
public class I18nUtil {

    public static String chooseI18nProperties(String language) {
        Locale locale;
        if (StringUtils.isEmpty(language)) {
            locale = new Locale("en", "US");
        } else {
            String[] split = language.split("_");
            locale = new Locale(split[0], split[1]);
        }

        String i18n = null;
        if (Locale.SIMPLIFIED_CHINESE.equals(locale)) {
            i18n = "i18n/messages_zh_CN.properties";
        } else if (Locale.JAPAN.equals(locale)) {
            i18n = "i18n/messages_ja_JP.properties";
        } else {
            i18n = "i18n/messages_en_US.properties";
        }
        return i18n;
    }



}
