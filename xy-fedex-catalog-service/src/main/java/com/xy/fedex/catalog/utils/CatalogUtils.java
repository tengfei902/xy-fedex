package com.xy.fedex.catalog.utils;

import com.xy.fedex.catalog.dto.SchemaDTO;
import com.xy.fedex.catalog.enums.MetaObjectType;

import java.util.List;

public class CatalogUtils {

    public static SchemaDTO getMetaObjectType(String objectName) {
        SchemaDTO schemaDTO = new SchemaDTO();
        if(objectName.contains("#")) {
            schemaDTO.setMetaObjectType(MetaObjectType.MODEL);
            schemaDTO.setDsnName(objectName.split("#")[0]);
            schemaDTO.setBizLineId(Long.parseLong(objectName.split("#")[1].replaceAll("biz_line_","")));
        } else {
            schemaDTO.setMetaObjectType(MetaObjectType.APP);
            schemaDTO.setBizLineId(Long.parseLong(objectName.replaceAll("biz_line_","")));
        }
        return schemaDTO;
    }
}
