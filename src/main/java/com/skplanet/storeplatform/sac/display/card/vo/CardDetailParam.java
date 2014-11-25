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
 * Class 설명
 *
 * Updated on : 2014. 10. 8.
 * Updated by : 양해엽, SK 플래닛.
 */
public class CardDetailParam extends CommonInfo {

	private static final long serialVersionUID = -5534315620656805065L;

	private String tenantId;
	private String cardId;
	private String userKey;

    public CardDetailParam() {}

    public CardDetailParam(String tenantId, String cardId, String userKey) {
        this.tenantId = tenantId;
        this.cardId = cardId;
        this.userKey = userKey;
    }

	public String getTenantId() {
		return tenantId;
	}

	public void setTenantId(String tenantId) {
		this.tenantId = tenantId;
	}

	public String getCardId() {
		return cardId;
	}

	public void setCardId(String cardId) {
		this.cardId = cardId;
	}

	public String getUserKey() {
		return userKey;
	}

	public void setUserKey(String userKey) {
		this.userKey = userKey;
	}

}
