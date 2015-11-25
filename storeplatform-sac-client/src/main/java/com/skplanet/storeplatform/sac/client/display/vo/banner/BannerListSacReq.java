/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */

package com.skplanet.storeplatform.sac.client.display.vo.banner;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;
import org.hibernate.validator.constraints.NotBlank;

/**
 * Updated on : 2015-06-30. Updated by : 양해엽, SK Planet.
 */
public class BannerListSacReq extends CommonInfo {
    private static final long serialVersionUID = 1L;

    @NotBlank
    private String bnrMenuId; // 배너 메뉴 ID

    @NotBlank
    private String bnrExpoMenuId; // 배너 노출 메뉴 ID

    @NotBlank
    private String imgSizeCd; // 배너 이미지 코드

    private String startKey;
    private Integer count;
	private String useGrdCd = "PD004403"; // 15세이용가

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

    public String getStartKey() {
        return startKey;
    }

    public void setStartKey(String startKey) {
        this.startKey = startKey;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

	public String getUseGrdCd() {
		return useGrdCd;
	}

	public void setUseGrdCd(String useGrdCd) {
		this.useGrdCd = useGrdCd;
	}
}
