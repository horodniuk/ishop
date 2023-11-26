package com.jshop.repository.buider;

public class CountProductsSearchFormSQLBuilder extends AbstractSearchFormSQLBuilder {
    @Override
    protected String getSelectFields() {
        return "count(*)";
    }
}
