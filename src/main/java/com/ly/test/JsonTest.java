package com.ly.test;

import com.alibaba.fastjson.JSON;

public class JsonTest {
    public static void main(String[] args) {
        String jsonStr="{aa:'12',\"bb\":\"123\"//aaa\n}";
        Object obj=JSON.parse(jsonStr);
        System.out.println(obj);
    }
}
