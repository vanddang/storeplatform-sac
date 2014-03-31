/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.display.common.vo;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;

/**
 * <p>
 * DESC
 * </p>
 * Updated on : 2014. 03. 31 Updated by : 정희원, SK 플래닛.
 */
public class TmembershipDcInfo extends CommonInfo {
    private static final long serialVersionUID = 1L;

    private String tenantProdGrpCd;
    private String prodTp;
    private String prodTpNm;
    private Integer dcRate;

    public String getTenantProdGrpCd() {
        return tenantProdGrpCd;
    }

    public void setTenantProdGrpCd(String tenantProdGrpCd) {
        this.tenantProdGrpCd = tenantProdGrpCd;
    }

    public String getProdTp() {
        return prodTp;
    }

    public void setProdTp(String prodTp) {
        this.prodTp = prodTp;
    }

    public String getProdTpNm() {
        return prodTpNm;
    }

    public void setProdTpNm(String prodTpNm) {
        this.prodTpNm = prodTpNm;
    }

    public Integer getDcRate() {
        return dcRate;
    }

    public void setDcRate(Integer dcRate) {
        this.dcRate = dcRate;
    }
}
