package com.xy.fedex.admin.service.dsn;

import com.xy.fedex.admin.service.dsn.model.bo.DsnDTO;
import com.xy.infrustruction.definition.connection.ConnectInfo;

public interface DsnService {

    DsnDTO createDsn(DsnDTO dsn);

    void checkDsn(ConnectInfo connectInfo);

    DsnDTO getDsn(String dsn);

    DsnDTO getDsn(Long dsnId);

    void deleteDsn(Long dsnId);
}
