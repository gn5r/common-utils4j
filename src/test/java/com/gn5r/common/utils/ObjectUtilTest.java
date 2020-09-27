package com.gn5r.common.utils;

import java.util.List;

import com.gn5r.common.resource.Difference;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.junit.Test;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

public class ObjectUtilTest {

    @lombok.Data
    @AllArgsConstructor
    @NoArgsConstructor
    private class User {
        private Integer id;
        private Integer age;
        private String name;
    }

    @lombok.Data
    @AllArgsConstructor
    @NoArgsConstructor
    private class Account {
        private Integer id;
        private String codeName;
        private String sex;
    }

    @Test
    public void diffTest() {
        User user = new User(1, 20, "gn5r");
        Account account = new Account(1, "shangyuan", "男");

        final boolean diff = ObjectUtil.check(user, account);
        System.out.println(diff);
    }

    @Test
    public void diffTest2() {
        User user = new User(1, 20, "gn5r");
        Account account = new Account(111, "shangyuan", "男");

        final List<Difference> diffList = ObjectUtil.diff(user, account);
        diffList.stream().forEach(
                diff -> System.out.println(ToStringBuilder.reflectionToString(diff, ToStringStyle.SHORT_PREFIX_STYLE)));
    }
}