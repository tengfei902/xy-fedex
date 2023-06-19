package com.xy.fedex.catalog.dto;

import com.xy.fedex.catalog.enums.MetaObjectType;
import lombok.Data;

@Data
public class SchemaDTO {
    private Long bizLineId;
    private MetaObjectType metaObjectType;
    private Long dsnId;
}
