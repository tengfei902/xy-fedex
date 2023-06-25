package com.xy.fedex.facade.catalog;

import com.xy.fedex.catalog.api.CatalogFacade;
import com.xy.fedex.catalog.api.dto.request.list.GetAppRequest;
import com.xy.fedex.catalog.api.dto.request.list.ListDimModelRequest;
import com.xy.fedex.catalog.api.dto.request.list.ListMetricModelRequest;
import com.xy.fedex.catalog.api.dto.response.list.ListResult;
import com.xy.fedex.catalog.common.definition.AppDefinition;
import com.xy.fedex.catalog.common.definition.field.impl.DimModel;
import com.xy.fedex.catalog.common.definition.field.impl.MetricModel;
import com.xy.fedex.catalog.common.enums.DimType;
import com.xy.fedex.def.Response;
import com.xy.fedex.facade.exceptions.FieldNotFoundException;
import com.xy.fedex.facade.utils.ApplicationContextUtils;
import lombok.Data;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.BeanUtils;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

public class AppHolder {
    private static final CatalogFacade catalogFacade;

    static {
        catalogFacade = ApplicationContextUtils.getBean(CatalogFacade.class);
    }

    public static App getApp(Long appId) {
        Response<AppDefinition> response = catalogFacade.getApp(GetAppRequest.builder().appId(appId).build());
        return new App(response.getData());
    }

    public static App getApp(String appId) {
        return getApp(Long.parseLong(appId));
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
            Response<ListResult<MetricModel>> response = catalogFacade.getMetricModels(ListMetricModelRequest.builder().appId(this.appId).metricId(this.metricId).build());
            return response.getData().getData();
        }

        public MetricModel getMetricModel(Long modelId) {
            Response<ListResult<MetricModel>> response = catalogFacade.getMetricModels(ListMetricModelRequest.builder().appId(this.appId).modelId(modelId).metricId(this.metricId).build());
            return response.getData().getData().get(0);
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
            Response<ListResult<DimModel>> response = catalogFacade.getDimModels(ListDimModelRequest.builder().appId(this.appId).dimId(this.dimId).build());
            return response.getData().getData();
        }

        public DimModel getDimModel(Long modelId) {
            Response<ListResult<DimModel>> response = catalogFacade.getDimModels(ListDimModelRequest.builder().appId(this.appId).modelId(modelId).dimId(this.dimId).build());
            return response.getData().getData().get(0);
        }
    }
}
