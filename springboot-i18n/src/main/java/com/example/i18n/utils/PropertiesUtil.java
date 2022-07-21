package com.example.i18n.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * @author created by shaos on 2020/8/18
 */
public class PropertiesUtil {

    public static Map<String, Object> readPropAsMap(String filepath) {
        try {
            Properties properties = new Properties();
            InputStream inputStream = PropertiesUtil.class.getClassLoader().getResourceAsStream(filepath);
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream)); // 解决读取中文乱码
            properties.load(bufferedReader);

            Map<String, Object> hashMap = new HashMap();

            Enumeration<?> enumeration = properties.propertyNames();
            while (enumeration.hasMoreElements()) {
                String key = (String) enumeration.nextElement();
                String value = properties.getProperty(key);
                hashMap.put(key, value);
                System.out.println(key + ":" + value);

            }
            return hashMap;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;

    }

    public static void main(String[] args) {
        readPropAsMap("i18n/messages_en_US.properties");
    }

}
