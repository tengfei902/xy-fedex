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

    public static MetricType parse(Integer code) {
        for(MetricType metricType:MetricType.values()) {
            if(metricType.metricType == code) {
                return metricType;
            }
        }
        return null;
    }
}
