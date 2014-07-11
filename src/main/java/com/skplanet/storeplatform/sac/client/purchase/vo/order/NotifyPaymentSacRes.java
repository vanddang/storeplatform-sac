/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.client.purchase.vo.order;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;

/**
 * 
 * 결제 결과 Noti 응답 VO
 * 
 * Updated on : 2014. 2. 28. Updated by : 이승택, nTels.
 */
public class NotifyPaymentSacRes extends CommonInfo {
	private static final long serialVersionUID = 201402281L;

	private String prchsId; // 구매 ID
	private int count; // 처리된 결제수단 갯수
	private String userKey; // 상품 보유자 회원 Key
	private String deviceKey; // 상품 보유자 디바이스 Key
	private String prchsDt; // 구매일시
	private String type; // 구매완료 Noti 타입: 01-일반결제
	private String publishType; // 쇼핑쿠폰 발행 처리 타입 : 01-동기 (또는 쇼핑 이외 상품), 02-비동기

	/**
	 */
	public NotifyPaymentSacRes() {
	}

	/**
	 * @param prchsId
	 *            구매 ID
	 * @param count
	 *            처리된 결제수단 갯수
	 */
	public NotifyPaymentSacRes(String prchsId, int count) {
		this.prchsId = prchsId;
		this.count = count;
	}

	/**
	 * @return the prchsId
	 */
	public String getPrchsId() {
		return this.prchsId;
	}

	/**
	 * @param prchsId
	 *            the prchsId to set
	 */
	public void setPrchsId(String prchsId) {
		this.prchsId = prchsId;
	}

	/**
	 * @return the count
	 */
	public int getCount() {
		return this.count;
	}

	/**
	 * @param count
	 *            the count to set
	 */
	public void setCount(int count) {
		this.count = count;
	}

	/**
	 * @return the userKey
	 */
	public String getUserKey() {
		return this.userKey;
	}

	/**
	 * @param userKey
	 *            the userKey to set
	 */
	public void setUserKey(String userKey) {
		this.userKey = userKey;
	}

	/**
	 * @return the deviceKey
	 */
	public String getDeviceKey() {
		return this.deviceKey;
	}

	/**
	 * @param deviceKey
	 *            the deviceKey to set
	 */
	public void setDeviceKey(String deviceKey) {
		this.deviceKey = deviceKey;
	}

	/**
	 * @return the prchsDt
	 */
	public String getPrchsDt() {
		return this.prchsDt;
	}

	/**
	 * @param prchsDt
	 *            the prchsDt to set
	 */
	public void setPrchsDt(String prchsDt) {
		this.prchsDt = prchsDt;
	}

	/**
	 * @return the type
	 */
	public String getType() {
		return this.type;
	}

	/**
	 * @param type
	 *            the type to set
	 */
	public void setType(String type) {
		this.type = type;
	}

	/**
	 * @return the publishType
	 */
	public String getPublishType() {
		return this.publishType;
	}

	/**
	 * @param publishType
	 *            the publishType to set
	 */
	public void setPublishType(String publishType) {
		this.publishType = publishType;
	}

}
