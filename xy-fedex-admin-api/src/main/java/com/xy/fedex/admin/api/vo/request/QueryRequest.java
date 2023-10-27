package com.xy.fedex.admin.api.vo.request;

import lombok.Data;

import java.util.Map;

@Data
public class QueryRequest extends UpsertRequest {
//    private QueryCompare compare;
    private boolean needTotalHit;

    public QueryRequest(String dsn,String sql) {
        super(dsn,sql);
    }

    public static Builder build(String dsn,String sql) {
        return new Builder(dsn,sql);
    }

    public static class Builder extends UpsertRequest.Builder {
//        private QueryCompare compare;
        private boolean needTotalHit;

        public Builder(String dsn,String sql) {
            super(dsn, sql);
        }

        public Builder params(Map<String,Object> params) {
            this.params = params;
            return this;
        }

//        public Builder compare(QueryCompare compare) {
//            this.compare = compare;
//            return this;
//        }

        public Builder needTotalHit(boolean needTotalHit) {
            this.needTotalHit = needTotalHit;
            return this;
        }

        public QueryRequest build() {
            QueryRequest queryRequest = new QueryRequest(super.dsn,super.sql);
            queryRequest.setParams(super.params);
//            queryRequest.setCompare(this.compare);
            queryRequest.setNeedTotalHit(this.needTotalHit);
            return queryRequest;
        }
    }
}
