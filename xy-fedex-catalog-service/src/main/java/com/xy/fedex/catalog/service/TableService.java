package com.xy.fedex.catalog.service;

import com.xy.fedex.catalog.dto.TableAliasDTO;

import java.util.List;

public interface TableService {
    List<TableAliasDTO> getTables(Long dsnId, String tableSource);
}
