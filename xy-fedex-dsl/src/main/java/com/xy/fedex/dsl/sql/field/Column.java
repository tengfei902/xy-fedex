package com.xy.fedex.dsl.sql.field;

import java.io.Serializable;

public interface Column extends Serializable {

    String getFormula();

    String getAlias();
}
