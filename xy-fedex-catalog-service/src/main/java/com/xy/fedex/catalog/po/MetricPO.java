package com.xy.fedex.catalog.po;

import java.io.Serializable;
import java.util.Date;

public class MetricPO implements Serializable {
    private Long id;

    private String metricCode;

    private String metricName;

    private String metricComment;

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

    public String getMetricCode() {
        return metricCode;
    }

    public void setMetricCode(String metricCode) {
        this.metricCode = metricCode == null ? null : metricCode.trim();
    }

    public String getMetricName() {
        return metricName;
    }

    public void setMetricName(String metricName) {
        this.metricName = metricName == null ? null : metricName.trim();
    }

    public String getMetricComment() {
        return metricComment;
    }

    public void setMetricComment(String metricComment) {
        this.metricComment = metricComment == null ? null : metricComment.trim();
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
        MetricPO other = (MetricPO) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getMetricCode() == null ? other.getMetricCode() == null : this.getMetricCode().equals(other.getMetricCode()))
            && (this.getMetricName() == null ? other.getMetricName() == null : this.getMetricName().equals(other.getMetricName()))
            && (this.getMetricComment() == null ? other.getMetricComment() == null : this.getMetricComment().equals(other.getMetricComment()))
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
        result = prime * result + ((getMetricCode() == null) ? 0 : getMetricCode().hashCode());
        result = prime * result + ((getMetricName() == null) ? 0 : getMetricName().hashCode());
        result = prime * result + ((getMetricComment() == null) ? 0 : getMetricComment().hashCode());
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
        sb.append(", metricCode=").append(metricCode);
        sb.append(", metricName=").append(metricName);
        sb.append(", metricComment=").append(metricComment);
        sb.append(", status=").append(status);
        sb.append(", createTime=").append(createTime);
        sb.append(", updateTime=").append(updateTime);
        sb.append(", creator=").append(creator);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}