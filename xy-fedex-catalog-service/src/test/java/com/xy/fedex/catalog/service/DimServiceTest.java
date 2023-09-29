package com.xy.fedex.catalog.service;

import com.google.gson.Gson;
import com.xy.fedex.catalog.BaseTest;
import com.xy.fedex.catalog.common.definition.field.Dim;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Arrays;
import java.util.List;

public class DimServiceTest extends BaseTest {
    @Autowired
    private DimService dimService;

    @Test
    public void testSaveDim() {
        Dim dim = new Dim();
        dim.setDimCode("dt");
        dim.setDimName("dt");
        dim.setDimComment("date time");
        dim.setDimType("date_format");

        dimService.saveDims(Arrays.asList(dim));

        List<Dim> dims = dimService.getDims(Arrays.asList("dt"));
        System.out.println(new Gson().toJson(dims));

        dim.setDimName("dt2");

        Dim dim2 = new Dim();
        dim2.setDimCode("hour");
        dimService.saveDims(Arrays.asList(dim,dim2));

        dims = dimService.getDims(Arrays.asList("dt","hour"));
        System.out.println(new Gson().toJson(dims));
    }
}
