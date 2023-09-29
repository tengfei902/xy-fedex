package com.xy.fedex.rpc.context;

import lombok.Data;

/**
 * @author tengfei
 */
@Data
public class UserContext {
    /**
     * 租户id
     */
    private String tenantId;
    /**
     * 用户id
     */
    private String identityId;
    /**
     * 用户类型
     */
    private UserType userType;
    /**
     * 项目
     */
    private String project;

    public String getUserName() {
        return this.tenantId+"#"+this.identityId;
    }

}
