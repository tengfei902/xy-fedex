package com.xy.fedex.catalog.service;

import com.google.gson.Gson;
import com.xy.fedex.catalog.BaseTest;
import com.xy.fedex.catalog.constants.Constants;
import com.xy.fedex.catalog.dao.*;
import com.xy.fedex.catalog.dto.ModelDTO;
import com.xy.fedex.catalog.po.*;
import com.xy.fedex.catalog.utils.CatalogUtils;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ModelServiceTest extends BaseTest {
    @Autowired
    private ModelDao modelDao;
    @Autowired
    private MetricModelDao metricModelDao;
    @Autowired
    private DimModelDao dimModelDao;
    @Autowired
    private MetricDao metricDao;
    @Autowired
    private DimDao dimDao;
    @Autowired
    private ModelParamsDao modelParamsDao;
    @Autowired
    private ModelService modelService;

    @Test
    public void testSaveModel() {
        //新增model
        ModelDTO modelDTO = new ModelDTO();
        modelDTO.setModelCode("test_model");
        modelDTO.setModelName("测试模型");
        modelDTO.setModelComment("测试模型验证");
        modelDTO.setDsnCode("test_dsn_code");

        String ddl = "create table model_test1 comment 'model test comment' as select sum(sale_cnt) as sale_cnt,sum(sale_amt) as sale_amt,sum(refund_amt) as refund_amt,sum(refund_amt)/sum(sale_amt) as refund_rate,dt as dt,category_id from t where dt > 20230830 and brand_id = 1000";

//        modelDTO.setTableSource(ddl);
        Map<String,String> modelParams = new HashMap<>();
        modelDTO.setModelParams(modelParams);
        modelService.saveModel(modelDTO);

        ModelPO modelPO = modelDao.selectByModelCode(CatalogUtils.getObjectFullName("test_model"));
        System.out.println(new Gson().toJson(modelPO));

        List<ModelParamsPO> modelParamsPOS = modelParamsDao.selectByModelId(modelPO.getId());
        Assert.assertEquals(modelPO.getModelCode(),CatalogUtils.getObjectFullName(modelDTO.getModelCode()));
        Assert.assertEquals(modelPO.getModelName(),modelDTO.getModelName());
        Assert.assertEquals(modelPO.getModelComment(),modelDTO.getModelComment());
        Assert.assertEquals(modelPO.getTableSource(),modelDTO.getTableSource());
        Assert.assertEquals(modelPO.getDsnCode(),modelDTO.getDsnCode());

        System.out.println(new Gson().toJson(modelParamsPOS));

        ModelDTO model = modelService.getModelByCode("test_model");
        System.out.println(new Gson().toJson(model));
        //更新model
        modelDTO.setModelName("test change model name");
        modelDTO.setModelComment("test change model name ssss");
        modelService.saveModel(modelDTO);

        ModelDTO model2 = modelService.getModelByCode("test_model");
        Assert.assertEquals(modelDTO.getModelName(),model2.getModelName());
        Assert.assertEquals(modelDTO.getModelComment(),model2.getModelComment());
    }
}
