package com.xy.fedex.catalog.po;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class MetricModelPO implements Serializable {
    private Long id;

    private Long modelId;

    private Long metricId;

    private String formula;

    private String condition;

    private String allowDims;

    private String forceDims;

    private String advanceCaculate;

    private String orderBy;

    private Integer status;

    private Date createTime;

    private Date updateTime;

    private String creator;

    private static final long serialVersionUID = 1L;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getModelId() {
        return modelId;
    }

    public void setModelId(Long modelId) {
        this.modelId = modelId;
    }

    public Long getMetricId() {
        return metricId;
    }

    public void setMetricId(Long metricId) {
        this.metricId = metricId;
    }

    public String getFormula() {
        return formula;
    }

    public void setFormula(String formula) {
        this.formula = formula == null ? null : formula.trim();
    }

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition == null ? null : condition.trim();
    }

    public String getAllowDims() {
        return allowDims;
    }

    public void setAllowDims(String allowDims) {
        this.allowDims = allowDims == null ? null : allowDims.trim();
    }

    public String getForceDims() {
        return forceDims;
    }

    public void setForceDims(String forceDims) {
        this.forceDims = forceDims == null ? null : forceDims.trim();
    }

    public String getAdvanceCaculate() {
        return advanceCaculate;
    }

    public void setAdvanceCaculate(String advanceCaculate) {
        this.advanceCaculate = advanceCaculate == null ? null : advanceCaculate.trim();
    }

    public String getOrderBy() {
        return orderBy;
    }

    public void setOrderBy(String orderBy) {
        this.orderBy = orderBy == null ? null : orderBy.trim();
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
            && (this.getFormula() == null ? other.getFormula() == null : this.getFormula().equals(other.getFormula()))
            && (this.getCondition() == null ? other.getCondition() == null : this.getCondition().equals(other.getCondition()))
            && (this.getAllowDims() == null ? other.getAllowDims() == null : this.getAllowDims().equals(other.getAllowDims()))
            && (this.getForceDims() == null ? other.getForceDims() == null : this.getForceDims().equals(other.getForceDims()))
            && (this.getAdvanceCaculate() == null ? other.getAdvanceCaculate() == null : this.getAdvanceCaculate().equals(other.getAdvanceCaculate()))
            && (this.getOrderBy() == null ? other.getOrderBy() == null : this.getOrderBy().equals(other.getOrderBy()))
            && (this.getStatus() == null ? other.getStatus() == null : this.getStatus().equals(other.getStatus()))
            && (this.getCreateTime() == null ? other.getCreateTime() == null : this.getCreateTime().equals(other.getCreateTime()))
            && (this.getUpdateTime() == null ? other.getUpdateTime() == null : this.getUpdateTime().equals(other.getUpdateTime()))
            && (this.getCreator() == null ? other.getCreator() == null : this.getCreator().equals(other.getCreator()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getModelId() == null) ? 0 : getModelId().hashCode());
        result = prime * result + ((getMetricId() == null) ? 0 : getMetricId().hashCode());
        result = prime * result + ((getFormula() == null) ? 0 : getFormula().hashCode());
        result = prime * result + ((getCondition() == null) ? 0 : getCondition().hashCode());
        result = prime * result + ((getAllowDims() == null) ? 0 : getAllowDims().hashCode());
        result = prime * result + ((getForceDims() == null) ? 0 : getForceDims().hashCode());
        result = prime * result + ((getAdvanceCaculate() == null) ? 0 : getAdvanceCaculate().hashCode());
        result = prime * result + ((getOrderBy() == null) ? 0 : getOrderBy().hashCode());
        result = prime * result + ((getStatus() == null) ? 0 : getStatus().hashCode());
        result = prime * result + ((getCreateTime() == null) ? 0 : getCreateTime().hashCode());
        result = prime * result + ((getUpdateTime() == null) ? 0 : getUpdateTime().hashCode());
        result = prime * result + ((getCreator() == null) ? 0 : getCreator().hashCode());
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
        sb.append(", formula=").append(formula);
        sb.append(", condition=").append(condition);
        sb.append(", allowDims=").append(allowDims);
        sb.append(", forceDims=").append(forceDims);
        sb.append(", advanceCaculate=").append(advanceCaculate);
        sb.append(", orderBy=").append(orderBy);
        sb.append(", status=").append(status);
        sb.append(", createTime=").append(createTime);
        sb.append(", updateTime=").append(updateTime);
        sb.append(", creator=").append(creator);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}