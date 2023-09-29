package com.xy.fedex.rpc.context;

import org.apache.dubbo.rpc.RpcContext;

import java.util.Objects;

/**
 * @author tengfei
 */
public class Tracer {

    public static String getParam(String paramName) {
        return RpcContext.getContext().getAttachment(paramName);
    }

    public static String getTenantId() {
        return RpcContext.getContext().getAttachment(TracerParamEnum.TENANT_ID.getValue());
    }

    public static String getAccountId() {
        return RpcContext.getContext().getAttachment(TracerParamEnum.ACCOUNT_ID.getValue());
    }

    public static String getTraceId() {
        return null;
    }
}
