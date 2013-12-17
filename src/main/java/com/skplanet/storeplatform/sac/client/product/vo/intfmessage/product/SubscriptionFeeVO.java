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
import java.util.List;

import com.skplanet.storeplatform.framework.core.annotation.ProtobufMapping;
import com.skplanet.storeplatform.framework.core.common.vo.CommonVO;
import com.skplanet.storeplatform.sac.client.intfmessage.product.vo.SubscriptionProto;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.PriceVO;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.TimeVO;

/**
 * Interface Message Subscription.SubscriptionFee Value Object.
 * 
 * Updated on : 2013. 12. 17. Updated by : 이태희, SK 플래닛.
 */
@ProtobufMapping(SubscriptionProto.Subscription.SubscriptionFee.class)
public class SubscriptionFeeVO extends CommonVO implements Serializable {
	private static final long serialVersionUID = 1L;

	private PriceVO price; // 가격
	/*
	 * 구독 기간 (duration : 정기 구독기간, additional : 추가 기간)
	 */
	private List<TimeVO> time;

	public PriceVO getPrice() {
		return this.price;
	}

	public void setPrice(PriceVO price) {
		this.price = price;
	}

	public List<TimeVO> getTime() {
		return this.time;
	}

	public void setTime(List<TimeVO> time) {
		this.time = time;
	}
}
