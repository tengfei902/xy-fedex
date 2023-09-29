package com.xy.fedex.catalog.api;

import com.xy.fedex.catalog.api.dto.request.GetAppRequest;
import com.xy.fedex.catalog.api.dto.request.SaveAppRequest;
import com.xy.fedex.catalog.common.definition.AppDefinition;
import com.xy.fedex.def.Response;
import org.junit.Test;

import java.util.Arrays;

public class AppFacadeApiTest {

    private AppFacade appFacade;

    @Test
    public void testSaveApp() {
        SaveAppRequest saveAppRequest = new SaveAppRequest();
        saveAppRequest.setAppName("test_app");
        saveAppRequest.setAppComment("test app model");
        saveAppRequest.addMetric("sales_cnt","sales cnt");
        saveAppRequest.addDim("sales_amt","sales amount");
        saveAppRequest.setRelateModels(Arrays.asList("dsn1.sdafggg","dsn2.dsagsadsa"));
        appFacade.saveApp(saveAppRequest);
    }

    @Test
    public void testGetApp() {
        Response<AppDefinition> response = appFacade.getApp(GetAppRequest.builder().appName("test_app").build());
        AppDefinition appDefinition = response.getData();
    }
}
