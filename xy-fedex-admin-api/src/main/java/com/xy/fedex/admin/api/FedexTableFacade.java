package com.xy.fedex.admin.api;

import com.xy.fedex.admin.api.vo.request.TableRequest;
import com.xy.fedex.admin.api.vo.response.TableDetailVO;

public interface FedexTableFacade {
    TableDetailVO getTable(TableRequest tableRequest);
}
