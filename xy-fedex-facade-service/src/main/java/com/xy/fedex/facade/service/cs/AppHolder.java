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
import lombok.Data;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class AppHolder {
    @DubboReference
    private static CatalogAppFacade catalogAppFacade;
    @DubboReference
    private static CatalogMetaFacade catalogMetaFacade;


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
    }

    @Data
    public static class Metric implements Serializable {
        private Long appId;
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
    public static class Dim implements Serializable {
        private Long appId;
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
