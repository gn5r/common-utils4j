package com.gn5r.common.utils;

import org.junit.Test;

public class DateUtilTest {

    @Test
    public void test1() {
        final String date = "2020/01/11";
        final double nendoHanki = DateUtil.getNendoHanki(date);
        System.out.println(date + "の年度半期:" + nendoHanki);
    }

    @Test
    public void test2() {
        final String date = "2019/07/29";
        final double nendoHanki = DateUtil.getNendoHanki(date);
        System.out.println(date + "の年度半期:" + nendoHanki);
    }

    @Test
    public void test3() {
        final String date = "2012/01/02";
        final double diff = DateUtil.diff(null, date);
        System.out.println("差分:" + diff);
    }
}