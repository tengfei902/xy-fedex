package com.xy.fedex.admin.enums;

import com.xy.fedex.admin.exception.DsnStatusNotFoundException;

import java.util.Objects;

public enum DsnStatus {
    NEW(0),VALID(1),DELETED(-1);

    private int code;

    DsnStatus(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }

    public static DsnStatus parse(Integer dsnStatus) {
        if(Objects.isNull(dsnStatus)) {
            throw new DsnStatusNotFoundException("DsnStatus empty");
        }
        for(DsnStatus status:DsnStatus.values()) {
            if(status.code == dsnStatus) {
                return status;
            }
        }
        throw new DsnStatusNotFoundException("DsnStatus not support:"+dsnStatus);
    }
}
