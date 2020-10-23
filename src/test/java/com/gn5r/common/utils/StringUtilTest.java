package com.gn5r.common.utils;

import org.junit.Test;

public class StringUtilTest {

    @Test
    public void test() {
        String test1 = null;
        String test2 = "";
        String test3 = "あいうえお";

        test1 = StringUtil.substring(test1, 3);
        test2 = StringUtil.substring(test2, 4);
        final int utf8 = StringUtil.getByteLength(test3, "UTF8");
        final int ms932 = StringUtil.getByteLength(test3);
        test3 = StringUtil.substring(test3, 4);

        System.out.println("test1:" + test1 + "\ntest2:" + test2 + "\ntest3:" + test3 + "\ntest3のバイト数(UTF8)"
                + String.valueOf(utf8) + "\ntest3のバイト数(MS932)" + String.valueOf(ms932));
    }
}