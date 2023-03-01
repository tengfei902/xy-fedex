package com.xy.fedex.admin.api;

import com.alibaba.druid.sql.SQLUtils;
import com.alibaba.druid.sql.ast.SQLStatement;
import com.alibaba.druid.sql.dialect.mysql.ast.statement.MySqlCreateTableStatement;
import com.xy.fedex.admin.api.vo.request.TableRequest;
import com.xy.fedex.admin.api.vo.response.TableDetailVO;
import com.xy.fedex.admin.exception.DsnNotFoundException;
import com.xy.fedex.admin.service.datasource.AclFactory;
import com.xy.fedex.admin.service.dsn.DsnService;
import com.xy.fedex.admin.service.dsn.model.bo.DsnDTO;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Objects;

@DubboService(version = "${dubbo.server.version}")
public class FedexTableFacadeImpl implements FedexTableFacade {
    @Autowired
    private DsnService dsnService;

    @Override
    public TableDetailVO getTable(TableRequest tableRequest) {
        DsnDTO dsnDTO = dsnService.getDsn(tableRequest.getDsnId());
        if(Objects.isNull(dsnDTO)) {
            throw new DsnNotFoundException("dsn not found:"+tableRequest.getDsnId());
        }
        String ddl = AclFactory.getInstance().getAcl(dsnDTO).showCreateTable(tableRequest.getTableName());
        SQLStatement sqlStatement = SQLUtils.parseSingleStatement(ddl, dsnDTO.getDbType());
        MySqlCreateTableStatement createTableStatement = (MySqlCreateTableStatement) sqlStatement;
        return null;
    }
}
