package com.gn5r.common.utils;

import java.util.List;

import com.gn5r.common.resource.Difference;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.junit.Test;

import lombok.AllArgsConstructor;

public class ObjectUtilTest {

    @lombok.Data
    @AllArgsConstructor
    private class User {
        private Integer id;
        private Integer age;
        private String name;
    }

    @Test
    public void diffTest() {
        User user1 = new User(1, 20, "gn5r");
        User user2 = new User(1, 24, "gn5r");

        final List<Difference> diffList = ObjectUtil.diff(user1, user2);
        diffList.stream().forEach(
                diff -> System.out.println(ToStringBuilder.reflectionToString(diff, ToStringStyle.SHORT_PREFIX_STYLE)));
    }
}