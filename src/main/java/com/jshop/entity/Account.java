package com.jshop.entity;

import com.jshop.framework.annotation.jdbc.Table;
import com.jshop.model.CurrentAccount;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Table(name="account", nextIdExpression="nextval('account_seq')")
public class Account extends AbstractEntity<Integer> implements CurrentAccount {
    private String name;
    private String email;


    public Account() {
    }

    public Account(String name, String email) {
        this.name = name;
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
