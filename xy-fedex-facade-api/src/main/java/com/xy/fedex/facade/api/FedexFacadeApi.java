package com.xy.fedex.facade.api;

import com.xy.fedex.facade.api.dto.FedexQueryRequest;
import com.xy.fedex.facade.api.dto.Response;

import java.util.List;
import java.util.Map;

/**
 * 查询服务
 */
public interface FedexFacadeApi {
    Response<List<Map<String, Object>>> select(String select);

    Response<List<Map<String,Object>>> select(FedexQueryRequest request);
}
