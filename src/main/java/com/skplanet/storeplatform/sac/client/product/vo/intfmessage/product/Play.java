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

import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.Date;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.Identifier;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.Price;

/**
 * Interface Message Rights.Play Value Object.
 * 
 * Updated on : 2013. 12. 17. Updated by : 이태희, SK 플래닛.
 */
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class Play extends CommonInfo implements Serializable {
	private static final long serialVersionUID = 1L;

	private Identifier identifier; // 상품의 에피소드 ID
	private String support; // Product의 support와 동일 ("play"와 "store" 두 개의 상품으로 나눠질 경우 각각 상품의 지원하는 항목을 정의하기 위해 사용)
	private Price price; // Rights 가격과 다를 경우 정의한다.
	private Date date; // Rights 이용기간과 다운로드 이용기간이 다를 경우만 정의한다.
	private String durationUsagePeriodUI; // 이용기간의 UI 표시
	private String networkRestrict; // 네트워크 제한 구분 (ota : 무선망)

	public Identifier getIdentifier() {
		return this.identifier;
	}

	public void setIdentifier(Identifier identifier) {
		this.identifier = identifier;
	}

	public String getSupport() {
		return this.support;
	}

	public void setSupport(String support) {
		this.support = support;
	}

	public Price getPrice() {
		return this.price;
	}

	public void setPrice(Price price) {
		this.price = price;
	}

	public Date getDate() {
		return this.date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getDurationUsagePeriodUI() {
		return this.durationUsagePeriodUI;
	}

	public void setDurationUsagePeriodUI(String durationUsagePeriodUI) {
		this.durationUsagePeriodUI = durationUsagePeriodUI;
	}

	public String getNetworkRestrict() {
		return this.networkRestrict;
	}

	public void setNetworkRestrict(String networkRestrict) {
		this.networkRestrict = networkRestrict;
	}
}
