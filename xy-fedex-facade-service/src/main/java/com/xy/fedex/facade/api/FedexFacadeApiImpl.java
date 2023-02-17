package com.xy.fedex.facade.api;

import com.alibaba.druid.sql.ast.SQLExpr;
import com.xy.fedex.facade.api.dto.FedexQueryRequest;
import com.xy.fedex.facade.api.dto.Response;
import com.xy.fedex.facade.service.FedexQueryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class FedexFacadeApiImpl implements FedexFacadeApi {
    @Autowired
    private FedexQueryService fedexQueryService;

    @Override
    public Response<List<Map<String, Object>>> select(String select) {
        SQLExpr sql = null;
        List<Map<String,Object>> result = fedexQueryService.query(null);
        return Response.success(result);
    }

    @Override
    public Response<List<Map<String, Object>>> select(FedexQueryRequest request) {
        return null;
    }
}
