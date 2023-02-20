package com.xy.fedex.catalog.enums;

public enum CatalogStatus {
    AVAILABLE(1),
    DELETED(-1);

    int status;

    CatalogStatus(int status) {
        this.status = status;
    }

    public int getStatus() {
        return status;
    }
}
