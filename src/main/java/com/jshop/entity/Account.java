package com.jshop.entity;


import com.jshop.model.CurrentAccount;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "account")
public class Account extends AbstractEntity<Integer> implements CurrentAccount {
    @Id
    @SequenceGenerator(name = "account_seq_generator", sequenceName = "account_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "account_seq_generator")
    private Integer id;
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
