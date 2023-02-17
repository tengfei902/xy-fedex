package com.xy.fedex.admin.api;

import com.xy.fedex.admin.api.vo.request.CreateDsnRequest;
import com.xy.fedex.admin.api.vo.response.DataResponse;
import com.xy.fedex.admin.api.vo.response.DsnBriefVO;
import com.xy.fedex.admin.api.vo.response.DsnDetailVO;

import java.util.List;

public interface FedexDsnFacade {

    /**
     * add
     * @param createDsnRequest
     * @return
     */
    DataResponse<DsnDetailVO> createDsn(CreateDsnRequest createDsnRequest);

    /**
     * remove
     */
    DataResponse<Boolean> deleteDsn(String accountId, Long dsnId);

    /**
     * get single dsn detail
     * @param accountId
     * @param dsnId
     * @return
     */
    DataResponse<DsnDetailVO> getDsn(String accountId, Long dsnId);

    DataResponse<List<DsnBriefVO>> getDsnList(Long accountId);
}
