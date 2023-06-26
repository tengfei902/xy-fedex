package com.xy.fedex.facade.clients;

import com.xy.fedex.catalog.api.CatalogFacade;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.stereotype.Component;

@Component
public class DubboClientHolder {
    @DubboReference(version = "1.0.0")
    private CatalogFacade catalogFacade;
}
