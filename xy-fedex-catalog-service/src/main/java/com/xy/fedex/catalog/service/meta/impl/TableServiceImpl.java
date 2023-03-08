package com.xy.fedex.catalog.service.meta.impl;

import com.alibaba.druid.sql.ast.SQLExpr;
import com.alibaba.druid.sql.ast.statement.SQLExprTableSource;
import com.alibaba.druid.sql.ast.statement.SQLJoinTableSource;
import com.alibaba.druid.sql.ast.statement.SQLSubqueryTableSource;
import com.alibaba.druid.sql.ast.statement.SQLTableSource;
import com.alibaba.druid.sql.dialect.mysql.ast.statement.MySqlSelectQueryBlock;
import com.xy.fedex.admin.api.FedexTableFacade;
import com.xy.fedex.admin.api.vo.request.TableRequest;
import com.xy.fedex.admin.api.vo.response.TableDetailVO;
import com.xy.fedex.catalog.common.definition.column.TableField;
import com.xy.fedex.catalog.common.definition.table.JoinTableRelation;
import com.xy.fedex.catalog.common.definition.table.SqlExprTableRelation;
import com.xy.fedex.catalog.common.definition.table.SubQueryTableRelation;
import com.xy.fedex.catalog.common.definition.table.TableRelation;
import com.xy.fedex.catalog.exception.TableSourceTypeNotSupportException;
import com.xy.fedex.catalog.service.meta.TableService;
import com.xy.fedex.dsl.utility.SQLExprUtils;
import org.apache.commons.lang3.RandomUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TableServiceImpl implements TableService {
//    @DubboReference
    private FedexTableFacade fedexTableFacade;

    @Override
    public TableRelation getTables(Long dsnId, String tableSource) {
        String sql = "select * from "+tableSource;
        MySqlSelectQueryBlock selectQueryBlock = (MySqlSelectQueryBlock) SQLExprUtils.parse(sql).getQueryBlock();
        SQLTableSource sqlTableSource = selectQueryBlock.getFrom();
        return getTableRelation(sqlTableSource);
    }

    private TableRelation getTableRelation(SQLTableSource sqlTableSource) {
        if(sqlTableSource instanceof SQLExprTableSource) {
            SQLExprTableSource sqlExprTableSource = (SQLExprTableSource) sqlTableSource;
            String tableName = sqlExprTableSource.getTableName();
            String alias = "t_"+ RandomUtils.nextInt();
            SqlExprTableRelation tableRelation = new SqlExprTableRelation();
            tableRelation.setTableName(tableName);
            tableRelation.setAlias(alias);
            return tableRelation;
        }
        if(sqlTableSource instanceof SQLJoinTableSource) {
            SQLJoinTableSource sqlJoinTableSource = (SQLJoinTableSource) sqlTableSource;
            SQLTableSource left = sqlJoinTableSource.getLeft();
            SQLTableSource right = sqlJoinTableSource.getLeft();
            JoinTableRelation joinTableRelation = new JoinTableRelation();
            joinTableRelation.setMainTable(getTableRelation(left));
            joinTableRelation.setJoinTable(getTableRelation(right));
            joinTableRelation.setJoinType(sqlJoinTableSource.getJoinType());
            SQLExpr condition = sqlJoinTableSource.getCondition();
            joinTableRelation.setConditions(null);
            return joinTableRelation;
        }
        if(sqlTableSource instanceof SQLSubqueryTableSource) {
            SQLSubqueryTableSource sqlSubqueryTableSource = (SQLSubqueryTableSource) sqlTableSource;
            SQLTableSource subQueryTableSource = sqlSubqueryTableSource.getSelect().getQueryBlock().getFrom();
            SubQueryTableRelation subQueryTableRelation = new SubQueryTableRelation();
            subQueryTableRelation.getAlias();
            subQueryTableRelation.getSql();
        }
        throw new TableSourceTypeNotSupportException("table source type not support:"+sqlTableSource.getClass().getTypeName());
    }
    @Override
    public List<TableField> getTableFields(Long bizLineId, Long dsnId, TableRelation tableRelation, List<TableField> existedMetaFields) {
        return getTableFields(tableRelation,dsnId);
    }

    private List<TableField> getTableFields(TableRelation tableRelation,Long dsnId) {
        if(tableRelation instanceof SqlExprTableRelation) {
            SqlExprTableRelation sqlExprTableRelation = (SqlExprTableRelation) tableRelation;
            String tableName = sqlExprTableRelation.getTableName();
            TableDetailVO tableDetailVO = fedexTableFacade.getTable(TableRequest.builder().dsnId(dsnId).tableName(tableName).build());
            List<TableField> tableFields = tableDetailVO.getColumns().stream().map(column -> {
                TableField tableField = new TableField();
                tableField.setTableName(tableDetailVO.getTableName());
                tableField.setTableAlias(tableRelation.getAlias());
                tableField.setColumnName(column.getColumnName());
                tableField.setColumnType(column.getColumnType());
                tableField.setComment(column.getComment());
                return tableField;
            }).collect(Collectors.toList());
            return tableFields;
        }
        if(tableRelation instanceof JoinTableRelation) {
            JoinTableRelation joinTableRelation = (JoinTableRelation) tableRelation;
            TableRelation mainTable = joinTableRelation.getMainTable();
            TableRelation joinTable = joinTableRelation.getJoinTable();

            List<TableField> mainTableFields = getTableFields(mainTable,dsnId);
            List<TableField> joinTableFields = getTableFields(joinTable,dsnId);

            List<TableField> tableFields = new ArrayList<>();
            tableFields.addAll(mainTableFields);
            tableFields.addAll(joinTableFields);
            return tableFields;
        }
        if(tableRelation instanceof SubQueryTableRelation) {
            SubQueryTableRelation subQueryTableRelation = (SubQueryTableRelation) tableRelation;
            subQueryTableRelation.getSql();
            //todo 待实现
        }
        throw new TableSourceTypeNotSupportException("table source type not support:"+tableRelation.getClass());
    }
}
