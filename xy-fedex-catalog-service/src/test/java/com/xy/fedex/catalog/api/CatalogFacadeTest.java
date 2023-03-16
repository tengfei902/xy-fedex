package com.xy.fedex.catalog.api;

import com.xy.fedex.catalog.BaseTest;
import com.xy.fedex.catalog.api.dto.ModelRequest;
import com.xy.fedex.catalog.api.dto.request.PrepareModelRequest;
import com.xy.fedex.catalog.api.dto.response.PrepareModelResponse;
import com.xy.fedex.def.Response;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class CatalogFacadeTest extends BaseTest {
    @Autowired
    private CatalogFacade catalogFacade;

    @Test
    public void testSaveModel() {
    }
}
