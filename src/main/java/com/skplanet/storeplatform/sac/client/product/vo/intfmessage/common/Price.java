/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common;

import java.io.Serializable;

import com.skplanet.storeplatform.framework.core.annotation.ProtobufMapping;
import com.skplanet.storeplatform.framework.core.common.vo.CommonVO;
import com.skplanet.storeplatform.sac.client.intfmessage.common.vo.PriceProto;

/**
 * Interface Message Price Value Object.
 * 
 * Updated on : 2013. 12. 17. Updated by : 이태희, SK 플래닛.
 */
@ProtobufMapping(PriceProto.Price.class)
public class Price extends CommonVO implements Serializable {
	private static final long serialVersionUID = 1L;

	private String name; // price.text가 여러 개를 정의하는 경우 세부 규격에서 정의하여 사용
	/*
	 * 가격단위 ( KRW : 대한민국 - 원 (default), USD : 미국 - 달러, JPY : 일본 - 엔, CNY : 중화인민공화국 - 런민비 (위안), POINT : point)
	 */
	private String unit;
	private String fixedPrice;// 정가
	private String discountRate;// 할인률 (%생략)
	private String discountPrice;// 할인가
	private int text;// 가격

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUnit() {
		return this.unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public String getFixedPrice() {
		return this.fixedPrice;
	}

	public void setFixedPrice(String fixedPrice) {
		this.fixedPrice = fixedPrice;
	}

	public String getDiscountRate() {
		return this.discountRate;
	}

	public void setDiscountRate(String discountRate) {
		this.discountRate = discountRate;
	}

	public String getDiscountPrice() {
		return this.discountPrice;
	}

	public void setDiscountPrice(String discountPrice) {
		this.discountPrice = discountPrice;
	}

	public int getText() {
		return this.text;
	}

	public void setText(int text) {
		this.text = text;
	}
}
