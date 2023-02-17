package com.xy.fedex.dsl.sql.where;

public class WhereCondition implements WhereBase {

    private CONN conn;

    private String owner;

    private String column;

    private Object value;

    @Override
    public CONN getConn() {
        return this.conn;
    }

    @Override
    public String el() {
        return null;
    }
}
