package com.xy.fedex.catalog.service;

import com.google.gson.Gson;
import com.xy.fedex.catalog.BaseTest;
import com.xy.fedex.catalog.api.dto.request.SaveAppRequest;
import com.xy.fedex.catalog.common.definition.AppDefinition;
import com.xy.fedex.catalog.service.meta.AppService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;

public class AppServiceTest extends BaseTest {
    @Autowired
    private AppService appService;

    @Transactional
    @Rollback(value = false)
    @Test
    public void testSaveApp() {
        SaveAppRequest saveAppRequest = new SaveAppRequest();
        saveAppRequest.setAppName("hf");
        saveAppRequest.setAppComment("hf");
        saveAppRequest.setBizLineId(0L);
        saveAppRequest.setRelateModelIds(Arrays.asList(10L,11L));
        Long appId = appService.saveApp(saveAppRequest);

        AppDefinition appDefinition = appService.getApp(0L,appId);
        System.out.println(new Gson().toJson(appDefinition));
    }
}
