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
import com.skplanet.storeplatform.sac.client.intfmessage.product.vo.RightsProto;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.DateVO;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.IdentifierVO;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.PriceVO;

/**
 * Interface Message Rights.Play Value Object.
 * 
 * Updated on : 2013. 12. 17. Updated by : 이태희, SK 플래닛.
 */
@ProtobufMapping(RightsProto.Rights.Play.class)
public class PlayVO extends CommonVO implements Serializable {
	private static final long serialVersionUID = 1L;

	private IdentifierVO identifier; // 상품의 에피소드 ID
	private String support; // Product의 support와 동일 ("play"와 "store" 두 개의 상품으로 나눠질 경우 각각 상품의 지원하는 항목을 정의하기 위해 사용)
	private PriceVO price; // Rights 가격과 다를 경우 정의한다.
	private DateVO date; // Rights 이용기간과 다운로드 이용기간이 다를 경우만 정의한다.
	private String durationUsagePeriodUI; // 이용기간의 UI 표시
	private String networkRestrict; // 네트워크 제한 구분 (ota : 무선망)

	public IdentifierVO getIdentifier() {
		return this.identifier;
	}

	public void setIdentifier(IdentifierVO identifier) {
		this.identifier = identifier;
	}

	public String getSupport() {
		return this.support;
	}

	public void setSupport(String support) {
		this.support = support;
	}

	public PriceVO getPrice() {
		return this.price;
	}

	public void setPrice(PriceVO price) {
		this.price = price;
	}

	public DateVO getDate() {
		return this.date;
	}

	public void setDate(DateVO date) {
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
