package com.jshop.entity;

import com.jshop.model.CurrentAccount;

import java.util.StringJoiner;

public class Account extends AbstractEntity<Integer> implements CurrentAccount {
    private String name;
    private String email;

    public Account() {
    }

    public Account(String name, String email) {
        this.name = name;
        this.email = email;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return String.format("Account [id=%s, name=%s, email=%s]", getId(), name, email);
    }

    @Override
    public String getDescription() {
        return name + "("+email+")";
    }
}
