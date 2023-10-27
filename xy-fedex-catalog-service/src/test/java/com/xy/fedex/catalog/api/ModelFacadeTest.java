package com.xy.fedex.catalog.api;

import com.google.gson.Gson;
import com.xy.fedex.catalog.BaseTest;
import com.xy.fedex.catalog.common.definition.ModelDefinition;
import com.xy.fedex.catalog.common.definition.field.Metric;
import com.xy.fedex.catalog.dao.ModelDao;
import com.xy.fedex.catalog.dao.ModelParamsDao;
import com.xy.fedex.catalog.dto.ModelDTO;
import com.xy.fedex.catalog.po.ModelPO;
import com.xy.fedex.catalog.po.ModelParamsPO;
import com.xy.fedex.catalog.service.MetricService;
import com.xy.fedex.catalog.service.ModelService;
import com.xy.fedex.catalog.utils.SqlReader;
import com.xy.fedex.def.Response;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class ModelFacadeTest extends BaseTest {
  @Autowired
  private ModelFacade modelFacade;
  @Autowired
  private MetricService metricService;
  @Autowired
  private ModelService modelService;
  @Autowired
  private ModelDao modelDao;
  @Autowired
  private ModelParamsDao modelParamsDao;

  @Test
  public void testSaveModel() {
    List<String> metricNames = Arrays.asList("expose_pv","expose_uv","click_pv","click_uv","view_pv","view_uv","buy_pv","buy_uv","visit_buy_rate");
    List<Metric> metrics = metricNames.stream().map(s -> {
      Metric metric = new Metric();
      metric.setMetricCode(s);
      return metric;
    }).collect(Collectors.toList());
    metricService.saveMetrics(metrics);
    String sql = SqlReader.read("ddl/model_flow_shop_sku_dt.sql");
    modelFacade.saveModel(sql);

    ModelPO modelPO = modelDao.selectByModelCode("10000@test_project.flow_shop_sku_dt");
    Assert.assertNotNull(modelPO);
    Assert.assertNotNull(modelPO.getModelComment());
    Assert.assertNotNull(modelPO.getModelName());
    Assert.assertNotNull(modelPO.getCreator());
    Assert.assertNotNull(modelPO.getTableSource());
    Assert.assertNotNull(modelPO.getDsnCode());
    Assert.assertEquals(modelPO.getStatus().intValue(),1);
    System.out.println(new Gson().toJson(modelPO));

    List<ModelParamsPO> modelParams = modelParamsDao.selectByModelId(modelPO.getId());
    System.out.println(new Gson().toJson(modelParams));

    ModelDTO modelDTO = modelService.getModelByCode("flow_shop_sku_dt");
    System.out.println(modelDTO.toString());

    Assert.assertEquals(modelDTO.getModelCode(),"flow_shop_sku_dt");
    Assert.assertEquals(modelDTO.getModelName(),modelPO.getModelName());
    Assert.assertEquals(modelDTO.getModelComment(),modelPO.getModelComment());
    Assert.assertEquals(modelDTO.getDsnCode(),modelPO.getDsnCode());
    Assert.assertEquals(modelDTO.getCreator(),modelPO.getCreator());
    Assert.assertNotNull(modelDTO.getTableSource());

    Response<ModelDefinition> response = modelFacade.getModel("flow_shop_sku_dt");
  }
}
