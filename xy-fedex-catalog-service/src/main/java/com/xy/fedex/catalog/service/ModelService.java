package com.xy.fedex.catalog.service;

import com.xy.fedex.catalog.api.dto.ModelRequest;

public interface ModelService {

    void createModel(ModelRequest modelRequest);

    void createModel(String sql);

    void checkModelRequest(ModelRequest modelRequest);
}
