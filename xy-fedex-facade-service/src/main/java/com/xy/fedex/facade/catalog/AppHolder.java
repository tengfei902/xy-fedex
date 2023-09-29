package com.xy.fedex.facade.catalog;

import com.alibaba.druid.sql.ast.SQLExpr;
import com.alibaba.druid.sql.ast.statement.SQLSelect;
import com.alibaba.druid.sql.ast.statement.SQLSelectItem;
import com.alibaba.druid.sql.ast.statement.SQLTableSource;
import com.github.benmanes.caffeine.cache.CacheLoader;
import com.github.benmanes.caffeine.cache.Caffeine;
import com.github.benmanes.caffeine.cache.LoadingCache;
import com.google.common.base.Joiner;
import com.xy.fedex.catalog.api.CatalogFacade;
import com.xy.fedex.catalog.api.dto.request.GetAppRequest;
import com.xy.fedex.catalog.api.dto.request.list.ListDimModelRequest;
import com.xy.fedex.catalog.api.dto.request.list.ListMetricModelRequest;
import com.xy.fedex.catalog.api.dto.response.list.ListResult;
import com.xy.fedex.catalog.common.definition.AppDefinition;
import com.xy.fedex.catalog.common.definition.ModelDefinition;
import com.xy.fedex.catalog.common.definition.field.impl.*;
import com.xy.fedex.catalog.common.enums.DimType;
import com.xy.fedex.def.Response;
import com.xy.fedex.dsl.utility.SQLExprUtils;
import com.xy.fedex.facade.exceptions.FieldNotFoundException;
import com.xy.fedex.facade.utils.ApplicationContextUtils;
import com.xy.fedex.facade.utils.Tupple;
import com.xy.fedex.facade.utils.Tupple3;
import lombok.Data;
import lombok.ToString;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.springframework.beans.BeanUtils;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author tengfei
 */
public class AppHolder {
    private static final CatalogFacade catalogFacade;

    private static LoadingCache<Long,App> APPCACHE = Caffeine.newBuilder().build(new CacheLoader<Long, App>() {
                @Override
                public @Nullable App load(Long appId) throws Exception {
                    return getAppFromCatalog(appId);
                }
            });

    private static LoadingCache<Long,Model> MODELCACHE = Caffeine.newBuilder().build(new CacheLoader<Long, Model>() {
        @Override
        public @Nullable Model load(Long modelId) throws Exception {
            return getModelFromCatalog(modelId);
        }
    });

    private static LoadingCache<Tupple<Long,Long>,List<MetricModel>> IDX_APP_METRIC_METRICMODELCACHE = Caffeine.newBuilder().build(new CacheLoader<Tupple<Long,Long>,List<MetricModel>>() {
        @Override
        public @Nullable List<MetricModel> load(Tupple<Long, Long> key) throws Exception {
            Response<ListResult<com.xy.fedex.catalog.common.definition.field.impl.MetricModel>> response = catalogFacade.getMetricModels(ListMetricModelRequest.builder().appId(key.get_1()).metricId(key.get_2()).build());
            return response.getData().getData().stream().map(CatalogConverter::convert).collect(Collectors.toList());
        }
    });

    private static LoadingCache<Tupple3<Long,Long,Long>,MetricModel> UNQ_APP_METRIC_MODEL_METRICMODELCACHE = Caffeine.newBuilder().build(new CacheLoader<Tupple3<Long, Long, Long>, MetricModel>() {
        @Override
        public @Nullable MetricModel load(Tupple3<Long, Long, Long> key) throws Exception {
            Response<ListResult<com.xy.fedex.catalog.common.definition.field.impl.MetricModel>> response = catalogFacade.getMetricModels(ListMetricModelRequest.builder().appId(key.get_1()).modelId(key.get_3()).metricId(key.get_2()).build());
            return CatalogConverter.convert(response.getData().getData().get(0));
        }
    });

    private static LoadingCache<Tupple<Long,Long>,List<DimModel>> IDX_APP_DIM_DIMMODELCACHE = Caffeine.newBuilder().build(new CacheLoader<Tupple<Long, Long>, List<DimModel>>() {
        @Override
        public @Nullable List<DimModel> load(Tupple<Long, Long> key) throws Exception {
            Response<ListResult<com.xy.fedex.catalog.common.definition.field.impl.DimModel>> response = catalogFacade.getDimModels(ListDimModelRequest.builder().appId(key.get_1()).dimId(key.get_2()).build());
            return response.getData().getData().stream().map(dimModel -> CatalogConverter.convert(dimModel)).collect(Collectors.toList());
        }
    });

    private static LoadingCache<Tupple3<Long,Long,Long>,DimModel> UNQ_APP_DIM_MODEL_DIMMODELCACHE = Caffeine.newBuilder().build(new CacheLoader<Tupple3<Long, Long, Long>, DimModel>() {
        @Override
        public @Nullable DimModel load(Tupple3<Long, Long, Long> key) throws Exception {
            Response<ListResult<com.xy.fedex.catalog.common.definition.field.impl.DimModel>> response = catalogFacade.getDimModels(ListDimModelRequest.builder().appId(key.get_1()).modelId(key.get_3()).dimId(key.get_2()).build());
            return CatalogConverter.convert(response.getData().getData().get(0));
        }
    });


    static {
        catalogFacade = ApplicationContextUtils.getBean(CatalogFacade.class);
    }

    public static App getApp(String appId) {
        return APPCACHE.get(Long.parseLong(appId));
    }

    public static App getApp(Long appId) {
        return APPCACHE.get(appId);
    }

    private static App getAppFromCatalog(Long appId) {
        Response<AppDefinition> response = catalogFacade.getApp(GetAppRequest.builder().appId(appId).build());
        return new App(response.getData());
    }

    public static Model getModel(Long modelId) {
        return MODELCACHE.get(modelId);
    }

    private static Model getModelFromCatalog(Long modelId) {
        Response<ModelDefinition> response = catalogFacade.getModel(modelId);
        ModelDefinition modelDefinition = response.getData();
        return new Model(modelDefinition);
    }

    @Data
    public static class App implements Serializable {
        private Long appId;
        private String appName;
        private String appComment;
        private List<Long> modelIds;
        private List<Metric> metrics;
        private List<Dim> dims;

        private Map<String,Metric> metricMap;

        private Map<String,Dim> dimMap;

        public App(AppDefinition appDefinition) {
            this.appId = appDefinition.getAppId();
            this.appName = appDefinition.getAppName();
            this.appComment = appDefinition.getAppComment();
            this.modelIds = appDefinition.getModelIds();
            if(!CollectionUtils.isEmpty(appDefinition.getMetrics())) {
                this.metrics = appDefinition.getMetrics().stream().map(metric -> {
                    Metric m = new Metric();
                    BeanUtils.copyProperties(metric,m);
                    m.setAppId(this.appId);
                    return m;
                }).collect(Collectors.toList());
            }
            if(!CollectionUtils.isEmpty(appDefinition.getDims())) {
                this.dims = appDefinition.getDims().stream().map(dim -> {
                    Dim d = new Dim();
                    BeanUtils.copyProperties(dim,d);
                    d.setAppId(this.appId);
                    return d;
                }).collect(Collectors.toList());
            }
        }

        public Field findField(String fieldName) {
            if(Objects.isNull(this.metricMap)) {
                synchronized (this) {
                    this.metricMap = this.metrics.stream().collect(Collectors.toMap(metric -> metric.getMetricCode(), Function.identity()));
                }
            }
            Metric metric = metricMap.get(fieldName);
            if(!Objects.isNull(metric)) {
                return metric;
            }
            if(Objects.isNull(this.dimMap)) {
                synchronized (this) {
                    this.dimMap = this.dims.stream().collect(Collectors.toMap(dim -> dim.getDimCode(),Function.identity()));
                }
            }
            Dim dim = dimMap.get(fieldName);
            if(!Objects.isNull(dim)) {
                return dim;
            }
            throw new FieldNotFoundException(String.format("field:%s not found in app:%s",fieldName,this.appId));
        }
    }

    @ToString
    @Data
    public static class Model {
        private Long modelId;
        private String modelName;
        private String modelComment;
        private SQLTableSource tableSource;
        private SQLExpr condition;
        private Map<String,String> modelProp;
        private List<MetricModel> metrics;
        private Map<String,MetricModel> metricMap;
        private List<DimModel> dims;
        private Map<String,DimModel> dimMap;

        public Model(ModelDefinition modelDefinition) {
            this.modelId = modelDefinition.getModelId();
            this.modelName = modelDefinition.getModelName();
            this.modelComment = modelDefinition.getModelComment();
            String sql = getModelSql(modelDefinition);

            SQLSelect sqlSelect = SQLExprUtils.parse(sql);
            this.tableSource = sqlSelect.getQueryBlock().getFrom();

            List<SQLSelectItem> selectItems = sqlSelect.getQueryBlock().getSelectList();

            Map<String,SQLSelectItem> selectItemMap = selectItems.stream().collect(Collectors.toMap(SQLSelectItem::getAlias,Function.identity()));
            this.metrics = modelDefinition.getMetrics().stream().map(metricModel -> getMetricModel(metricModel,selectItemMap.get(metricModel.getMetricCode()))).collect(Collectors.toList());
            this.dims = modelDefinition.getDims().stream().map(dimModel -> getDimModel(dimModel,selectItemMap.get(dimModel.getDimCode()))).collect(Collectors.toList());

            this.metricMap = this.metrics.stream().collect(Collectors.toMap(MetricModel::getMetricCode,Function.identity()));
            this.dimMap = this.dims.stream().collect(Collectors.toMap(DimModel::getDimCode,Function.identity()));

            this.modelProp = modelDefinition.getModelProp();
        }

        public DimModel getDim(String dimCode) {
            return this.dimMap.get(dimCode);
        }

        public MetricModel getMetric(String metricCode) {
            return this.metricMap.get(metricCode);
        }

        private MetricModel getMetricModel(com.xy.fedex.catalog.common.definition.field.impl.MetricModel metricModel,SQLSelectItem selectItem) {
            if(metricModel instanceof com.xy.fedex.catalog.common.definition.field.impl.PrimaryMetricModel) {
                com.xy.fedex.catalog.common.definition.field.impl.PrimaryMetricModel pm = (com.xy.fedex.catalog.common.definition.field.impl.PrimaryMetricModel) metricModel;
                PrimaryMetricModel primaryMetricModel = new PrimaryMetricModel();
                primaryMetricModel.setModelId(pm.getModelId());
                primaryMetricModel.setMetricModelId(pm.getMetricModelId());
                primaryMetricModel.setMetricId(pm.getMetricId());
                primaryMetricModel.setMetricCode(pm.getMetricCode());
                primaryMetricModel.setCondition(SQLExprUtils.getSqlCondition(pm.getAdvanceCalculate().getCondition()));
                primaryMetricModel.setAllowDims(pm.getAdvanceCalculate().getAllowDims());
                primaryMetricModel.setForceDims(pm.getAdvanceCalculate().getForceDims());
                primaryMetricModel.setAssist(pm.getAdvanceCalculate().isAssist());
                primaryMetricModel.setSecondaryCalculate(pm.getAdvanceCalculate().getSecondary());
                primaryMetricModel.setFormula(selectItem.getExpr());
                return primaryMetricModel;
            } else {
                com.xy.fedex.catalog.common.definition.field.impl.DeriveMetricModel dm = (com.xy.fedex.catalog.common.definition.field.impl.DeriveMetricModel) metricModel;
                DeriveMetricModel deriveMetricModel = new DeriveMetricModel();
                deriveMetricModel.setMetricId(dm.getMetricId());
                deriveMetricModel.setMetricModelId(dm.getMetricModelId());
                deriveMetricModel.setMetricCode(dm.getMetricCode());
                deriveMetricModel.setRelateMetricModels(dm.getRelateMetricModels().stream().map(m -> {
                    SQLSelectItem relateSelectItem = SQLExprUtils.getSqlSelectItem(m.getFormula()+" as "+m.getMetricCode());
                    return getMetricModel(m,relateSelectItem);
                }).collect(Collectors.toList()));
                deriveMetricModel.setFormula(selectItem.getExpr());
                return deriveMetricModel;
            }
        }

        private DimModel getDimModel(com.xy.fedex.catalog.common.definition.field.impl.DimModel dimModel,SQLSelectItem selectItem) {
            DimModel dim = new DimModel();
            dim.setDimId(dimModel.getDimId());
            dim.setDimModelId(dimModel.getDimModelId());
            dim.setDimCode(dimModel.getDimCode());
            dim.setFormula(selectItem.getExpr());
            return dim;
        }

        private String getModelSql(ModelDefinition modelDefinition) {
            StringBuilder sql = new StringBuilder("select ");
            List<String> metrics = modelDefinition.getMetrics().stream().map(metricModel -> metricModel.getFormula()+" as "+metricModel.getMetricCode()).collect(Collectors.toList());
            List<String> dims = modelDefinition.getDims().stream().map(dimModel -> dimModel.getFormula()+" as "+dimModel.getDimCode()).collect(Collectors.toList());
            sql = sql.append(Joiner.on(",").join(metrics));

            if(!CollectionUtils.isEmpty(dims)) {
                sql = sql.append(",");
                sql = sql.append(Joiner.on(",").join(dims));
            }

            sql = sql.append(" from ").append(modelDefinition.getTableSource());
            if(!StringUtils.isEmpty(modelDefinition.getCondition())) {
                sql = sql.append(" where ").append(modelDefinition.getCondition());
            }

            if(!CollectionUtils.isEmpty(dims)) {
                sql = sql.append(" group by ").append(Joiner.on(",").join(modelDefinition.getDims().stream().map(com.xy.fedex.catalog.common.definition.field.impl.DimModel::getFormula).collect(Collectors.toList())));
            }
            return sql.toString();
        }
    }

    @Data
    public static class Field implements Serializable {
        protected Long appId;
    }

    @Data
    public static class Metric extends Field {
        private Long metricId;
        private String metricCode;
        private String metricName;
        private String metricComment;

        public List<MetricModel> getMetricModels() {
            return IDX_APP_METRIC_METRICMODELCACHE.get(new Tupple<>(this.appId,this.metricId));
        }

        public MetricModel getMetricModel(Long modelId) {
            return UNQ_APP_METRIC_MODEL_METRICMODELCACHE.get(new Tupple3<>(this.appId,this.metricId,modelId));
        }
    }

    @Data
    public static class Dim extends Field {
        private Long dimId;
        private String dimCode;
        private String dimName;
        private String dimComment;
        private DimType dimType;

        public List<DimModel> getDimModels() {
            return IDX_APP_DIM_DIMMODELCACHE.get(new Tupple<>(this.appId,this.dimId));
        }

        public DimModel getDimModel(Long modelId) {
            return UNQ_APP_DIM_MODEL_DIMMODELCACHE.get(new Tupple3<>(this.appId,this.dimId,modelId));
        }
    }

    @Data
    public static class MetricModel {
        private Long metricId;
        private String metricCode;
        private Long metricModelId;
        private SQLExpr formula;

        @Override
        public String toString() {
            return "MetricModel{" +
                    "metricId=" + metricId +
                    ", metricCode='" + metricCode + '\'' +
                    ", metricModelId=" + metricModelId +
                    ", formula=" + formula.toString() +
                    '}';
        }
    }

    @Data
    public static class DimModel {
        private Long dimId;
        private String dimCode;
        private Long dimModelId;
        private SQLExpr formula;

        @Override
        public String toString() {
            return "DimModel{" +
                    "dimId=" + dimId +
                    ", dimCode='" + dimCode + '\'' +
                    ", dimModelId=" + dimModelId +
                    ", formula=" + formula.toString() +
                    '}';
        }
    }

    @Data
    public static class PrimaryMetricModel extends MetricModel {
        private Long modelId;
        private SQLExpr condition;
        private List<String> allowDims;
        private List<String> forceDims;
        private boolean assist;
        private SecondaryCalculate secondaryCalculate;
    }

    @Data
    public static class DeriveMetricModel extends MetricModel {
        private List<MetricModel> relateMetricModels;
    }
}
