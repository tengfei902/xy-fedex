package com.xy.fedex.facade.api.request;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class OrthogonalRequest implements Serializable {
    private String app;
    private List<String> metrics;
    private List<String> dims;
}
