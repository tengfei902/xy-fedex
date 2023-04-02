package com.xy.fedex.facade.clients;

import com.xy.fedex.catalog.api.CatalogAppFacade;
import com.xy.fedex.catalog.api.CatalogMetaFacade;
import com.xy.fedex.catalog.api.CatalogModelFacade;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.stereotype.Component;

@Component
public class DubboClientHolder {
    @DubboReference(version = "1.0.0")
    private CatalogAppFacade catalogAppFacade;
    @DubboReference(version = "1.0.0")
    private CatalogMetaFacade catalogMetaFacade;
    @DubboReference(version = "1.0.0")
    private CatalogModelFacade catalogModelFacade;
}
