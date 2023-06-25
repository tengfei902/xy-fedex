package com.xy.fedex.catalog.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Builder
@Data
public class DimModelRequest {
    private List<Long> modelIds;
    private Long dimId;
}
