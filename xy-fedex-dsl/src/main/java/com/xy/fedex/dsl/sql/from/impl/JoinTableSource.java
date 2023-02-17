package com.xy.fedex.dsl.sql.from.impl;

import com.xy.fedex.dsl.sql.from.TableSource;

public class JoinTableSource implements TableSource {

    private TableSource left;
    private TableSource right;


    @Override
    public String el() {
        return null;
    }

    @Override
    public String getAlias() {
        return null;
    }

    @Override
    public String getFormula() {
        return null;
    }
}
