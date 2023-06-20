package com.xy.fedex.catalog.po;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

public class AppColumnPO implements Serializable {
    private Long id;

    private Long appId;

    private String columnCode;

    private String columnName;

    private Integer columnType;

    private Long columnId;

    private String columnFormat;

    private Integer status;

    private Date createTime;

    private Date updateTime;

    private static final long serialVersionUID = 1L;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getAppId() {
        return appId;
    }

    public void setAppId(Long appId) {
        this.appId = appId;
    }

    public String getColumnName() {
        return columnName;
    }

    public void setColumnName(String columnName) {
        this.columnName = columnName == null ? null : columnName.trim();
    }

    public Integer getColumnType() {
        return columnType;
    }

    public void setColumnType(Integer columnType) {
        this.columnType = columnType;
    }

    public Long getColumnId() {
        return columnId;
    }

    public void setColumnId(Long columnId) {
        this.columnId = columnId;
    }

    public String getColumnFormat() {
        return columnFormat;
    }

    public void setColumnFormat(String columnFormat) {
        this.columnFormat = columnFormat == null ? null : columnFormat.trim();
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

    public String getColumnCode() {
        return columnCode;
    }

    public void setColumnCode(String columnCode) {
        this.columnCode = columnCode;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AppColumnPO that = (AppColumnPO) o;
        return Objects.equals(id, that.id) && Objects.equals(appId, that.appId) && Objects.equals(columnCode, that.columnCode) && Objects.equals(columnName, that.columnName) && Objects.equals(columnType, that.columnType) && Objects.equals(columnId, that.columnId) && Objects.equals(columnFormat, that.columnFormat) && Objects.equals(status, that.status) && Objects.equals(createTime, that.createTime) && Objects.equals(updateTime, that.updateTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, appId, columnCode, columnName, columnType, columnId, columnFormat, status, createTime, updateTime);
    }

    @Override
    public String toString() {
        return "AppColumnPO{" +
                "id=" + id +
                ", appId=" + appId +
                ", columnCode='" + columnCode + '\'' +
                ", columnName='" + columnName + '\'' +
                ", columnType=" + columnType +
                ", columnId=" + columnId +
                ", columnFormat='" + columnFormat + '\'' +
                ", status=" + status +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                '}';
    }
}