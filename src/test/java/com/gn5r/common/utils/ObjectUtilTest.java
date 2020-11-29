package com.gn5r.common.utils;

import java.io.Serializable;

import org.junit.Test;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

public class ObjectUtilTest {

    @lombok.Data
    @AllArgsConstructor
    @NoArgsConstructor
    private class User implements Serializable {
        private static final long serialVersionUID = 1L;
        private Integer id;
        private Integer age;
        private String name;
    }

    @lombok.Data
    @AllArgsConstructor
    @NoArgsConstructor
    private class Account implements Serializable {
        private static final long serialVersionUID = 1L;
        private Integer id;
        private String codeName;
        private String sex;
    }

    @Test
    public void diffTest() {
        final Account account = new Account(1, "shangyuan", "男");
        System.out.println(ObjectUtil.toString(account));
    }
}