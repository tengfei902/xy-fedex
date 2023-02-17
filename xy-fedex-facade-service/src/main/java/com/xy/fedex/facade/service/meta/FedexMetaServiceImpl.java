package com.xy.fedex.facade.service.meta;

import com.alibaba.druid.sql.ast.statement.*;
import com.xy.fedex.facade.service.meta.dto.QueryMatchedModelDTO;
import com.xy.fedex.facade.service.meta.match.ModelMatchService;
import com.xy.fedex.facade.service.meta.order.ModelOrderService;
import com.xy.fedex.facade.service.meta.dto.PhysicalQueryPlan;
import com.xy.fedex.facade.service.meta.plan.ModelPlanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FedexMetaServiceImpl implements FedexMetaService {
    @Autowired
    private ModelMatchService modelMatchService;
    @Autowired
    private ModelOrderService modelOrderService;
    @Autowired
    private ModelPlanService modelPlanService;

    /**
     * 物理模型查询计划
     * 1.逻辑查询语义改写（针对自定义指标、维度）
     * 2.寻址物理模型，获得指标与物理模型的映射关系
     * 3.指标映射物理模型排序
     * 4.构建指标的物理查询
     * 5.构建物理查询计划
     *
     * @param logicalSelect
     * @return
     */
    @Override
    public PhysicalQueryPlan getPhysicalSelects(SQLSelect logicalSelect) {
        QueryMatchedModelDTO queryMatchedModelDTO = modelMatchService.getMetaMatchedModels(logicalSelect);
        queryMatchedModelDTO = modelOrderService.sortMetaMatchedModels(queryMatchedModelDTO);
        return modelPlanService.getPhysicalQueryPlan(queryMatchedModelDTO);
    }

    @Override
    public void getMetaOrthogonality() {

    }
}
