package com.xy.fedex.catalog.api.dto.request.save.field;

import lombok.Data;

import java.io.Serializable;

/**
 * @author tengfei
 */
@Data
public class SaveFieldRequest implements Serializable {
    private String code;
    private String formula;
    private String comment;
}
