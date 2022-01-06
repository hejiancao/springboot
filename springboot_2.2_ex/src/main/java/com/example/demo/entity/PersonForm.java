package com.example.demo.entity;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class PersonForm {

    @NotNull(message = "name不能为空")
    @Size(min=2, max=30)
    private String name;

    @NotNull(message = "age不能为空")
    @Min(18)
    private Integer age;

    @Valid
    @NotNull(message = "user不能为空")
    private User user;

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    private static class User {

        private int id;

        @NotNull(message = "userName不能为空")
        @Valid
        private String userName;


        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }
    }
}


