package com.xy.fedex.catalog.po;

import com.google.common.collect.Lists;
import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Objects;

public class MetricModelPO implements Serializable {
    private Long id;

    private String modelId;

    private Long metricId;

    private Integer metricType;

    private String formula;

    private String advanceCalculate;

    private Integer status;

    private Date createTime;

    private Date updateTime;

    private String creator;

    private String modelIdArray;

    private String metricCode;

    private static final long serialVersionUID = 1L;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getModelId() {
        return modelId;
    }

    public List<Long> getModelIds() {
        if(Objects.isNull(this.modelId)) {
            return Lists.newArrayList();
        }
        List<Long> modelIds = new Gson().fromJson(this.modelId,new TypeToken<List<Long>>(){}.getType());
        return modelIds;
    }

    public void setModelId(String modelId) {
        this.modelId = modelId == null ? null : modelId.trim();
    }

    public Long getMetricId() {
        return metricId;
    }

    public void setMetricId(Long metricId) {
        this.metricId = metricId;
    }

    public Integer getMetricType() {
        return metricType;
    }

    public void setMetricType(Integer metricType) {
        this.metricType = metricType;
    }

    public String getFormula() {
        return formula;
    }

    public void setFormula(String formula) {
        this.formula = formula == null ? null : formula.trim();
    }

    public String getAdvanceCalculate() {
        return advanceCalculate;
    }

    public void setAdvanceCalculate(String advanceCalculate) {
        this.advanceCalculate = advanceCalculate == null ? null : advanceCalculate.trim();
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator == null ? null : creator.trim();
    }

    public String getModelIdArray() {
        return modelIdArray;
    }

    public void setModelIdArray(String modelIdArray) {
        this.modelIdArray = modelIdArray == null ? null : modelIdArray.trim();
    }

    public String getMetricCode() {
        return metricCode;
    }

    public void setMetricCode(String metricCode) {
        this.metricCode = metricCode;
    }

    @Override
    public boolean equals(Object that) {
        if (this == that) {
            return true;
        }
        if (that == null) {
            return false;
        }
        if (getClass() != that.getClass()) {
            return false;
        }
        MetricModelPO other = (MetricModelPO) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getModelId() == null ? other.getModelId() == null : this.getModelId().equals(other.getModelId()))
            && (this.getMetricId() == null ? other.getMetricId() == null : this.getMetricId().equals(other.getMetricId()))
            && (this.getMetricType() == null ? other.getMetricType() == null : this.getMetricType().equals(other.getMetricType()))
            && (this.getFormula() == null ? other.getFormula() == null : this.getFormula().equals(other.getFormula()))
            && (this.getAdvanceCalculate() == null ? other.getAdvanceCalculate() == null : this.getAdvanceCalculate().equals(other.getAdvanceCalculate()))
            && (this.getStatus() == null ? other.getStatus() == null : this.getStatus().equals(other.getStatus()))
            && (this.getCreateTime() == null ? other.getCreateTime() == null : this.getCreateTime().equals(other.getCreateTime()))
            && (this.getUpdateTime() == null ? other.getUpdateTime() == null : this.getUpdateTime().equals(other.getUpdateTime()))
            && (this.getCreator() == null ? other.getCreator() == null : this.getCreator().equals(other.getCreator()))
            && (this.getModelIdArray() == null ? other.getModelIdArray() == null : this.getModelIdArray().equals(other.getModelIdArray()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getModelId() == null) ? 0 : getModelId().hashCode());
        result = prime * result + ((getMetricId() == null) ? 0 : getMetricId().hashCode());
        result = prime * result + ((getMetricType() == null) ? 0 : getMetricType().hashCode());
        result = prime * result + ((getFormula() == null) ? 0 : getFormula().hashCode());
        result = prime * result + ((getAdvanceCalculate() == null) ? 0 : getAdvanceCalculate().hashCode());
        result = prime * result + ((getStatus() == null) ? 0 : getStatus().hashCode());
        result = prime * result + ((getCreateTime() == null) ? 0 : getCreateTime().hashCode());
        result = prime * result + ((getUpdateTime() == null) ? 0 : getUpdateTime().hashCode());
        result = prime * result + ((getCreator() == null) ? 0 : getCreator().hashCode());
        result = prime * result + ((getModelIdArray() == null) ? 0 : getModelIdArray().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", modelId=").append(modelId);
        sb.append(", metricId=").append(metricId);
        sb.append(", metricType=").append(metricType);
        sb.append(", formula=").append(formula);
        sb.append(", advanceCalculate=").append(advanceCalculate);
        sb.append(", status=").append(status);
        sb.append(", createTime=").append(createTime);
        sb.append(", updateTime=").append(updateTime);
        sb.append(", creator=").append(creator);
        sb.append(", modelIdArray=").append(modelIdArray);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}