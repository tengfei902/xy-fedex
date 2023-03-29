package com.xy.fedex.facade.service.cs;

import com.xy.fedex.catalog.api.CatalogAppFacade;
import com.xy.fedex.catalog.api.CatalogMetaFacade;
import com.xy.fedex.catalog.api.dto.request.GetAppRequest;
import com.xy.fedex.catalog.api.dto.request.GetDimModelRequest;
import com.xy.fedex.catalog.api.dto.request.GetMetricModelRequest;
import com.xy.fedex.catalog.common.definition.AppDefinition;
import com.xy.fedex.catalog.common.definition.field.impl.DimModel;
import com.xy.fedex.catalog.common.definition.field.impl.MetricModel;
import com.xy.fedex.catalog.common.enums.DimType;
import com.xy.fedex.facade.exceptions.FieldNotFoundException;
import com.xy.fedex.facade.utils.ApplicationContextUtils;
import lombok.Data;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@Component
public class AppHolder {
    private static CatalogAppFacade catalogAppFacade;
    private static CatalogMetaFacade catalogMetaFacade;

    static {
        catalogAppFacade = ApplicationContextUtils.getBean(CatalogAppFacade.class);
        catalogMetaFacade = ApplicationContextUtils.getBean(CatalogMetaFacade.class);
    }

    public App getApp(Long appId) {
        AppDefinition appDefinition = catalogAppFacade.getApp(GetAppRequest.builder().appId(appId).build());
        App app = new App(appDefinition);
        return null;
    }

    public App getApp(String appId) {
        return getApp(Long.parseLong(appId));
    }

    @Data
    public static class App implements Serializable {
        private Long appId;
        private String appName;
        private String appDesc;
        private List<Long> modelIds;
        private List<Metric> metrics;
        private List<Dim> dims;

        private Map<String,Metric> metricMap;

        private Map<String,Dim> dimMap;

        public App(AppDefinition appDefinition) {
            this.appId = appDefinition.getAppId();
            this.appName = appDefinition.getAppName();
            this.appDesc = appDefinition.getAppDesc();
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
            Metric metric = metricMap.get(fieldName);
            if(!Objects.isNull(metric)) {
                return metric;
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
            List<MetricModel> metricModels = catalogMetaFacade.getMetricModels(GetMetricModelRequest.builder().appId(this.appId).metricId(this.metricId).build());
            return metricModels;
        }

        public MetricModel getMetricModel(Long modelId) {
            MetricModel metricModel = catalogMetaFacade.getMetricModel(GetMetricModelRequest.builder().appId(this.appId).modelId(modelId).metricId(this.metricId).build());
            return metricModel;
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
            List<DimModel> dimModels = catalogMetaFacade.getDimModels(GetDimModelRequest.builder().appId(this.appId).dimId(this.dimId).build());
            return dimModels;
        }

        public DimModel getDimModel(Long modelId) {
            DimModel dimModel = catalogMetaFacade.getDimModel(GetDimModelRequest.builder().appId(this.appId).modelId(modelId).dimId(this.dimId).build());
            return dimModel;
        }
    }
}
