package com.xy.fedex.facade.service.meta;

import com.xy.fedex.dsl.utility.SQLExprUtils;
import com.xy.fedex.facade.BaseTest;
import com.xy.fedex.facade.service.meta.match.ModelMatchService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class ModelMatchServiceTest extends BaseTest {
    @Autowired
    private ModelMatchService modelMatchService;

    @Test
    public void testGetMetaMatchedModels() {
        String sql = "select order_cnt,fee,actual_amount,amount,lock_amount,paid_amount,total_amount,dt,opr_type from 21 where dt between 20230101 and 20230301 group by dt,opr_type";
        modelMatchService.getMetricMatchedModels(SQLExprUtils.parse(sql));
    }
}
