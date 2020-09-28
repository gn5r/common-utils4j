package com.gn5r.common.utils;

import java.util.Arrays;

import org.junit.Test;

public class ArrayUtilsTest {

    @Test
    public void pushTest() {
        Integer[] args = { 10, 99 };

        Integer[] obj = ArrayUtil.unshift(args, 20, 40);

        Arrays.asList(obj).stream().forEach(System.out::println);
    }
}