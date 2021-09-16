package com.ly.test;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegexMatches {
    public static void main(String[] args) {
        // 按指定模式在字符串查找
        String line = "This.order.was. placed. for. OK?";
        String pattern = "This\\.[^.]+\\.was\\..+\\. OK\\?";
        // 创建 Pattern 对象
        boolean isMatch = Pattern.matches(pattern,line);
        System.out.println(isMatch);
        // 现在创建 matcher 对象
//        Matcher m = r.matcher(line);
//        while (m.find()) {
//            for (int i = 0; i < m.groupCount(); i++) {
//                System.out.println("Found value: " + m.group(i));
//            }
//        }
//        } else {
//            System.out.println("NO MATCH");
//        }
    }
}
