package com.jshop.repository.impl;

import com.jshop.entity.Account;
import com.jshop.framework.factory.JDBCConnectionUtils;
import com.jshop.framework.handler.DefaultUniqueResultSetHandler;
import com.jshop.framework.handler.ResultSetHandler;
import com.jshop.jdbc.JDBCUtils;
import com.jshop.repository.AccountRepository;

public class AccountRepositoryImpl implements AccountRepository {
    private final ResultSetHandler<Account> accountResultSetHandler = new DefaultUniqueResultSetHandler<>(Account.class);

    @Override
    public Account findByEmail(String email) {
        return JDBCUtils.select(JDBCConnectionUtils.getCurrentConnection(), "select * from account where email=?", accountResultSetHandler, email);
    }

    @Override
    public void create(Account account) {
        Account createdAccount = JDBCUtils.insert(JDBCConnectionUtils.getCurrentConnection(),
                "insert into account values (nextval('account_seq'),?,?,?)",
                accountResultSetHandler, account.getName(), account.getEmail());
        account.setId(createdAccount.getId());
    }
}
