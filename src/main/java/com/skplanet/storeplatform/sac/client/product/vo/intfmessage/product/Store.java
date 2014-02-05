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
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.Date;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.Identifier;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.Price;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.Source;

/**
 * Interface Message Rights.Store Value Object.
 * 
 * Updated on : 2013. 12. 17. Updated by : 이태희, SK 플래닛.
 */
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class Store extends CommonInfo implements Serializable {
	private static final long serialVersionUID = 1L;

	private Identifier identifier; // 상품의 에피소드 ID
	// private String support; // Product의 support와 동일 ("play"와 "store" 두 개의 상품으로 나눠질 경우 각각 상품의 지원하는 항목을 정의하기 위해 사용)
	private Support support; // 지원매체
	private Price price; // Rights 가격과 다를 경우 정의한다.
	private Date date; // Rights 이용기간과 다운로드 이용기간이 다를 경우만 정의한다.
	private String durationUsagePeriodUI; // 이용기간의 UI 표시
	private String networkRestrict; // 네트워크 제한 구분 (ota : 무선망)
	private Source source; // store의 source
	private List<Support> supportList; // 지원매체 리스트

	/**
	 * Store().
	 */
	public Store() {
	}

	/**
	 * 
	 * @param support
	 *            support
	 * @param price
	 *            price
	 * @param date
	 *            date
	 */
	public Store(Support support, Price price, Date date) {
		super();
		this.support = support;
		this.price = price;
		this.date = date;
	}

	/**
	 * @return Identifier
	 */
	public Identifier getIdentifier() {
		return this.identifier;
	}

	/**
	 * @param identifier
	 *            identifier
	 */
	public void setIdentifier(Identifier identifier) {
		this.identifier = identifier;
	}

	/**
	 * @return String
	 */
	public Support getSupport() {
		return this.support;
	}

	/**
	 * @param support
	 *            support
	 */
	public void setSupport(Support support) {
		this.support = support;
	}

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
	 * @return Date
	 */
	public Date getDate() {
		return this.date;
	}

	/**
	 * @param date
	 *            date
	 */
	public void setDate(Date date) {
		this.date = date;
	}

	/**
	 * @return String
	 */
	public String getDurationUsagePeriodUI() {
		return this.durationUsagePeriodUI;
	}

	/**
	 * @param durationUsagePeriodUI
	 *            durationUsagePeriodUI
	 */
	public void setDurationUsagePeriodUI(String durationUsagePeriodUI) {
		this.durationUsagePeriodUI = durationUsagePeriodUI;
	}

	/**
	 * @return String
	 */
	public String getNetworkRestrict() {
		return this.networkRestrict;
	}

	/**
	 * @param networkRestrict
	 *            networkRestrict
	 */
	public void setNetworkRestrict(String networkRestrict) {
		this.networkRestrict = networkRestrict;
	}

	/**
	 * @return Source
	 */
	public Source getSource() {
		return this.source;
	}

	/**
	 * @param source
	 *            source
	 */
	public void setSource(Source source) {
		this.source = source;
	}

	/**
	 * @return the supportList
	 */
	public List<Support> getSupportList() {
		return this.supportList;
	}

	/**
	 * @param supportList
	 *            the supportList to set
	 */
	public void setSupportList(List<Support> supportList) {
		this.supportList = supportList;
	}
}
