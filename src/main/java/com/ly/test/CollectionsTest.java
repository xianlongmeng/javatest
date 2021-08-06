package com.ly.test;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class CollectionsTest {
    public static void main(String[] args) {
        Map<String,String> m=new ConcurrentHashMap<>(null);
        m.put("123","value");
        System.out.println(m);
    }
}
