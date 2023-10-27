package com.xy.fedex.catalog.api.conf;

import java.util.Map;
import java.util.Objects;
import org.apache.commons.lang3.StringUtils;

public enum VarConf {
  APP_RELATE_MODELS("app.relate_models","","app relate model codes"),
  MODEL_FORCE_DIMS("model.force_dims","","model force dims"),
  MODEL_GROUPING_SET("model.grouping_set","","model grouping sets"),
  MODEL_SHOW_NAME("model.show_name","","model show name"),
  MODEL_RELATE_TABLES("model.relate_tables","","model relate tables"),
  MODEL_DSN("model.dsn","","dsn of model"),
  MODEL_COLUMN_COMMENT("model.column_comment.%s","","comment of model column"),
  METRIC_GROUP_BY_DIMS("metric.group_by_dims","","metric default group by dims"),
  METRIC_FILTER_C0NDITION("metric.filter_condition","","metric default filter condition");

  private String varName;
  private String defaultValue;
  private String description;

  VarConf(String varName,String defaultValue,String description) {
    this.varName = varName;
    this.defaultValue = defaultValue;
    this.description = description;
  }

  public String getVarName() {
    return varName;
  }

  public String getDefaultValue() {
    return defaultValue;
  }

  public static String getParamValue(String paramKey, Map<String,String> params) {
    VarConf varConf = parse(paramKey);
    if(!Objects.isNull(varConf)) {
      return params.getOrDefault(paramKey,varConf.defaultValue);
    } else {
      return params.get(paramKey);
    }
  }

  public static VarConf parse(String paramKey) {
    for(VarConf varConf:VarConf.values()) {
      if(StringUtils.equalsIgnoreCase(varConf.getVarName(),paramKey)) {
        return varConf;
      }
    }
    return null;
  }
}
