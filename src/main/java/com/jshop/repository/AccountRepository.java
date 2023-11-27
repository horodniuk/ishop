package com.jshop.repository;

import com.jshop.entity.Account;
import com.jshop.framework.annotation.jdbc.Insert;
import com.jshop.framework.annotation.jdbc.JDBCRepository;
import com.jshop.framework.annotation.jdbc.Select;

@JDBCRepository
public interface AccountRepository {

    @Select("select * from account where email=?")
    Account findByEmail(String email);

    @Insert
    void create(Account account);
}
