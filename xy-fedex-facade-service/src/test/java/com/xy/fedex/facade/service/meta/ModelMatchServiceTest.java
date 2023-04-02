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
        String sql = "select fee,actual_amount,amount,fee_rate,order_cnt,dt,channel_provider_code from 21 where dt between 20230101 and 20230301 group by dt,channel_provider_code";
        modelMatchService.getMetricMatchedModels(SQLExprUtils.parse(sql));
    }
}
