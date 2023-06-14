package com.xy.fedex.catalog.api.dto.request.save;

import com.xy.fedex.catalog.api.dto.request.save.field.dim.SaveDimModelRequest;
import com.xy.fedex.catalog.api.dto.request.save.field.metric.SaveMetricModelRequest;
import com.xy.fedex.catalog.common.definition.ModelDefinition;
import lombok.Data;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * @author tengfei
 */
@Data
public class SaveModelRequest implements Serializable {
    private Long modelId;
    private String modelName;
    private String modelComment;
    private String tableSource;
    private String condition;
    private String version;
    private String creator;
    private Long bizLineId;
    private Long dsnId;
    private Map<String,String> modelProp;
    private List<SaveMetricModelRequest> metricModels;
    private List<SaveDimModelRequest> dimModels;
}
