package com.xy.fedex.catalog.common.enums;

import com.xy.fedex.catalog.common.exception.EnumTypeNotFoundException;

public enum DimType {
    TEXT(0),
    NUMBER(1),
    DATE(2),
    DATETIME(3),
    YEAR(4),
    MONTH(5),
    DAY(6),
    HOUR(7);

    int dimType;

    DimType(int dimType) {
        this.dimType = dimType;
    }

    public int getDimType() {
        return dimType;
    }

    public static DimType parse(Integer dimTypeCode) {
        for(DimType dimType : DimType.values()) {
            if(dimType.dimType == dimTypeCode) {
                return dimType;
            }
        }
        throw new EnumTypeNotFoundException(DimType.class,dimTypeCode);
    }
}
