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
import org.hibernate.validator.constraints.NotBlank;

/**
 * Created by 양해엽 on 2015-01-13.
 */
public class BannerInfoSacReq extends CommonInfo {

    private static final long serialVersionUID = 2919760197160082862L;

    @NotBlank
    private String tenantId;

    @NotBlank
    private String bnrMenuId;

    @NotBlank
    private String bnrExpoMenuId;

    @NotBlank
    private String imgSizeCd;

    private Integer count;

    public String getTenantId() {
        return tenantId;
    }

    public void setTenantId(String tenantId) {
        this.tenantId = tenantId;
    }

    public String getBnrMenuId() {
        return bnrMenuId;
    }

    public void setBnrMenuId(String bnrMenuId) {
        this.bnrMenuId = bnrMenuId;
    }

    public String getBnrExpoMenuId() {
        return bnrExpoMenuId;
    }

    public void setBnrExpoMenuId(String bnrExpoMenuId) {
        this.bnrExpoMenuId = bnrExpoMenuId;
    }

    public String getImgSizeCd() {
        return imgSizeCd;
    }

    public void setImgSizeCd(String imgSizeCd) {
        this.imgSizeCd = imgSizeCd;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }
}
