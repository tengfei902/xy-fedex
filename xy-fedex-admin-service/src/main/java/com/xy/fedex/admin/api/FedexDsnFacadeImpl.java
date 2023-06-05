package com.xy.fedex.admin.api;

import com.xy.fedex.admin.api.vo.request.CreateDsnRequest;
import com.xy.fedex.admin.api.vo.response.DataResponse;
import com.xy.fedex.admin.api.vo.response.DsnBriefVO;
import com.xy.fedex.admin.api.vo.response.DsnDetailVO;
import com.xy.fedex.admin.service.dsn.DsnService;
import com.xy.fedex.admin.service.dsn.model.bo.DsnDTO;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@DubboService(version = "${dubbo.server.version}")
public class FedexDsnFacadeImpl implements FedexDsnFacade {
    @Autowired
    private DsnService dsnService;
    @Override
    public DataResponse<DsnDetailVO> createDsn(CreateDsnRequest createDsnRequest) {
        //创建dsn
        DsnDTO dsnDTO = DsnDTO.builder()
                .dsnName(createDsnRequest.getDsn())
                .dbType(null)
                .connectInfo(createDsnRequest.getConnectInfo())
                .creator("").build();
        dsnDTO = dsnService.createDsn(dsnDTO);
        //授权
        List<String> owners = createDsnRequest.getOwners();
        List<String> viewers = createDsnRequest.getViewers();

        DsnDetailVO dsnDetail = new DsnDetailVO();
        dsnDetail.setDsnId(dsnDTO.getDsnId());
        dsnDetail.setDsnName(dsnDTO.getDsnName());
        dsnDetail.setConnectInfo(dsnDTO.getConnectInfo());
        dsnDetail.setDbType(dsnDTO.getDbType().name());
        return DataResponse.success(dsnDetail);
    }

    @Override
    public DataResponse<Boolean> deleteDsn(String accountId, Long dsnId) {
        return null;
    }

    @Override
    public DataResponse<DsnDetailVO> getDsn(String accountId, Long dsnId) {
        return null;
    }

    @Override
    public DataResponse<List<DsnBriefVO>> getDsnList(Long accountId) {
        return null;
    }
}
