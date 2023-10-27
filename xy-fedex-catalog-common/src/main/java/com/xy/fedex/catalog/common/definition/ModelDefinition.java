package com.xy.fedex.catalog.common.definition;

import com.xy.fedex.catalog.common.definition.field.impl.DimModel;
import com.xy.fedex.catalog.common.definition.field.impl.MetricModel;
import java.util.Date;
import lombok.Data;
import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * @author tengfei
 */
@Data
public class ModelDefinition implements Serializable {
    private String modelCode;
    private String modelName;
    private String modelComment;
    private Map<String,String> modelProp;
    private List<MetricModel> metrics;
    private List<DimModel> dims;
    private String tableSource;
    private String version;
    private String creator;
    private Date createTime;
    private Date updateTime;
}
