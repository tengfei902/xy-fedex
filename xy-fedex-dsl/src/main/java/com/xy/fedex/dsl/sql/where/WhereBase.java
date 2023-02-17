package com.xy.fedex.dsl.sql.where;

import java.io.Serializable;
import java.util.LinkedList;

public interface WhereBase extends Serializable {

    enum CONN {
        AND, OR, NOT
    }

    CONN getConn();

    String el();
}
