package com.xy.fedex.catalog.po;

import java.io.Serializable;
import java.util.Date;

public class DimFamilyPO implements Serializable {
    private Long id;

    private String tenantId;

    private Long bizLineId;

    private String dimFamilyName;

    private Long masterDimId;

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

    public String getTenantId() {
        return tenantId;
    }

    public void setTenantId(String tenantId) {
        this.tenantId = tenantId == null ? null : tenantId.trim();
    }

    public Long getBizLineId() {
        return bizLineId;
    }

    public void setBizLineId(Long bizLineId) {
        this.bizLineId = bizLineId;
    }

    public String getDimFamilyName() {
        return dimFamilyName;
    }

    public void setDimFamilyName(String dimFamilyName) {
        this.dimFamilyName = dimFamilyName == null ? null : dimFamilyName.trim();
    }

    public Long getMasterDimId() {
        return masterDimId;
    }

    public void setMasterDimId(Long masterDimId) {
        this.masterDimId = masterDimId;
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
        DimFamilyPO other = (DimFamilyPO) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getTenantId() == null ? other.getTenantId() == null : this.getTenantId().equals(other.getTenantId()))
            && (this.getBizLineId() == null ? other.getBizLineId() == null : this.getBizLineId().equals(other.getBizLineId()))
            && (this.getDimFamilyName() == null ? other.getDimFamilyName() == null : this.getDimFamilyName().equals(other.getDimFamilyName()))
            && (this.getMasterDimId() == null ? other.getMasterDimId() == null : this.getMasterDimId().equals(other.getMasterDimId()))
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
        result = prime * result + ((getDimFamilyName() == null) ? 0 : getDimFamilyName().hashCode());
        result = prime * result + ((getMasterDimId() == null) ? 0 : getMasterDimId().hashCode());
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
        sb.append(", dimFamilyName=").append(dimFamilyName);
        sb.append(", masterDimId=").append(masterDimId);
        sb.append(", createTime=").append(createTime);
        sb.append(", updateTime=").append(updateTime);
        sb.append(", creator=").append(creator);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}