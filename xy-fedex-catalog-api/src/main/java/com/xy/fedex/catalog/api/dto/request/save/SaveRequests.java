package com.xy.fedex.catalog.api.dto.request.save;

import com.xy.fedex.catalog.api.dto.request.save.field.metric.SaveDimRequest;
import com.xy.fedex.catalog.api.dto.request.save.field.metric.SaveMetricRequest;

/**
 * @author tengfei
 */
public class SaveRequests {

    public static SaveMetricRequestBuilder newSaveMetricRequest(Long bizLineId,String metricCode) {
        return new SaveMetricRequestBuilder(bizLineId, metricCode);
    }

    public static SaveDimRequestBuilder newSaveDimRequest(Long bizLineId,String dimCode) {
        return new SaveDimRequestBuilder(bizLineId, dimCode);
    }

    public static class SaveMetricRequestBuilder {
        private Long bizLineId;
        private Long subjectId;
        private String metricCode;
        private String metricName;
        private String metricComment;

        private SaveMetricRequestBuilder() {}

        public SaveMetricRequestBuilder(Long bizLineId,String metricCode) {
            this.metricCode = metricCode;
            this.bizLineId = bizLineId;
        }

        public SaveMetricRequestBuilder subjectId(Long subjectId) {
            this.subjectId = subjectId;
            return this;
        }

        public SaveMetricRequestBuilder metricName(String metricName) {
            this.metricName = metricName;
            return this;
        }

        public SaveMetricRequestBuilder metricComment(String metricComment) {
            this.metricComment = metricComment;
            return this;
        }

        public SaveMetricRequest build() {
            SaveMetricRequest saveMetricRequest = new SaveMetricRequest(this.bizLineId,this.metricCode);
            saveMetricRequest.setSubjectId(this.subjectId);
            saveMetricRequest.setMetricName(this.metricName);
            saveMetricRequest.setMetricComment(this.metricComment);
            return saveMetricRequest;
        }
    }

    public static class SaveDimRequestBuilder {
        private Long bizLineId;
        private String dimCode;
        private String dimName;
        private String dimComment;
        private Long dimFamilyId;
        private boolean master;

        public SaveDimRequestBuilder(Long bizLineId,String dimCode) {
            this.bizLineId = bizLineId;
            this.dimCode = dimCode;
        }

        public SaveDimRequestBuilder dimName(String dimName) {
            this.dimName = dimName;
            return this;
        }

        public SaveDimRequestBuilder dimComment(String dimComment) {
            this.dimComment = dimComment;
            return this;
        }

        public SaveDimRequestBuilder dimFamily(Long dimFamilyId,boolean master) {
            this.dimFamilyId = dimFamilyId;
            this.master = master;
            return this;
        }

        public SaveDimRequest build() {
            SaveDimRequest saveDimRequest = new SaveDimRequest();
            saveDimRequest.setBizLineId(this.bizLineId);
            saveDimRequest.setDimCode(this.dimCode);
            saveDimRequest.setDimName(this.dimName);
            saveDimRequest.setDimComment(this.dimComment);
            saveDimRequest.setDimFamilyId(this.dimFamilyId);
            saveDimRequest.setMaster(this.master);
            return saveDimRequest;
        }
    }
}
