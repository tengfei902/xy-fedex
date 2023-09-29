package com.xy.fedex.catalog.utils;

import com.xy.fedex.catalog.dto.SchemaDTO;
import com.xy.fedex.catalog.enums.MetaObjectType;
import com.xy.fedex.rpc.context.UserContextHolder;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class CatalogUtils {

    public static SchemaDTO getMetaObjectType(String objectName) {
        SchemaDTO schemaDTO = new SchemaDTO();
        if (objectName.contains("#")) {
            schemaDTO.setMetaObjectType(MetaObjectType.MODEL);
            schemaDTO.setDsnName(objectName.split("#")[0]);
            schemaDTO.setBizLineId(Long.parseLong(objectName.split("#")[1].replaceAll("biz_line_", "")));
        } else {
            schemaDTO.setMetaObjectType(MetaObjectType.APP);
            schemaDTO.setBizLineId(Long.parseLong(objectName.replaceAll("biz_line_", "")));
        }
        return schemaDTO;
    }

    /**
     * 获取对象全名
     * 对像命名规则：account@project.{objectName}
     *
     * @param objectName
     * @return
     */
    public static String getObjectFullName(String objectName) {
        String tenantId = UserContextHolder.getCurrentUser().getTenantId();
        String project = UserContextHolder.getCurrentUser().getProject();
        return String.format("%s@%s.%s", tenantId, project, objectName);
    }

    public static String getCurrentUserName() {
        return UserContextHolder.getCurrentUser().getUserName();
    }

    public static String getCurrentProject() {
        return String.format("%s@%s",UserContextHolder.getCurrentUser().getTenantId(),UserContextHolder.getCurrentUser().getProject());
    }

    public static CatalogObject getObject(String objectFullName) {
        String tenantId = objectFullName.split("@")[0];
        String project = objectFullName.split("@")[1].split("\\.")[0];
        String objectName = objectFullName.split("@")[1].split("\\.")[1];
        return new CatalogObject(tenantId,project,objectName);
    }

    public static String getCurrentVersion() {
        return new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
    }

    @AllArgsConstructor
    @Data
    public static class CatalogObject {
        private String tenantId;
        private String project;
        private String objectName;
    }
}
