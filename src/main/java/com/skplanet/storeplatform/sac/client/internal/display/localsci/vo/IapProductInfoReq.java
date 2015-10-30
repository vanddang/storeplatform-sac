/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.client.internal.display.localsci.vo;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;

/**
 * <p>
 * IapProductInfoReq
 * </p>
 * Updated on : 2014. 04. 16 Updated by : 정희원, SK 플래닛.
 */
public class IapProductInfoReq extends CommonInfo {
    private static final long serialVersionUID = 1L;
    private static final String TENANT_TSTORE = "S01";

    @NotNull @NotEmpty
    private String partProdId;

    private String tenantId;

    public IapProductInfoReq() {}

    public IapProductInfoReq(String partProdId) {
        this.partProdId = partProdId;
        this.tenantId = TENANT_TSTORE;
    }

    public IapProductInfoReq(String partProdId, String tenantId) {
        this.partProdId = partProdId;
        this.tenantId = tenantId;
    }

    public String getPartProdId() {
        return partProdId;
    }

    public void setPartProdId(String partProdId) {
        this.partProdId = partProdId;
    }

    public String getTenantId() {
        return tenantId;
    }

    public void setTenantId(String tenantId) {
        this.tenantId = tenantId;
    }
}
