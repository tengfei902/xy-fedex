package com.xy.fedex.catalog.service.meta;

import com.xy.fedex.catalog.common.definition.column.TableField;
import com.xy.fedex.catalog.common.definition.field.MetaField;
import com.xy.fedex.catalog.common.definition.table.TableRelation;
import com.xy.fedex.catalog.dto.TableAliasDTO;
import org.apache.commons.lang3.tuple.Pair;

import java.util.List;

public interface TableService {
    TableRelation getTables(Long dsnId, String tableSource);

    List<TableField> getTableFields(Long bizLineId, Long dsnId, TableRelation tableRelation, List<TableField> existedFields);
}
