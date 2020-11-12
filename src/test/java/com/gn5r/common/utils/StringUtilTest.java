package com.gn5r.common.utils;

import org.junit.Test;

public class StringUtilTest {

    @Test
    public void test() {
        final String url = "http://localhost:1111/gn5rapl/spring-boot-sample/index";
        final String contextPath = "/gn5rapl/spring-boot-sample";
        final String subDirectory = url.replaceAll(".*" + contextPath, "");
        System.out.println("サブディレクトリ:" + subDirectory);
    }
}