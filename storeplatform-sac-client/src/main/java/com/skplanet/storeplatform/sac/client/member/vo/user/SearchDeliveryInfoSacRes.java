package com.skplanet.storeplatform.sac.client.member.vo.user;

import java.util.List;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;
import com.skplanet.storeplatform.sac.client.member.vo.common.DeliveryInfo;

/**
 * [RESPONSE] 배송지 정보 조회
 * 
 * Updated on : 2015. 10. 01. Updated by : 최진호, Bogogt.
 */
public class SearchDeliveryInfoSacRes extends CommonInfo {

	private static final long serialVersionUID = 1L;

	/** 사용자 키 */
	private String userKey;

	/** 배송지 정보 리스트 */
	private List<DeliveryInfo> deliveryInfoList;

    public SearchDeliveryInfoSacRes() {}

    public SearchDeliveryInfoSacRes(String userKey, List<DeliveryInfo> deliveryInfoList) {
        this.userKey = userKey;
        this.deliveryInfoList = deliveryInfoList;
    }

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
	 * 배송지 정보 리스트를 리턴한다.
	 * 
	 * @return deliveryInfoList - 배송지 정보 리스트
	 */
	public List<DeliveryInfo> getDeliveryInfoList() {
		return this.deliveryInfoList;
	}

	/**
	 * 배송지 정보 리스트를 셋팅한다.
	 * 
	 * @param deliveryInfoList
	 *            배송지 정보 리스트
	 */
	public void setDeliveryInfoList(List<DeliveryInfo> deliveryInfoList) {
		this.deliveryInfoList = deliveryInfoList;
	}

}
