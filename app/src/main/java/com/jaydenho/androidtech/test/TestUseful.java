package com.jaydenho.androidtech.test;

import android.util.Log;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 测试时发现的有用的代码。
 * Created by hedazhao on 2019/3/4.
 */
public class TestUseful {
    private static final String TAG = "TestUseful";
    /**
     * 反斜杠。
     */
    private void testBackslash(){
        String str = "\\uD83D\\uDE0E下载得现金【手机迅雷福利版】\\n \\uD83D\\uDCB0 现在加入送最高【18元】\\n [Packet] 领取链接：";
        Log.d(TAG,"testTran-str=" + str);
        String regex = "\\\\(.*?)(?=[^a-z|^0-9])";
        Matcher matcher = Pattern.compile(regex).matcher(str);
        //无法做到将"\\uD83D"换成"\uD83D", 因为"\\"中，第一个反斜杠是转义符号，第二个反斜杠是真正的反斜杠，但是代码run起来后，就无法把这两个反斜杠换成一个转义符号。
        //在代码中操作字符串，和在txt编辑器中操作字符串是不同的，不同点就在于在txt编辑器中能两个反斜杠都是真正的反斜杠，是可以操作的。
        //testFakeUnicode方法 可以把\\uD83D换成\uD83D.
        String str1 = str.replaceAll(regex,"\\$1");
        Log.d(TAG,"testTran-str1=" + str1+"|str="+str);
    }

    private void testFakeUnicode(){
        String source = "\\uD83D\\uDE0E下载得现金【手机迅雷福利版】\\n \\uD83D\\uDCB0 现在加入送最高【18元】\\n [Packet] 领取链接：";
        if (source.contains("\\u")) {
            StringBuffer buf = new StringBuffer();
            Matcher m = Pattern.compile("\\\\u([0-9A-Fa-f]{4})").matcher(source);
            while (m.find()) {
                try {
                    int cp = Integer.parseInt(m.group(1), 16);
                    m.appendReplacement(buf, "");
                    buf.appendCodePoint(cp);
                } catch (NumberFormatException e) {
                }
            }
            m.appendTail(buf);
            String result = buf.toString();
            Log.d(TAG,"testFakeUnicode-result="+result);
        }
    }
}
