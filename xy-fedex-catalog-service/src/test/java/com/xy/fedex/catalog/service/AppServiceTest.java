package com.xy.fedex.catalog.service;

import com.xy.fedex.catalog.BaseTest;
import com.xy.fedex.catalog.api.dto.AppRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Arrays;

public class AppServiceTest extends BaseTest {
    @Autowired
    private AppService appService;

    @Test
    public void testSaveApp() {
        AppRequest appRequest = new AppRequest();
        appRequest.setAppName("交易");
        appRequest.setAppDesc("交易");
        appRequest.setBizLineId(0L);
        appRequest.setTenantId(0L);
        appRequest.setRelatedModelIds(Arrays.asList(30L));
        appService.saveApp(appRequest);
    }
}
