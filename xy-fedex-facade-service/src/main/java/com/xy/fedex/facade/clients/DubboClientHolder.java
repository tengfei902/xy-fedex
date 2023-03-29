package com.xy.fedex.facade.clients;

import com.xy.fedex.catalog.api.CatalogAppFacade;
import com.xy.fedex.catalog.api.CatalogMetaFacade;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.stereotype.Component;

@Component
public class DubboClientHolder {
    @DubboReference(version = "1.0.0")
    private CatalogAppFacade catalogAppFacade;
    @DubboReference
    private CatalogMetaFacade catalogMetaFacade;
}
