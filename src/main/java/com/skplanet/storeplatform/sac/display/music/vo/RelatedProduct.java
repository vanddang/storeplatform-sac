/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.display.music.vo;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;

/**
 * <p>
 * 관련 상품 정보
 * </p>
 * Updated on : 2014. 03. 13 Updated by : 정희원, SK 플래닛.
 */
public class RelatedProduct extends CommonInfo {
    private static final long serialVersionUID = 1L;

    private String prodId;
    private String metaClsfCd;

    public String getProdId() {
        return prodId;
    }

    public void setProdId(String prodId) {
        this.prodId = prodId;
    }

    public String getMetaClsfCd() {
        return metaClsfCd;
    }

    public void setMetaClsfCd(String metaClsfCd) {
        this.metaClsfCd = metaClsfCd;
    }
}
