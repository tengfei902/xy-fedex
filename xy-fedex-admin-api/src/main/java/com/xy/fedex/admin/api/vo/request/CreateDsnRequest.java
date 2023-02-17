package com.xy.fedex.admin.api.vo.request;

import com.xy.infrustruction.definition.DbType;
import com.xy.infrustruction.definition.connection.ConnectInfo;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class CreateDsnRequest implements Serializable {
    private String dsn;
    private DbType dbType;
    private ConnectInfo connectInfo;
    private List<String> owners;
    private List<String> viewers;
}
