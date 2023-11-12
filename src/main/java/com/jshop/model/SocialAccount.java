package com.jshop.model;

import lombok.Getter;

@Getter
public class SocialAccount {
    private final String name;
    private final String email;

    public SocialAccount(String name, String email) {
        this.name = name;
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }
}
