package com.xy.fedex.catalog.common.definition;

import com.xy.fedex.catalog.common.definition.field.DimModel;
import com.xy.fedex.catalog.common.definition.field.MetricModel;
import lombok.Data;
import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * @author tengfei
 */
@Data
public class ModelDefinition implements Serializable {
    private Long modelId;
    private String modelName;
    private String modelDesc;
    private String tableSource;
    private String condition;
    private String version;
    private Map<String,String> modelProp;
    private List<MetricModel> metrics;
    private List<DimModel> dims;
}
