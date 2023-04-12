package com.xy.fedex.facade.service.meta;

import com.alibaba.druid.sql.ast.SQLExpr;
import com.alibaba.druid.sql.ast.expr.SQLAggregateExpr;
import com.alibaba.druid.sql.ast.expr.SQLIdentifierExpr;
import com.alibaba.druid.sql.ast.expr.SQLPropertyExpr;
import com.alibaba.druid.sql.ast.statement.SQLSelectItem;
import com.alibaba.druid.sql.dialect.mysql.ast.statement.MySqlSelectQueryBlock;
import com.xy.fedex.catalog.api.CatalogAppFacade;
import com.xy.fedex.catalog.api.CatalogMetaFacade;
import com.xy.fedex.catalog.api.CatalogModelFacade;
import com.xy.fedex.catalog.api.dto.request.GetDimModelRequest;
import com.xy.fedex.catalog.api.dto.request.GetMetricModelRequest;
import com.xy.fedex.catalog.api.dto.request.GetModelRequest;
import com.xy.fedex.catalog.common.definition.AppDefinition;
import com.xy.fedex.catalog.common.definition.ModelDefinition;
import com.xy.fedex.catalog.common.definition.field.AdvanceCalculate;
import com.xy.fedex.catalog.common.definition.field.SecondaryCalculate;
import com.xy.fedex.catalog.common.definition.field.impl.DeriveMetricModel;
import com.xy.fedex.catalog.common.definition.field.impl.DimModel;
import com.xy.fedex.catalog.common.definition.field.impl.MetricModel;
import com.xy.fedex.catalog.common.definition.field.impl.PrimaryMetricModel;
import com.xy.fedex.catalog.common.enums.DimType;
import com.xy.fedex.catalog.common.enums.MetricType;
import com.xy.fedex.def.Response;
import com.xy.fedex.dsl.utility.SQLExprUtils;
import com.xy.fedex.facade.BaseTest;
import com.xy.fedex.facade.clients.DubboClientHolder;
import com.xy.fedex.facade.service.cs.AppHolder;
import com.xy.fedex.facade.service.cs.ModelHolder;
import com.xy.fedex.facade.service.meta.dto.QueryMatchedModelDTO;
import com.xy.fedex.facade.service.meta.match.ModelMatchService;
import org.apache.commons.lang3.RandomUtils;
import org.apache.commons.lang3.StringUtils;
import org.junit.Assert;
import org.junit.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.util.ReflectionTestUtils;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.*;
import java.util.stream.Collectors;

public class ModelMatchServiceTest extends BaseTest {
    @Autowired
    private ModelMatchService modelMatchService;
    @Mock
    private CatalogAppFacade catalogAppFacade;
    @Mock
    private CatalogMetaFacade catalogMetaFacade;
    @Mock
    private CatalogModelFacade catalogModelFacade;
    @Autowired
    private DubboClientHolder dubboClientHolder;

    @BeforeEach
    public void init() throws Exception {
        MockitoAnnotations.openMocks(this);
        ReflectionTestUtils.setField(dubboClientHolder,"catalogAppFacade",catalogAppFacade);

        Field field = AppHolder.class.getDeclaredField("catalogAppFacade");
        field.setAccessible(true);
        Field modifiersField = Field.class.getDeclaredField("modifiers");
        modifiersField.setAccessible(true);
        modifiersField.setInt(field, field.getModifiers() & ~Modifier.FINAL);
        field.set(null,catalogAppFacade);

        Field field2 = AppHolder.class.getDeclaredField("catalogMetaFacade");
        field2.setAccessible(true);
        Field modifiersField2 = Field.class.getDeclaredField("modifiers");
        modifiersField2.setAccessible(true);
        modifiersField2.setInt(field2, field2.getModifiers() & ~Modifier.FINAL);
        field2.set(null,catalogMetaFacade);

        Field field3 = ModelHolder.class.getDeclaredField("catalogModelFacade");
        field3.setAccessible(true);
        Field modifiersField3 = Field.class.getDeclaredField("modifiers");
        modifiersField3.setAccessible(true);
        modifiersField3.setInt(field3, field3.getModifiers() & ~Modifier.FINAL);
        field3.set(null,catalogModelFacade);

    }

    @Test
    public void testGetMetaMatchedModels() {
        String sql = "select fee,actual_amount,amount,fee_rate,order_cnt,dt,channel_provider_code,out_trade_no from 21 where dt between 20230101 and 20230301 group by dt,channel_provider_code,out_trade_no";
        modelMatchService.getMetricMatchedModels((MySqlSelectQueryBlock) SQLExprUtils.parse(sql).getQueryBlock());
    }

    /**
     * 验证基础指标
     */
    @Test
    public void testGetMatchedModelsForPrimaryMetric() {
        String sql1 = "select sum(t1.sale_amount) as sale_amount,count(distinct t1.poi_id) as poi_cnt,t1.dt as dt,t1.poi_id as poi_id,t2.city_id as city_id,t2.province_id as province_id,t1.sell_type as sell_type,t2.poi_name as poi_name from t1_1 t1 left join t2_1 t2 on t1.poi_id = t2.poi_id group by t1.dt,t2.city_id,t2.province_id,t1.sell_type,t1.poi_id,t2.poi_name";
        String sql2 = "select sum(sale_amount) as sale_amount,dt,poi_id,channel_provide_code,channel_provide_name from t1_2 where poi_type = 1 and channel_provider_code = 1 group by dt,channel_provide_code,channel_provide_name,poi_id";
        //String sql3 = "select sum(market_amount) as market_amount ,dt,poi_id from t2_1 group by dt,poi_id";
        initAppDefinition(Arrays.asList(sql1,sql2));

        String logicalSelect = "select sale_amount,poi_cnt ,dt,poi_name from 12222 where sell_type = 10 group by dt,poi_name";
        QueryMatchedModelDTO queryMatchedModelDTO = modelMatchService.getMetricMatchedModels((MySqlSelectQueryBlock) SQLExprUtils.parse(logicalSelect).getQueryBlock());

        Assert.assertNotNull(queryMatchedModelDTO);
        List<QueryMatchedModelDTO.MetricMatchedModelDTO> metricMatchedModels = queryMatchedModelDTO.getMetricMatchedModelList();
        for(QueryMatchedModelDTO.MetricMatchedModelDTO metricMatchedModel:metricMatchedModels) {
            System.out.println("metric name:"+metricMatchedModel.getMetricName());
            for(QueryMatchedModelDTO.MetricModel metricModel: metricMatchedModel.getMetricModels()) {
                System.out.println("metric model id:"+ metricModel.getMetricModelId());
                System.out.println("metric select:"+metricModel.getMetricSelect().toString());
            }
        }
    }

    /**
     * 包含二次计算
     */
    @Test
    public void testGetMatchedModelsForSecondaryCalculate() {
        String sql = "select count(distinct t1.uuid) as uv,t1.page_id as page_id,t1.poi_id as poi_id,t1.dt as dt from t t1 group by t1.page_id,t1.poi_id,t1.dt";
        initAppDefinition(Arrays.asList(sql));

        String select = "select uv as pageuv,page_id as page from 12222 where dt between 20230101 and 20230401 group by page_id";
        QueryMatchedModelDTO queryMatchedModelDTO = modelMatchService.getMetricMatchedModels(SQLExprUtils.parse(select));
        for(QueryMatchedModelDTO.MetricMatchedModelDTO metricMatchedModel : queryMatchedModelDTO.getMetricMatchedModelList()) {
            for(QueryMatchedModelDTO.MetricModel metricModel: metricMatchedModel.getMetricModels()) {
                System.out.println(metricModel.getMetricSelect().toString());
            }
        }

        String select2 = "select uv as pageuv,page_id as page,dt from 12222 where dt between 20230101 and 20230401 group by page_id,dt";
        queryMatchedModelDTO = modelMatchService.getMetricMatchedModels((MySqlSelectQueryBlock) SQLExprUtils.parse(select2).getQueryBlock());
        for(QueryMatchedModelDTO.MetricMatchedModelDTO metricMatchedModel : queryMatchedModelDTO.getMetricMatchedModelList()) {
            for(QueryMatchedModelDTO.MetricModel metricModel: metricMatchedModel.getMetricModels()) {
                System.out.println(metricModel.getMetricSelect().toString());
            }
        }
    }

    /**
     * 衍生指标
     */
    @Test
    public void testGetMatchedModelsForDeriveMetric() {
        String sql1 = "select sum(sale_amount) as sale_amount,dt,poi_id,city_id,province_id,sell_type from t1_1 group by dt,city_id,province_id,sell_type,poi_id";
        //String sql2 = "select sum(sale_amount) as sale_amount,dt,poi_id,channel_provide_code,channel_provide_name from t1_2 where poi_type = 1 and channel_provider_code = 1 group by dt,channel_provide_code,channel_provide_name,poi_id";
        String sql3 = "select sum(market_amount) as market_amount ,dt,poi_id from t2_1 group by dt,poi_id";
        AppDefinition app = initAppDefinition(Arrays.asList(sql1,sql3));

        AppDefinition.Metric roi = new AppDefinition.Metric();
        roi.setMetricId(RandomUtils.nextLong());
        roi.setMetricCode("roi");
        roi.setMetricType(MetricType.DERIVE);

        app.getMetrics().add(roi);

        AppDefinition.Metric saleAmountMetric = null;
        AppDefinition.Metric marketAmountMetric = null;

        for(AppDefinition.Metric metric:app.getMetrics()) {
            if(StringUtils.equalsIgnoreCase(metric.getMetricCode(),"sale_amount")) {
                saleAmountMetric = metric;
            }
            if(StringUtils.equalsIgnoreCase(metric.getMetricCode(),"market_amount")) {
                marketAmountMetric = metric;
            }
        }

        MetricModel saleAmountMetricModel = catalogMetaFacade.getMetricModels(GetMetricModelRequest.builder().appId(app.getAppId()).metricId(saleAmountMetric.getMetricId()).build()).getData().get(0);
        MetricModel marketAmountMetricModel = catalogMetaFacade.getMetricModels(GetMetricModelRequest.builder().appId(app.getAppId()).metricId(marketAmountMetric.getMetricId()).build()).getData().get(0);

        DeriveMetricModel roiMetricModel = new DeriveMetricModel();
        roiMetricModel.setCode("roi");
        roiMetricModel.setMetricModelId(RandomUtils.nextLong());
        roiMetricModel.setMetricId(roi.getMetricId());
        roiMetricModel.setFormula("${0}/${1}");
        AdvanceCalculate advanceCalculate = new AdvanceCalculate();
        roiMetricModel.setAdvanceCalculate(advanceCalculate);
        roiMetricModel.setRelateMetricModels(Arrays.asList(saleAmountMetricModel,marketAmountMetricModel));

        Mockito.when(catalogMetaFacade.getMetricModels(GetMetricModelRequest.builder().appId(app.getAppId()).metricId(roi.getMetricId()).build())).thenReturn(Response.success(Arrays.asList(roiMetricModel)));

        String logicalSelect = "select roi ,dt from 12222 where dt between 20230101 and 20230301 group by dt";
        QueryMatchedModelDTO queryMatchedModelDTO = modelMatchService.getMetricMatchedModels((MySqlSelectQueryBlock) SQLExprUtils.parse(logicalSelect).getQueryBlock());
        for(QueryMatchedModelDTO.MetricMatchedModelDTO metricMatchedModelDTO:queryMatchedModelDTO.getMetricMatchedModelList()) {
            for(QueryMatchedModelDTO.MetricModel metricModel:metricMatchedModelDTO.getMetricModels()) {
                System.out.println(metricModel.getMetricSelect().toString());
            }
        }
    }

    /**
     * 衍生指标:辅助计算指标
     */
    @Test
    public void testGetMatchedModelsForDeriveMetricOfAssist() {
        String sql = "select sum(sale_amount) as sale_amount,dt,poi_id,city_id,province_id,sell_type from t1_1 group by dt,city_id,province_id,sell_type,poi_id";
        AppDefinition app = initAppDefinition(Arrays.asList(sql));

        AppDefinition.Metric roi = new AppDefinition.Metric();
        roi.setMetricId(RandomUtils.nextLong());
        roi.setMetricCode("sale_amount_rate");
        roi.setMetricType(MetricType.DERIVE);

        app.getMetrics().add(roi);

        AppDefinition.Metric saleAmountMetric = null;

        for(AppDefinition.Metric metric:app.getMetrics()) {
            if(StringUtils.equalsIgnoreCase(metric.getMetricCode(),"sale_amount")) {
                saleAmountMetric = metric;
            }
        }

        PrimaryMetricModel saleAmountMetricModel = (PrimaryMetricModel) catalogMetaFacade.getMetricModels(GetMetricModelRequest.builder().appId(app.getAppId()).metricId(saleAmountMetric.getMetricId()).build()).getData().get(0);
        PrimaryMetricModel saleAmountMetricModelOfProvince = new PrimaryMetricModel();
        BeanUtils.copyProperties(saleAmountMetricModel,saleAmountMetricModelOfProvince);

        AdvanceCalculate advanceCalculate = new AdvanceCalculate();
        BeanUtils.copyProperties(saleAmountMetricModelOfProvince.getAdvanceCalculate(),advanceCalculate);
        advanceCalculate.setAssist(true);
        advanceCalculate.setAllowDims(Arrays.asList("dt","province_id"));
        saleAmountMetricModelOfProvince.setAdvanceCalculate(advanceCalculate);

        DeriveMetricModel roiMetricModel = new DeriveMetricModel();
        roiMetricModel.setCode("sale_amount_rate");
        roiMetricModel.setMetricModelId(RandomUtils.nextLong());
        roiMetricModel.setMetricId(roi.getMetricId());
        roiMetricModel.setFormula("${0}/${1}");
        AdvanceCalculate advanceCalculate2 = new AdvanceCalculate();
        roiMetricModel.setAdvanceCalculate(advanceCalculate2);
        roiMetricModel.setRelateMetricModels(Arrays.asList(saleAmountMetricModel,saleAmountMetricModelOfProvince));

        Mockito.when(catalogMetaFacade.getMetricModels(GetMetricModelRequest.builder().appId(app.getAppId()).metricId(roi.getMetricId()).build())).thenReturn(Response.success(Arrays.asList(roiMetricModel)));

        String logicalSelect = "select sale_amount_rate,city_id,province_id,dt from 12222 where dt between 20230101 and 20230301 group by dt,city_id,province_id";
        QueryMatchedModelDTO queryMatchedModelDTO = modelMatchService.getMetricMatchedModels(SQLExprUtils.parse(logicalSelect));
        for(QueryMatchedModelDTO.MetricMatchedModelDTO metricMatchedModelDTO:queryMatchedModelDTO.getMetricMatchedModelList()) {
            for(QueryMatchedModelDTO.MetricModel metricModel:metricMatchedModelDTO.getMetricModels()) {
                System.out.println(metricModel.getMetricSelect().toString());
            }
        }
    }

    private AppDefinition initAppDefinition(List<String> sqls) {
        List<ModelDefinition> models = new ArrayList<>();
        for(String sql:sqls) {
            Long modelId = RandomUtils.nextLong();
            MySqlSelectQueryBlock selectQueryBlock = (MySqlSelectQueryBlock) SQLExprUtils.parse(sql).getQueryBlock();
            List<SQLSelectItem> selectItems = selectQueryBlock.getSelectList();

            ModelDefinition modelDefinition = new ModelDefinition();
            modelDefinition.setModelId(modelId);
            modelDefinition.setMetrics(getModelMetrics(modelId,selectItems));
            modelDefinition.setDims(getModelDims(selectItems));
            modelDefinition.setTableSource(selectQueryBlock.getFrom().toString());
            if(!Objects.isNull(selectQueryBlock.getWhere())) {
                modelDefinition.setCondition(selectQueryBlock.getWhere().toString());
            }

            Mockito.when(catalogModelFacade.getModel(GetModelRequest.builder().modelId(modelDefinition.getModelId()).build())).thenReturn(Response.success(modelDefinition));
            models.add(modelDefinition);
        }

        AppDefinition appDefinition = new AppDefinition();
        appDefinition.setAppId(RandomUtils.nextLong());
        appDefinition.setModelIds(models.stream().map(ModelDefinition::getModelId).collect(Collectors.toList()));
        appDefinition.setMetrics(getAppMetrics(models));
        appDefinition.setDims(getAppDims(models));

        Mockito.when(catalogAppFacade.getApp(Mockito.any())).thenReturn(appDefinition);

        Map<String, List<MetricModel>> metricModelMap = getMetricModelMap(models);

        for(AppDefinition.Metric appMetric : appDefinition.getMetrics()) {
            List<MetricModel> metricModels = metricModelMap.get(appMetric.getMetricCode());
            Mockito.when(catalogMetaFacade.getMetricModels(GetMetricModelRequest.builder().appId(appDefinition.getAppId()).metricId(appMetric.getMetricId()).build())).thenReturn(Response.success(metricModels));
        }

        Map<String,List<DimModel>> dimModelMap = getDimModelMap(models);

        for(AppDefinition.Dim appDim : appDefinition.getDims()) {
            Mockito.when(catalogMetaFacade.getDimModels(GetDimModelRequest.builder().appId(appDefinition.getAppId()).dimId(appDim.getDimId()).build())).thenReturn(Response.success(dimModelMap.get(appDim.getDimCode())));
        }

        return appDefinition;
    }

    private Map<String, List<MetricModel>> getMetricModelMap(List<ModelDefinition> models) {
        Map<String,List<MetricModel>> metricModelMap = new HashMap<>();
        for(ModelDefinition model:models) {
            List<String> allowDims = model.getDims().stream().map(ModelDefinition.Dim::getDimCode).collect(Collectors.toList());

            for(ModelDefinition.Metric metric : model.getMetrics()) {
                PrimaryMetricModel metricModel = new PrimaryMetricModel();
                metricModel.setMetricModelId(metric.getMetricModelId());
                metricModel.setMetricId(metric.getMetricId());
                metricModel.setCode(metric.getMetricCode());
                metricModel.setFormula(metric.getFormula());
                metricModel.setModelId(model.getModelId());
                AdvanceCalculate advanceCalculate = metric.getAdvanceCalculate();
                if(Objects.isNull(metric.getAdvanceCalculate())) {
                    advanceCalculate = new AdvanceCalculate();
                }
                advanceCalculate.setAllowDims(allowDims);
                if(metric.getMetricCode().equals("uv")) {
                    SecondaryCalculate secondaryCalculate = new SecondaryCalculate();
                    secondaryCalculate.setAgg("avg");
                    secondaryCalculate.setGroupByList(Arrays.asList("dt"));
                    advanceCalculate.setSecondary(secondaryCalculate);
                }
                metricModel.setAdvanceCalculate(advanceCalculate);

                metricModelMap.putIfAbsent(metricModel.getCode(),new ArrayList<>());
                metricModelMap.get(metricModel.getCode()).add(metricModel);
            }
        }
        return metricModelMap;
    }

    private Map<String,List<DimModel>> getDimModelMap(List<ModelDefinition> models) {
        Map<String,List<DimModel>> dimModelMap = new HashMap<>();
        for(ModelDefinition model:models) {
            for(ModelDefinition.Dim dim:model.getDims()) {
                DimModel dimModel = new DimModel();
                dimModel.setDimModelId(dim.getDimModelId());
                dimModel.setDimId(dim.getDimId());
                dimModel.setModelId(model.getModelId());
                dimModel.setCode(dim.getDimCode());
                dimModel.setFormula(dim.getFormula());

                dimModelMap.putIfAbsent(dim.getDimCode(),new ArrayList<>());
                dimModelMap.get(dim.getDimCode()).add(dimModel);
            }
        }
        return dimModelMap;
    }

    private List<AppDefinition.Metric> getAppMetrics(List<ModelDefinition> models) {
        List<AppDefinition.Metric> appMetrics = new ArrayList<>();
        List<String> metricCodes = new ArrayList<>();
        for(ModelDefinition model:models) {
            for(ModelDefinition.Metric metric: model.getMetrics()) {
                if(metricCodes.contains(metric.getMetricCode())) {
                    continue;
                }
                AppDefinition.Metric appMetric = new AppDefinition.Metric();
                appMetric.setMetricId(metric.getMetricId());
                appMetric.setMetricCode(metric.getMetricCode());
                appMetric.setMetricType(MetricType.PRIMARY);
                appMetrics.add(appMetric);
                metricCodes.add(metric.getMetricCode());
            }
        }
        return appMetrics;
    }

    private List<AppDefinition.Dim> getAppDims(List<ModelDefinition> models) {
        List<AppDefinition.Dim> appDims = new ArrayList<>();
        List<String> dimCodes = new ArrayList<>();
        for(ModelDefinition model:models) {
            for(ModelDefinition.Dim dim: model.getDims()) {
                if(dimCodes.contains(dim.getDimCode())) {
                    continue;
                }
                AppDefinition.Dim appDim = new AppDefinition.Dim();
                appDim.setDimId(dim.getDimId());
                appDim.setDimCode(dim.getDimCode());
                appDim.setDimType(DimType.TEXT);
                dimCodes.add(dim.getDimCode());
                appDims.add(appDim);
            }
        }
        return appDims;
    }

    private List<ModelDefinition.Metric> getModelMetrics(Long appId,List<SQLSelectItem> selectItems) {
        List<ModelDefinition.Metric> metrics = new ArrayList<>();
        for(SQLSelectItem selectItem:selectItems) {
            SQLExpr expr = selectItem.getExpr();
            String alias = selectItem.getAlias();

            if(expr instanceof SQLAggregateExpr) {
                SQLAggregateExpr sqlAggregateExpr = (SQLAggregateExpr) expr;
                String formula = sqlAggregateExpr.toString();

                ModelDefinition.Metric metric = new ModelDefinition.Metric();
                metric.setMetricModelId(RandomUtils.nextLong());
                metric.setMetricId(Long.parseLong(String.valueOf(alias.hashCode())));
                metric.setMetricCode(alias);
                metric.setFormula(formula);
                metrics.add(metric);
            }
        }
        return metrics;
    }

    private List<ModelDefinition.Dim> getModelDims(List<SQLSelectItem> selectItems) {
        List<ModelDefinition.Dim> dims = new ArrayList<>();
        for(SQLSelectItem selectItem:selectItems) {
            SQLExpr expr = selectItem.getExpr();

            if(expr instanceof SQLIdentifierExpr) {
                SQLIdentifierExpr sqlIdentifierExpr = (SQLIdentifierExpr) expr;
                String formula = sqlIdentifierExpr.toString();
                String alias = StringUtils.isEmpty(selectItem.getAlias())?formula:selectItem.getAlias();
                ModelDefinition.Dim dim = new ModelDefinition.Dim();
                dim.setDimId((long)alias.hashCode());
                dim.setDimCode(alias);
                dim.setFormula(formula);
                dim.setDimModelId(RandomUtils.nextLong());
                dims.add(dim);
            }
            if(expr instanceof SQLPropertyExpr) {
                SQLPropertyExpr sqlPropertyExpr = (SQLPropertyExpr) expr;
                String formula = sqlPropertyExpr.toString();
                String alias = StringUtils.isEmpty(selectItem.getAlias())?sqlPropertyExpr.getName():selectItem.getAlias();
                ModelDefinition.Dim dim = new ModelDefinition.Dim();
                dim.setDimId((long)alias.hashCode());
                dim.setDimCode(alias);
                dim.setFormula(formula);
                dim.setDimModelId(RandomUtils.nextLong());
                dims.add(dim);
            }
        }
        return dims;
    }

    /**
     * 二次计算
     */
    @Test
    public void testGetMatchedModelsForAdvanceCalculate() {

    }
}
