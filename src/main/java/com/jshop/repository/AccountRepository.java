package com.jshop.repository;

import com.jshop.entity.Account;

public interface AccountRepository {

    Account findByEmail(String email);

    void create(Account account);
}
