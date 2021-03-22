package com.ly.test;

public class StringTest {
    public static void main(String[] args) {
        byte[] bs=new byte[2];
        bs[0]=22;
        bs[1]=23;
        String s=new String(bs);
        System.out.println(s);
    }
}
