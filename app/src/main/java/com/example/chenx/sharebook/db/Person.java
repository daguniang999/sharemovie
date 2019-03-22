package com.example.chenx.sharebook.db;

import org.litepal.crud.LitePalSupport;

public class Person extends LitePalSupport {

    private String name;
    private String password;

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {

        return name;
    }

    public void setPassword(String password) {
        this.password = password;
    }


    public String getPassword() {
        return password;
    }
}
