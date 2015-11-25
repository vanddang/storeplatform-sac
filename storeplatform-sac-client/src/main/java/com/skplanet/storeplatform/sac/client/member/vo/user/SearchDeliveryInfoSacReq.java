package com.skplanet.storeplatform.sac.client.member.vo.user;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;

/**
 * [REQUEST] 배송지 정보 조회
 * 
 * Updated on : 2015. 10. 01. Updated by : 최진호, Bogogt.
 */
public class SearchDeliveryInfoSacReq extends CommonInfo {

	private static final long serialVersionUID = 1L;

	/** 사용자 키 */
	private String userKey;

	/** 배송지 타입 */
	private String deliveryTypeCd;

	/**
	 * 사용자 키를 리턴한다.
	 * 
	 * @return userKey - 사용자 키
	 */
	public String getUserKey() {
		return this.userKey;
	}

	/**
	 * 사용자 키를 셋팅한다.
	 * 
	 * @param userKey
	 *            사용자 키
	 */
	public void setUserKey(String userKey) {
		this.userKey = userKey;
	}

	/**
	 * 배송지 타입을(를) 리턴한다.
	 * 
	 * @return deliveryTypeCd - 배송지 타입
	 */
	public String getDeliveryTypeCd() {
		return this.deliveryTypeCd;
	}

	/**
	 * 배송지 타입을(를) 셋팅한다.
	 * 
	 * @param deliveryTypeCd
	 *            배송지 타입
	 */
	public void setDeliveryTypeCd(String deliveryTypeCd) {
		this.deliveryTypeCd = deliveryTypeCd;
	}

	/**
	 * @return the serialversionuid
	 */
	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
