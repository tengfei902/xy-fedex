package com.xy.fedex.dsl.sql.from.impl;

import com.xy.fedex.dsl.sql.from.TableSource;
import org.apache.commons.lang3.StringUtils;

public class LiteralTableSource implements TableSource {
    private String name;
    private String alias;
    /**
     * table as alias
     * @return
     */
    @Override
    public String el() {
        return StringUtils.isEmpty(this.alias)?name:String.format("%s as %s",name,alias);
    }

    @Override
    public String getAlias() {
        return this.alias;
    }

    @Override
    public String getFormula() {
        return null;
    }
}
