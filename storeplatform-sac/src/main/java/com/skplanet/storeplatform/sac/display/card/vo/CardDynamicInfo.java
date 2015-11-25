/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.display.card.vo;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;

/**
 * <p>
 * CardDynamicInfo
 * </p>
 * Updated on : 2014. 12. 01 Updated by : 정희원, SK 플래닛.
 */
public class CardDynamicInfo extends CommonInfo {
    private static final long serialVersionUID = 1L;

    private String cardId;
    private String likeYn = "N";
    private Integer cntLike;
    private Integer cntShar;


    public String getCardId() {
		return cardId;
	}

	public void setCardId(String cardId) {
		this.cardId = cardId;
	}

	public String getLikeYn() {
        return likeYn;
    }

    public void setLikeYn(String likeYn) {
        this.likeYn = likeYn;
    }

    public Integer getCntLike() {
        return cntLike;
    }

    public void setCntLike(Integer cntLike) {
        this.cntLike = cntLike;
    }

    public Integer getCntShar() {
        return cntShar;
    }

    public void setCntShar(Integer cntShar) {
        this.cntShar = cntShar;
    }
}
