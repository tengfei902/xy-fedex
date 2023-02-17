package com.xy.fedex.admin.api.vo.request;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@NoArgsConstructor
@Data
public class UpsertRequest extends ExecuteRequest {
    private Map<String,Object> params;

    public UpsertRequest(String dsn,String sql) {
        super(dsn,sql);
    }

    public static Builder build(String dsn,String sql) {
        return new Builder(dsn,sql);
    }

    public static class Builder {
        protected String dsn;
        protected String sql;
        protected Map<String,Object> params;

        public Builder(String dsn,String sql) {
            this.dsn = dsn;
            this.sql = sql;
        }

        public Builder params(Map<String,Object> params) {
            this.params = params;
            return this;
        }

        public UpsertRequest build() {
            UpsertRequest upsertRequest = new UpsertRequest(this.dsn,this.sql);
            upsertRequest.setParams(this.params);
            return upsertRequest;
        }
    }
}
