package com.xy.fedex.facade.service.meta;

import com.xy.fedex.facade.BaseTest;
import com.xy.fedex.facade.clients.DubboClientHolder;
import com.xy.fedex.facade.service.meta.match.ModelMatchService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;

public class ModelMatchServiceTest extends BaseTest {
    @Autowired
    private ModelMatchService modelMatchService;
//    @Mock
//    private CatalogAppFacade catalogAppFacade;
//    @Mock
//    private CatalogMetaFacade catalogMetaFacade;
//    @Mock
//    private CatalogModelFacade catalogModelFacade;
    @Autowired
    private DubboClientHolder dubboClientHolder;

    @BeforeEach
    public void init() throws Exception {

    }

    @Test
    public void testGetMetaMatchedModels() {
    }

    /**
     * 验证基础指标
     */
    @Test
    public void testGetMatchedModelsForPrimaryMetric() {

    }
}
