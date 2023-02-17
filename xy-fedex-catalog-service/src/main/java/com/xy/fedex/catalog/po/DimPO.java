package com.xy.fedex.catalog.po;

import java.io.Serializable;
import java.util.Date;

public class DimPO implements Serializable {
    private Long id;

    private Long tenantId;

    private Long bizLineId;

    private String dimName;

    private Integer dimType;

    private String dimDesc;

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

    public Long getTenantId() {
        return tenantId;
    }

    public void setTenantId(Long tenantId) {
        this.tenantId = tenantId;
    }

    public Long getBizLineId() {
        return bizLineId;
    }

    public void setBizLineId(Long bizLineId) {
        this.bizLineId = bizLineId;
    }

    public String getDimName() {
        return dimName;
    }

    public void setDimName(String dimName) {
        this.dimName = dimName == null ? null : dimName.trim();
    }

    public Integer getDimType() {
        return dimType;
    }

    public void setDimType(Integer dimType) {
        this.dimType = dimType;
    }

    public String getDimDesc() {
        return dimDesc;
    }

    public void setDimDesc(String dimDesc) {
        this.dimDesc = dimDesc == null ? null : dimDesc.trim();
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
        DimPO other = (DimPO) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getTenantId() == null ? other.getTenantId() == null : this.getTenantId().equals(other.getTenantId()))
            && (this.getBizLineId() == null ? other.getBizLineId() == null : this.getBizLineId().equals(other.getBizLineId()))
            && (this.getDimName() == null ? other.getDimName() == null : this.getDimName().equals(other.getDimName()))
            && (this.getDimType() == null ? other.getDimType() == null : this.getDimType().equals(other.getDimType()))
            && (this.getDimDesc() == null ? other.getDimDesc() == null : this.getDimDesc().equals(other.getDimDesc()))
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
        result = prime * result + ((getTenantId() == null) ? 0 : getTenantId().hashCode());
        result = prime * result + ((getBizLineId() == null) ? 0 : getBizLineId().hashCode());
        result = prime * result + ((getDimName() == null) ? 0 : getDimName().hashCode());
        result = prime * result + ((getDimType() == null) ? 0 : getDimType().hashCode());
        result = prime * result + ((getDimDesc() == null) ? 0 : getDimDesc().hashCode());
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
        sb.append(", tenantId=").append(tenantId);
        sb.append(", bizLineId=").append(bizLineId);
        sb.append(", dimName=").append(dimName);
        sb.append(", dimType=").append(dimType);
        sb.append(", dimDesc=").append(dimDesc);
        sb.append(", status=").append(status);
        sb.append(", createTime=").append(createTime);
        sb.append(", updateTime=").append(updateTime);
        sb.append(", creator=").append(creator);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}