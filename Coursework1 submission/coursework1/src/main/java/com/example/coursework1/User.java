package com.example.coursework1;

import java.io.Serializable;

public class User implements Serializable {
    private String name,password,role;

    public User(String name, String password,String role) {
        this.name = name;
        this.password = password;
        this.role = role;
    }

    public String getPassword() {
        return password;
    }

    public String getRole() {
        return role;
    }

    public String getName() {
        return name;
    }

}
