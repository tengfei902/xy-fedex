package com.xy.fedex.admin.api.vo.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ExecuteRequest implements Serializable {
    private String dsn;
    private String sql;
}
