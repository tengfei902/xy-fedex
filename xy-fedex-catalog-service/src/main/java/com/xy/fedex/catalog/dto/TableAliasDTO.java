package com.xy.fedex.catalog.dto;

import com.xy.fedex.admin.api.vo.response.TableDetailVO;
import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class TableAliasDTO {
    private String alias;
    private TableDetailVO table;
}
