package com.xy.fedex.facade.service.meta;

import com.alibaba.druid.sql.ast.SQLExpr;
import com.alibaba.druid.sql.ast.statement.SQLSelect;
import com.google.common.base.Joiner;
import com.google.gson.Gson;
import com.xy.fedex.catalog.common.definition.field.impl.SecondaryCalculate;
import com.xy.fedex.dsl.utility.SQLExprUtils;
import com.xy.fedex.facade.BaseTest;
import com.xy.fedex.facade.clients.DubboClientHolder;
import com.xy.fedex.facade.service.meta.dto.QueryMatchedModelDTO;
import com.xy.fedex.facade.service.meta.match.ModelMatchService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class ModelMatchServiceTest extends BaseTest {
    @Autowired
    private ModelMatchService modelMatchService;

    @Test
    public void testGetMetaMatchedModels() {
        String select = "select click_uv as click_uv,view_pv as view_pv,visit_buy_rate as visit_buy_rate,sale_amt as sale_amt,refund_amt as refund_amt,dt,shop_name,brand_name from 33 where dt between 20230101 and 20230401 and brand_id = 10000 group by dt,shop_name,brand_name";
        SQLSelect sqlSelect = SQLExprUtils.parse(select);
        QueryMatchedModelDTO queryMatchedModelDTO = modelMatchService.getMetricMatchedModels(sqlSelect);

        for(QueryMatchedModelDTO.MetricMatchedModelDTO metricMatchedModelDTO:queryMatchedModelDTO.getMetricMatchedModelList()) {
            System.out.println("metric_code:"+metricMatchedModelDTO.getMetricCode());
            for(QueryMatchedModelDTO.MetricModel metricModel:metricMatchedModelDTO.getMetricModels()) {
                if(metricModel instanceof QueryMatchedModelDTO.PrimaryMetricModel) {
                    System.out.println("metric_model_id:"+metricModel.getMetricModelId());
                    System.out.println(metricModel.getFormula().toString()+" as "+metricModel.getAlias());

                    String metricModelSelect = getMetricModelSelect(metricModel);
                    System.out.println(metricModelSelect);
                } else {
                    System.out.println("------");
                }
            }
        }
    }

    private String getMetricModelSelect(QueryMatchedModelDTO.MetricModel metricModel) {
        if(metricModel instanceof QueryMatchedModelDTO.PrimaryMetricModel) {
            QueryMatchedModelDTO.PrimaryMetricModel primaryMetricModel = (QueryMatchedModelDTO.PrimaryMetricModel) metricModel;
            QueryMatchedModelDTO.SecondaryCalculate secondaryCalculate = primaryMetricModel.getSecondary();

            List<String> selectItems = new ArrayList<>();
            selectItems.add(primaryMetricModel.getFormula().toString()+" as "+primaryMetricModel.getAlias());
            selectItems.addAll(primaryMetricModel.getDims().stream().map(dim -> dim.getFormula() + " as "+dim.getAlias()).collect(Collectors.toList()));
            List<String> groupByItems = primaryMetricModel.getDims().stream().map(dim -> dim.getAlias()).collect(Collectors.toList());

            if(!Objects.isNull(secondaryCalculate) && !secondaryCalculate.isEmpty()) {
                if(primaryMetricModel.getDims().containsAll(secondaryCalculate.getGroupByList())) {
                    return "select " + Joiner.on(",").join(selectItems) +" from "+primaryMetricModel.getModelId()+" where "+ primaryMetricModel.getCondition().toString()+" group by "+Joiner.on(",").join(groupByItems);
                }

                List<String> secondaryGroupByItems = new ArrayList<>(groupByItems);
                secondaryGroupByItems.addAll(secondaryCalculate.getGroupByList().stream().map(QueryMatchedModelDTO.Dim::getDimCode).collect(Collectors.toList()));
                secondaryGroupByItems = secondaryGroupByItems.stream().distinct().collect(Collectors.toList());
                List<String> secondarySelectItems = new ArrayList<>(selectItems);
                secondarySelectItems.addAll(secondaryCalculate.getGroupByList().stream().map(QueryMatchedModelDTO.Dim::getDimCode).collect(Collectors.toList()));
                secondarySelectItems = secondarySelectItems.stream().distinct().collect(Collectors.toList());

                String secondarySelect = "select "+Joiner.on(",").join(secondarySelectItems)+" from "+primaryMetricModel.getModelId()+" where "+primaryMetricModel.getCondition().toString()+" group by "+Joiner.on(",").join(secondaryGroupByItems);

                String alias = primaryMetricModel.getAlias();
                List<String> outerSelectItems = new ArrayList<>();
                outerSelectItems.add(String.format("%s(%s) as %s",secondaryCalculate.getAgg(),alias,alias));
                outerSelectItems.addAll(primaryMetricModel.getDims().stream().map(dim -> dim.getFormula()+" as "+dim.getAlias()).collect(Collectors.toList()));

                return "select "+Joiner.on(",").join(outerSelectItems) + " from ("+secondarySelect+") as t group by "+Joiner.on(",").join(primaryMetricModel.getDims());
            }
            return "select " + Joiner.on(",").join(selectItems) +" from "+primaryMetricModel.getModelId()+" where "+ primaryMetricModel.getCondition().toString()+" group by "+Joiner.on(",").join(groupByItems);
        } else {
            QueryMatchedModelDTO.DeriveMetricModel deriveMetricModel = (QueryMatchedModelDTO.DeriveMetricModel) metricModel;
            List<QueryMatchedModelDTO.MetricModel> relateMetricModels = deriveMetricModel.getRelateMetricModels();

            QueryMatchedModelDTO.MetricModel relateMetricModel0 = relateMetricModels.get(0);
            String select0 = getMetricModelSelect(relateMetricModel0);

            for(int i = 1;i<relateMetricModels.size();i++) {
                String select = getMetricModelSelect(relateMetricModels.get(i));
                QueryMatchedModelDTO.MetricModel relateMetricModel = relateMetricModels.get(i);

                select0 = select0 + " join ("+select+") as t_"+i;
            }

            SQLExpr formula = deriveMetricModel.getFormula();
            return null;
        }
    }

    /**
     * 验证基础指标
     */
    @Test
    public void testGetMatchedModelsForPrimaryMetric() {

    }
}
