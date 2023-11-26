package com.jshop.repository.buider;

public class ListProductsSearchFormSQLBuilder  extends AbstractSearchFormSQLBuilder {
    @Override
    protected String getSelectFields() {
        return "p.*, c.name as category, pr.name as producer";
    }

    @Override
    protected String getLastSqlPart() {
        return " order by p.id limit ? offset ?";
    }
}
