package com.jaydenho.androidtech.regex;

/**
 * Created by hedazhao on 2018/6/23.
 */
public class RegexClient {
    public static void main(String[] args) {
        RegexTest.testSplit("boo:and:foo", "o", 6);//[b, , :and:f, , ]
        RegexTest.testSplit("boo:and:fooo", "o", 6);//[b, , :and:f, , , ]
        RegexTest.testSplit("boo:and:foo", ":", 5);//[boo, and, foo]
        RegexTest.testSplit("boo:and:foo", ":", 0);//[boo, and, foo]
    }
}
