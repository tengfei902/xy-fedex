package com.xy.fedex.facade.catalog;

import com.alibaba.druid.sql.ast.statement.SQLSelect;
import com.alibaba.druid.sql.ast.statement.SQLSelectItem;
import com.xy.fedex.catalog.common.definition.field.impl.DeriveMetricModel;
import com.xy.fedex.catalog.common.definition.field.impl.DimModel;
import com.xy.fedex.catalog.common.definition.field.impl.MetricModel;
import com.xy.fedex.catalog.common.definition.field.impl.PrimaryMetricModel;
import com.xy.fedex.dsl.utility.SQLExprUtils;
import com.xy.fedex.facade.service.meta.dto.QueryMatchedModelDTO;
import org.apache.commons.lang3.StringUtils;

import java.util.List;
import java.util.stream.Collectors;

public class CatalogConverter {

    public static AppHolder.MetricModel convert(MetricModel metricModel) {
        if(metricModel instanceof PrimaryMetricModel) {
            AppHolder.PrimaryMetricModel primaryMetricModel = convertPrimaryMetricModel((PrimaryMetricModel) metricModel);
            return primaryMetricModel;
        } else {
            DeriveMetricModel deriveMetricModel = (DeriveMetricModel) metricModel;
            return convertDeriveMetricModel(deriveMetricModel);
        }
    }

    private static AppHolder.PrimaryMetricModel convertPrimaryMetricModel(PrimaryMetricModel primaryMetricModel) {
        SQLSelect sqlSelect = getMetricSelect(primaryMetricModel);

        AppHolder.PrimaryMetricModel pm = new AppHolder.PrimaryMetricModel();
        pm.setMetricModelId(primaryMetricModel.getMetricModelId());
        pm.setModelId(primaryMetricModel.getModelId());
        pm.setMetricId(primaryMetricModel.getMetricId());
        pm.setMetricCode(primaryMetricModel.getMetricCode());
        pm.setFormula(sqlSelect.getQueryBlock().getSelectItem(0).getExpr());
        pm.setCondition(sqlSelect.getQueryBlock().getWhere());
        pm.setAssist(primaryMetricModel.getAdvanceCalculate().isAssist());
        pm.setSecondaryCalculate(primaryMetricModel.getAdvanceCalculate().getSecondary());
        pm.setAllowDims(primaryMetricModel.getAdvanceCalculate().getAllowDims());
        pm.setForceDims(primaryMetricModel.getAdvanceCalculate().getForceDims());
        return pm;
    }

    private static SQLSelect getMetricSelect(PrimaryMetricModel primaryMetricModel) {
        String select = "select";
        select = select+" "+primaryMetricModel.getFormula() +" as "+primaryMetricModel.getMetricCode();
        if(!StringUtils.isEmpty(primaryMetricModel.getAdvanceCalculate().getCondition())) {
            select = select+" from t where "+primaryMetricModel.getAdvanceCalculate().getCondition();
        }
        SQLSelect sqlSelect = SQLExprUtils.parse(select);
        return sqlSelect;
    }

    private static AppHolder.DeriveMetricModel convertDeriveMetricModel(DeriveMetricModel deriveMetricModel) {
        SQLSelect sqlSelect = getMetricSelect(deriveMetricModel);
        AppHolder.DeriveMetricModel dm = new AppHolder.DeriveMetricModel();
        dm.setMetricId(deriveMetricModel.getMetricId());
        dm.setMetricCode(deriveMetricModel.getMetricCode());
        dm.setMetricModelId(deriveMetricModel.getMetricModelId());
        dm.setFormula(sqlSelect.getQueryBlock().getSelectItem(0).getExpr());
        List<AppHolder.MetricModel> relateMetricModels = deriveMetricModel.getRelateMetricModels().stream().map(metricModel -> convert(metricModel)).collect(Collectors.toList());
        dm.setRelateMetricModels(relateMetricModels);
        return dm;
    }

    private static SQLSelect getMetricSelect(DeriveMetricModel deriveMetricModel) {
        String select = "select";
        select = select+" "+deriveMetricModel.getFormula()+" as "+deriveMetricModel.getMetricCode();
        return SQLExprUtils.parse(select);
    }

    public static AppHolder.DimModel convert(DimModel dimModel) {
        AppHolder.DimModel dim = new AppHolder.DimModel();
        dim.setDimId(dimModel.getDimId());
        dim.setDimModelId(dimModel.getDimModelId());
        dim.setDimCode(dimModel.getDimCode());
        dim.setFormula(SQLExprUtils.getSqlSelectItem(dimModel.getFormula()+" as "+dimModel.getDimCode()).getExpr());
        return dim;
    }
}
