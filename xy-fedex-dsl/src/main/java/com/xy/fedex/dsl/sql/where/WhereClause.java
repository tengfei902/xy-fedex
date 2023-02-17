package com.xy.fedex.dsl.sql.where;

import java.util.LinkedList;

public class WhereClause implements WhereBase {

    private CONN conn;

    private LinkedList<WhereBase> subWheres;

    @Override
    public CONN getConn() {
        return this.conn;
    }

    @Override
    public String el() {
        return null;
    }
}
