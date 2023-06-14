package com.xy.fedex.rpc.context;

/**
 * @author tengfei
 */

public enum TracerParamEnum {
    TENANT_ID("trace-tenant-id"),
    ACCOUNT_ID("trace-account-id");

    private String value;
    TracerParamEnum(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
