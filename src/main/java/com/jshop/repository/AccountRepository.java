package com.jshop.repository;

import com.jshop.entity.Account;

import org.springframework.data.repository.CrudRepository;


public interface AccountRepository extends CrudRepository<Account, Integer> {

    Account findByEmail(String email);
}
