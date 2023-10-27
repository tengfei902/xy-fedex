package com.xy.fedex.admin.api.vo.request;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class CreateDsnRequest implements Serializable {
    private String dsn;
    private List<String> owners;
    private List<String> viewers;
}
