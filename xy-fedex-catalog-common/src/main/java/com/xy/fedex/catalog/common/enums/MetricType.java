package com.xy.fedex.catalog.common.enums;

public enum MetricType {
    PRIMARY(0),DERIVE(1);

    private int metricType;

    MetricType(int metricType) {
        this.metricType = metricType;
    }

    public int getMetricType() {
        return metricType;
    }
}
