package com.xy.fedex.facade.service.meta.dto;

import com.alibaba.druid.sql.ast.statement.SQLSelect;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @author tengfei
 */
@Data
public class PhysicalQueryPlan {
    private SQLSelect logicalSelect;
    private List<PhysicalSelect> physicalSelects;

    public void addPhysicalSelect(PhysicalSelect physicalSelect) {
        if(Objects.isNull(this.physicalSelects)) {
            this.physicalSelects = new ArrayList<>();
        }
        this.physicalSelects.add(physicalSelect);
    }

    public void addPhysicalSelect(List<PhysicalSelect> physicalSelects) {
        if(Objects.isNull(this.physicalSelects)) {
            this.physicalSelects = new ArrayList<>();
        }
        this.physicalSelects.addAll(physicalSelects);
    }


    public static class PhysicalSelect {

    }

    @AllArgsConstructor
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
