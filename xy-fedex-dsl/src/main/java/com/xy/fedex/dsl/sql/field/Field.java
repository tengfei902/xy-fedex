package com.xy.fedex.dsl.sql.field;

public class Field implements Column {
    private String name;
    private String alias;

    @Override
    public String getFormula() {
        return this.name;
    }

    @Override
    public String getAlias() {
        return this.alias;
    }
}
