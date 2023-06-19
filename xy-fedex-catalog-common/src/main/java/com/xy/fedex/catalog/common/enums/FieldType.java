package com.xy.fedex.catalog.common.enums;

public enum FieldType {
    METRIC(0),DIM(1);

    int fieldType;

    FieldType(int fieldType) {
        this.fieldType = fieldType;
    }

    public int getFieldType() {
        return fieldType;
    }
}
