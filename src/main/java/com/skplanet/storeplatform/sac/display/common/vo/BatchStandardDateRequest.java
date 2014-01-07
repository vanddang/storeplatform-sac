package com.skplanet.storeplatform.sac.display.common.vo;

/**
 * BatchStandardDateRequest VO
 * Updated on : 2014. 01. 07 Updated by : 정희원, SK 플래닛.
 */
public class BatchStandardDateRequest {
    private String tenantId;
    private String batchId;

    public BatchStandardDateRequest(String tenantId, String batchId) {
        this.tenantId = tenantId;
        this.batchId = batchId;
    }

    public String getTenantId() {
        return tenantId;
    }

    public String getBatchId() {
        return batchId;
    }

}
