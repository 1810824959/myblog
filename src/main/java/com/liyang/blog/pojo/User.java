package com.liyang.blog.pojo;


import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Pattern;

public class User {
    private Integer id;

    @Length(max = 6,min = 3,message = "账号在3到6位之间")
    private String name;

//    @Length(max = 6,min = 3,message = "密码在3到6位之间")
    @Pattern(regexp = "\\d{3,6}",message = "密码只能是3~6位的数字哦")
    private String password;

    private String salt;

    private String headUrl;

    private String role;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password == null ? null : password.trim();
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt == null ? null : salt.trim();
    }

    public String getHeadUrl() {
        return headUrl;
    }

    public void setHeadUrl(String headUrl) {
        this.headUrl = headUrl == null ? null : headUrl.trim();
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role == null ? null : role.trim();
    }
}