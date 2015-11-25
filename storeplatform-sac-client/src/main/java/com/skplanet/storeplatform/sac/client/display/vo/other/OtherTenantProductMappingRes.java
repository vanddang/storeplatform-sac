/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.client.display.vo.other;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;

import java.util.List;

/**
 * <p>
 * OtherTenantProductMappingRes
 * </p>
 * Updated on : 2015. 01. 19 Updated by : 정희원, SK 플래닛.
 */
public class OtherTenantProductMappingRes extends CommonInfo {
    private static final long serialVersionUID = 1L;

    private List<TenantProductMapping> tenantProductMappingList;

    public List<TenantProductMapping> getTenantProductMappingList() {
        return tenantProductMappingList;
    }

    public void setTenantProductMappingList(List<TenantProductMapping> tenantProductMappingList) {
        this.tenantProductMappingList = tenantProductMappingList;
    }

    public static class TenantProductMapping extends CommonInfo {
        private static final long serialVersionUID = 1L;

        private String tenantId;
        private String salesStatusCd;
        private String prodId;

        public TenantProductMapping() {}
        public TenantProductMapping(String tenantId, String salesStatusCd, String prodId) {
            this.tenantId = tenantId;
            this.salesStatusCd = salesStatusCd;
            this.prodId = prodId;
        }

        public String getTenantId() {
            return tenantId;
        }

        public void setTenantId(String tenantId) {
            this.tenantId = tenantId;
        }

        public String getSalesStatusCd() {
            return salesStatusCd;
        }

        public void setSalesStatusCd(String salesStatusCd) {
            this.salesStatusCd = salesStatusCd;
        }

        public String getProdId() {
            return prodId;
        }

        public void setProdId(String prodId) {
            this.prodId = prodId;
        }
    }
}
