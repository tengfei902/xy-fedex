package com.xy.fedex.catalog.api;

import com.google.gson.Gson;
import com.xy.fedex.catalog.BaseTest;
import com.xy.fedex.catalog.api.dto.ModelRequest;
import com.xy.fedex.catalog.api.dto.request.PrepareModelRequest;
import com.xy.fedex.catalog.api.dto.request.SaveAppRequest;
import com.xy.fedex.catalog.api.dto.request.save.SaveRequests;
import com.xy.fedex.catalog.api.dto.request.save.field.metric.SaveDimRequest;
import com.xy.fedex.catalog.api.dto.request.save.field.metric.SaveMetricRequest;
import com.xy.fedex.catalog.api.dto.response.PrepareModelResponse;
import com.xy.fedex.catalog.common.definition.AppDefinition;
import com.xy.fedex.catalog.common.definition.ModelDefinition;
import com.xy.fedex.catalog.utils.SqlReader;
import com.xy.fedex.def.Response;
import lombok.Data;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CatalogFacadeTest extends BaseTest {
    @Autowired
    private CatalogFacade catalogFacade;

    @Test
    public void testSaveModel() {
        String modelDefinition = "create table biz_line_0.trade_shop_dt \n" +
                "comment '门店交易数据'\n" +
                "tblproperties(" +
                "'dsn'='cs_mysql'" +
                ")" +
                "as \n" +
                "select \n" +
                "\tt1.dt as dt,\n" +
                "    t1.shop_id as shop_id,\n" +
                "    t2.shop_name as shop_name,\n" +
                "    t2.shop_full_name as shop_full_name,\n" +
                "    t2.tenant_id as tenant_id,\n" +
                "    t2.tenant_name as tenant_name,\n" +
                "    t2.brand_id as brand_id,\n" +
                "\tt2.brand_name as brand_name,\n" +
                "    t2.ditrict_code as ditrict_code,\n" +
                "    t2.business_district_id as business_district_id,\n" +
                "    t2.latitude as latitude,\n" +
                "    t2.longitude as longitude,\n" +
                "    t3.district_name as district_name,\n" +
                "    t3.city_code as city_code,\n" +
                "    t3.city_name as city_name,\n" +
                "    t3.province_code as province_code,\n" +
                "    t3.province_name as province_name,\n" +
                "    t3.country_code as country_code,\n" +
                "    t3.country_name as country_name,\n" +
                "    t4.day_of_week as day_of_week,\n" +
                "    t4.day_of_month as day_of_month,\n" +
                "    t4.day_of_year as day_of_year,\n" +
                "    t4.week_of_month as week_of_month,\n" +
                "    t4.week_of_year as week_of_year,\n" +
                "    t4.month_of_year as month_of_year,\n" +
                "    t4.year as `year`,\n" +
                "    t4.holiday as holiday,\n" +
                "    sum(t1.sale_amt) as sale_amt,\n" +
                "    sum(t1.sale_cnt) as sale_cnt,\n" +
                "    sum(t1.refund_amt) as refund_amt,\n" +
                "    sum(t1.refund_cnt) as refund_cnt,\n" +
                "    sum(t1.fee) as fee\n" +
                "from dws_trade_shop_d_ss t1 \n" +
                " left join dim_shop_ss t2 on t1.shop_id = t2.shop_id and t1.dt = t2.dt\n" +
                " left join dim_city_ss t3 on t2.district_code = t3.district_code and t2.dt = t3.dt\n" +
                " left join dim_dt_ss t4 on t1.dt = t4.dt \n" +
                " group by \n" +
                " t1.dt,\n" +
                "    t1.shop_id,\n" +
                "    t2.shop_name,\n" +
                "    t2.shop_full_name,\n" +
                "    t2.tenant_id,\n" +
                "    t2.tenant_name,\n" +
                "    t2.brand_id,\n" +
                "\tt2.brand_name,\n" +
                "    t2.ditrict_code,\n" +
                "    t2.business_district_id,\n" +
                "    t2.latitude,\n" +
                "    t2.longitude,\n" +
                "    t3.district_name,\n" +
                "    t3.city_code,\n" +
                "    t3.city_name,\n" +
                "    t3.province_code,\n" +
                "    t3.province_name,\n" +
                "    t3.country_code,\n" +
                "    t3.country_name,\n" +
                "    t4.day_of_week,\n" +
                "    t4.day_of_month,\n" +
                "    t4.day_of_year,\n" +
                "    t4.week_of_month,\n" +
                "    t4.week_of_year,\n" +
                "    t4.month_of_year,\n" +
                "    t4.year ,\n" +
                "    t4.holiday";

        catalogFacade.saveModel(modelDefinition);
    }

    @Test
    public void saveApp() {

    }

    @Rollback(value = false)
    @Test
    public void testSaveMetric() {
        Long bizLineId = 0L;
        String metrics = "expose_pv,expose_uv,click_pv,click_uv,view_pv,view_uv,visit_buy_rate";
        for(String metric:metrics.split(",")) {
            SaveMetricRequest metricRequest = SaveRequests.newSaveMetricRequest(bizLineId,metric).build();
            catalogFacade.saveMetric(metricRequest);
        }
    }

    @Rollback(value = false)
    @Test
    public void testSaveDim() {
        Long bizLineId = 0L;
        List<String> dimCodes = Arrays.asList("dt,shop_id,shop_name,shop_full_name,tenant_id,tenant_name,brand_id,brand_name,ditrict_code,business_district_id,latitude,longitude,district_name,city_code,city_name,province_code,province_name,country_code,country_name,day_of_week,day_of_month,day_of_year,week_of_month,week_of_year,month_of_year,year,holiday,sku_id,sku_name,spu_id,spu_name,catagory1_id,catagory1_name,catagory2_id,catagory2_name,catagory3_id,catagory3_name,catagory4_id,catagory4_name,catagory5_id,catagory5_name,weight,volume,specifications,manufacturer".split(","));
        for(String dimCode:dimCodes) {
            SaveDimRequest saveDimRequest = SaveRequests.newSaveDimRequest(bizLineId,dimCode).build();
            catalogFacade.saveDim(saveDimRequest);
        }
    }

    @Test
    public void testCreateApp() {
        String sql = SqlReader.read("ddl/app_ecs.sql");
        Response<Long> response = catalogFacade.execute(sql);
        Response<AppDefinition> appResponse = catalogFacade.getApp(response.getData());
        System.out.println(new Gson().toJson(appResponse.getData()));
    }

    @Test
    public void testCreateModel() {
        String sql = SqlReader.read("ddl/model_trade_shop_sku_dt.sql");
        Response<Long> response = catalogFacade.execute(sql);
        Response<ModelDefinition> model = catalogFacade.getModel(response.getData());
        System.out.println(new Gson().toJson(model.getData()));
    }
}
