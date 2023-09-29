package com.xy.fedex.catalog.common.definition.field;

import lombok.Builder;
import lombok.Data;
import java.io.Serializable;

/**
 * @author tengfei
 */
@Data
public class Dim implements Serializable {
    private String dimCode;
    private String dimName;
    private String dimComment;
    private String dimFormat;
    private String dimType;
}
