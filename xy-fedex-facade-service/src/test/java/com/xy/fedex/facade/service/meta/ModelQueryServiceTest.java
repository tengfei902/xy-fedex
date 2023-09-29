package com.xy.fedex.facade.service.meta;

import com.alibaba.druid.sql.ast.SQLExpr;
import com.alibaba.druid.sql.ast.statement.SQLSelect;
import com.google.gson.Gson;
import com.xy.fedex.dsl.utility.SQLExprUtils;
import com.xy.fedex.facade.BaseTest;
import com.xy.fedex.facade.service.meta.dto.PhysicalQueryPlan;
import com.xy.fedex.facade.service.meta.dto.QueryMatchedModelDTO;
import com.xy.fedex.facade.service.meta.match.ModelMatchService;
import com.xy.fedex.facade.service.meta.query.ModelQueryService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class ModelQueryServiceTest extends BaseTest {
    @Autowired
    private ModelQueryService modelQueryService;
    @Autowired
    private ModelMatchService modelMatchService;

    @Test
    public void testGetPhysicalQuery() {
        String select = "select click_uv as click_uv,view_pv as view_pv,visit_buy_rate as visit_buy_rate,sale_amt as sale_amt,refund_amt as refund_amt,dt,shop_name,brand_name from 33 where dt between 20230101 and 20230401 and brand_id = 10000 group by dt,shop_name,brand_name";
        SQLSelect sqlSelect = SQLExprUtils.parse(select);
        QueryMatchedModelDTO queryMatchedModelDTO = modelMatchService.getMetricMatchedModels(sqlSelect);
        PhysicalQueryPlan physicalQueryPlan = modelQueryService.getPhysicalQuery(queryMatchedModelDTO);
        System.out.println(new Gson().toJson(physicalQueryPlan));
    }

    /**
     * 基础指标查询
     */
    @Test
    public void testGetPhysicalQueryForPrimary() {
        String select = "select click_uv as click_uv,view_pv as view_pv,dt,shop_name,brand_name from 33 where dt between 20230101 and 20230401 and brand_id = 10000 group by dt,shop_name,brand_name";
        SQLSelect sqlSelect = SQLExprUtils.parse(select);
        QueryMatchedModelDTO queryMatchedModelDTO = modelMatchService.getMetricMatchedModels(sqlSelect);
        PhysicalQueryPlan physicalQueryPlan = modelQueryService.getPhysicalQuery(queryMatchedModelDTO);
        System.out.println(new Gson().toJson(physicalQueryPlan));
    }
}
