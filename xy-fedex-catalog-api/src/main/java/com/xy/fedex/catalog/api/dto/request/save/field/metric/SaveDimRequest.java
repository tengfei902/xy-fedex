package com.xy.fedex.catalog.api.dto.request.save.field.metric;

import lombok.Data;

import java.io.Serializable;

/**
 * @author tengfei
 */
@Data
public class SaveDimRequest implements Serializable {
    private Long bizLineId;
    private Long dimId;
    private String dimCode;
    private String dimName;
    private String dimComment;
    private Long dimFamilyId;
    private Boolean master;
}
