package com.xy.fedex.facade.service.meta.dto;

import com.alibaba.druid.sql.ast.statement.SQLSelect;
import lombok.Data;

import java.util.List;

/**
 * @author tengfei
 */
public class PhysicalQueryPlan {
    private SQLSelect logicalSelect;
    private List<PhysicalSelect> physicalQueries;


    public static class PhysicalSelect {

    }

    @Data
    public static class PrimaryPhysicalSelect extends PhysicalSelect {
        private SQLSelect select;
    }

    @Data
    public static class DerivePhysicalSelect extends PhysicalSelect {
        private SQLSelect select;
        private List<SQLSelect> relateSelects;
    }
}
