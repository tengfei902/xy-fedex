package com.xy.fedex.dsl.sql.from;

import java.io.Serializable;

public interface TableSource extends Serializable {

    String el();

    String getAlias();

    String getFormula();
}
