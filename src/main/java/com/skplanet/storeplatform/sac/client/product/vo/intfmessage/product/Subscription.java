/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product;

import java.io.Serializable;

import com.skplanet.storeplatform.framework.core.annotation.ProtobufMapping;
import com.skplanet.storeplatform.framework.core.common.vo.CommonVO;
import com.skplanet.storeplatform.sac.client.intfmessage.product.vo.SubscriptionProto;

/**
 * Interface Message Subscription Value Object.
 * 
 * Updated on : 2013. 12. 17. Updated by : 이태희, SK 플래닛.
 */
@ProtobufMapping(SubscriptionProto.Subscription.class)
public class Subscription extends CommonVO implements Serializable {
	private static final long serialVersionUID = 1L;

	/*
	 * 기간 타입 ( monthly : 월간, weekly : 주간, biweekly : 격주간)
	 */
	private String durationType;
	private SubscriptionFee subscriptionFee; // 구독료 정보
	private FreeIssuedBookInfo freeIssuedBookInfo; // 무료 발행호 정보

	public String getDurationType() {
		return this.durationType;
	}

	public void setDurationType(String durationType) {
		this.durationType = durationType;
	}

	public SubscriptionFee getSubscriptionFee() {
		return this.subscriptionFee;
	}

	public void setSubscriptionFee(SubscriptionFee subscriptionFee) {
		this.subscriptionFee = subscriptionFee;
	}

	public FreeIssuedBookInfo getFreeIssuedBookInfo() {
		return this.freeIssuedBookInfo;
	}

	public void setFreeIssuedBookInfo(FreeIssuedBookInfo freeIssuedBookInfo) {
		this.freeIssuedBookInfo = freeIssuedBookInfo;
	}
}
