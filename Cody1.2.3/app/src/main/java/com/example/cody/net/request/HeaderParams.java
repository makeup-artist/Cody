package com.example.cody.net.request;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created By cyz on 2019/6/2 18:27
 * e-mail：462065470@qq.com
 */
public class HeaderParams {
    public ConcurrentHashMap<String, String> urlParams = new ConcurrentHashMap<>();

    /**
     * @param source map类型参数
     * @function 构造器 吧用户传来map类型的参数存入我们的集合
     */
    public HeaderParams(Map<String, String> source) {
        if (source != null) {
            //遍历key value集合
            for (Map.Entry<String, String> entry : source.entrySet()) {
                put(entry.getKey(), entry.getValue());
            }
        }
    }

    /**
     * @param key   请求参数key
     * @param value 请求参数value
     * @function key value 放入hashmap
     */
    public void put(String key, String value) {
        if (key != null && value != null) {
            urlParams.put(key, value);
        }
    }
    /**
     * @function 空请求参数
     */
    public HeaderParams() {
        this((Map<String, String>) null);
    }

    /**
     * @funtion 判断是否有请求参数
     */
    public boolean hasParams() {
        if (urlParams.size() > 0) {
            return true;
        }
        return false;
    }
}
