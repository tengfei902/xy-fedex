package com.xy.fedex.admin.api.vo.request;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

@Builder
@Data
public class TableRequest implements Serializable {
    private Long dsnId;
    private String tableName;
}
