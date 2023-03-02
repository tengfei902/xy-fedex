package com.xy.fedex.catalog.common.definition.table;

import com.alibaba.druid.sql.ast.statement.SQLJoinTableSource;
import lombok.Data;
import org.apache.commons.lang3.tuple.Pair;

import java.util.List;

@Data
public class JoinTableRelation extends TableRelation {
    private TableRelation mainTable;
    private TableRelation joinTable;
    private SQLJoinTableSource.JoinType joinType;
    private List<Pair<String,String>> conditions;
}
