package com.xy.fedex.facade.service.meta;

import com.xy.fedex.facade.BaseTest;
import com.xy.fedex.facade.service.meta.match.ModelMatchService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class ModelMatchServiceTest extends BaseTest {
    @Autowired
    private ModelMatchService modelMatchService;

    @Test
    public void testGetMetaMatchedModels() {
        String sql = "";
        modelMatchService.getMetaMatchedModels(null);
    }
}
