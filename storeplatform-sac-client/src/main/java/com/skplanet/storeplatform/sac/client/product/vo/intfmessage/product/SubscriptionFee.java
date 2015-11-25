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

import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.Price;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.Time;

/**
 * Interface Message Subscription.SubscriptionFee Value Object.
 * 
 * Updated on : 2013. 12. 17. Updated by : 이태희, SK 플래닛.
 */
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class SubscriptionFee extends CommonInfo implements Serializable {
	private static final long serialVersionUID = 1L;

	private Price price; // 가격
	/*
	 * 구독 기간 (duration : 정기 구독기간, additional : 추가 기간)
	 */
	private List<Time> time;

	/**
	 * @return Price
	 */
	public Price getPrice() {
		return this.price;
	}

	/**
	 * @param price
	 *            price
	 */
	public void setPrice(Price price) {
		this.price = price;
	}

	/**
	 * @return List<Time>
	 */
	public List<Time> getTime() {
		return this.time;
	}

	/**
	 * @param time
	 *            time
	 */
	public void setTime(List<Time> time) {
		this.time = time;
	}

}
