package com.shbone.reggiedemo.dto;

/**
 * @author: SunHB
 * @createTime: 2023/12/12 上午12:43
 * @description:
 */
public class UserTokenDTO {
    private String userName;
    private String userId;
    private Integer age;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }
}
