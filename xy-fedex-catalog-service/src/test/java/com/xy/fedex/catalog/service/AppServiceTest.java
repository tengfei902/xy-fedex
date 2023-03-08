package com.xy.fedex.catalog.service;

import com.xy.fedex.catalog.BaseTest;
import com.xy.fedex.catalog.api.dto.request.SaveAppRequest;
import com.xy.fedex.catalog.service.meta.AppService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Arrays;

public class AppServiceTest extends BaseTest {
    @Autowired
    private AppService appService;

    @Test
    public void testSaveApp() {
        SaveAppRequest saveAppRequest = new SaveAppRequest();
        saveAppRequest.setAppName("交易");
        saveAppRequest.setAppDesc("交易");
        saveAppRequest.setBizLineId(0L);
        saveAppRequest.setTenantId(0L);
        saveAppRequest.setRelatedModelIds(Arrays.asList(30L));
        appService.saveApp(saveAppRequest);
    }
}
