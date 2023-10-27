package com.xy.fedex.admin.api.vo.response;

import lombok.Data;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

@Data
public class DsnDetailVO implements Serializable {
    private Long dsnId;
    private String dsnName;
    private String dbType;
    private List<Map<String,String>> owners;
    private List<Map<String,String>> viewers;
}
