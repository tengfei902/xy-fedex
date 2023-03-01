package com.xy.fedex.catalog.service.impl;

import com.alibaba.druid.sql.ast.statement.SQLExprTableSource;
import com.alibaba.druid.sql.ast.statement.SQLJoinTableSource;
import com.alibaba.druid.sql.ast.statement.SQLSubqueryTableSource;
import com.alibaba.druid.sql.ast.statement.SQLTableSource;
import com.alibaba.druid.sql.dialect.mysql.ast.statement.MySqlSelectQueryBlock;
import com.xy.fedex.admin.api.FedexTableFacade;
import com.xy.fedex.admin.api.vo.request.TableRequest;
import com.xy.fedex.admin.api.vo.response.TableDetailVO;
import com.xy.fedex.catalog.dto.TableAliasDTO;
import com.xy.fedex.catalog.exception.TableSourceTypeNotSupportException;
import com.xy.fedex.catalog.service.TableService;
import com.xy.fedex.dsl.utility.SQLExprUtils;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.RandomUtils;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class TableServiceImpl implements TableService {
    @DubboReference
    private FedexTableFacade fedexTableFacade;

    @Override
    public List<TableAliasDTO> getTables(Long dsnId, String tableSource) {
        String sql = "select * from "+tableSource;
        MySqlSelectQueryBlock selectQueryBlock = (MySqlSelectQueryBlock) SQLExprUtils.parse(sql).getQueryBlock();
        SQLTableSource sqlTableSource = selectQueryBlock.getFrom();
        return getTables(dsnId,sqlTableSource);
    }

    private List<TableAliasDTO> getTables(Long dsnId, SQLTableSource sqlTableSource) {
        if(sqlTableSource instanceof SQLExprTableSource) {
            SQLExprTableSource sqlExprTableSource = (SQLExprTableSource) sqlTableSource;
            String tableName = sqlExprTableSource.getTableName();
            TableDetailVO tableDetail = fedexTableFacade.getTable(TableRequest.builder().dsnId(dsnId).tableName(tableName).build());
            String alias = "t_"+ RandomUtils.nextInt();
            return Arrays.asList(new TableAliasDTO(alias,tableDetail));
        }
        if(sqlTableSource instanceof SQLJoinTableSource) {
            SQLJoinTableSource sqlJoinTableSource = (SQLJoinTableSource) sqlTableSource;
            SQLTableSource left = sqlJoinTableSource.getLeft();
            SQLTableSource right = sqlJoinTableSource.getLeft();
            return new ArrayList<>(CollectionUtils.union(getTables(dsnId,left),getTables(dsnId,right)));
        }
        if(sqlTableSource instanceof SQLSubqueryTableSource) {
            SQLSubqueryTableSource sqlSubqueryTableSource = (SQLSubqueryTableSource) sqlTableSource;
            SQLTableSource subQueryTableSource = sqlSubqueryTableSource.getSelect().getQueryBlock().getFrom();
            return getTables(dsnId,subQueryTableSource);
        }
        throw new TableSourceTypeNotSupportException("table source type not support:"+sqlTableSource.getClass().getTypeName());
    }

}
