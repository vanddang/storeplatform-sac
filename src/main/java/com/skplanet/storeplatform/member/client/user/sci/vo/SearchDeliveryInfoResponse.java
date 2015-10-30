package com.skplanet.storeplatform.member.client.user.sci.vo;

import java.io.Serializable;
import java.util.List;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;
import com.skplanet.storeplatform.member.client.common.util.Utils;
import com.skplanet.storeplatform.member.client.common.vo.CommonResponse;

/**
 * 배송지 정보 조회 응답 Value Object
 * 
 * Updated on : 2015. 9. 30 Updated by : 최진호, Bogogt
 */
public class SearchDeliveryInfoResponse extends CommonInfo implements Serializable {

	/** The Constant serial Version UID. */
	private static final long serialVersionUID = 1L;

	/** 공통 요청 Value Object. */
	private CommonResponse commonResponse;

	/** 사용자 배송지 조회 Value Object List. */
	private List<SearchDeliveryInfo> searchDeliveryInfo;

	/** 사용자 key. */
	private String userKey;

	/**
	 * 공통 응답 Value Object를 리턴한다.
	 * 
	 * @return commonResponse 공통 응답 Value Object
	 */
	public CommonResponse getCommonResponse() {
		return this.commonResponse;
	}

	/**
	 * 공통 응답 Value Object를 설정한다.
	 * 
	 * @param commonResponse
	 *            공통 응답 Value Object
	 */
	public void setCommonResponse(CommonResponse commonResponse) {
		this.commonResponse = commonResponse;
	}

	/**
	 * 배송지 정보 Value Object List를 리턴한다.
	 * 
	 * @return searchDeliveryInfoList - 배송지 정보 Value Object List
	 */
	public List<SearchDeliveryInfo> getSearchDeliveryInfoList() {
		return this.searchDeliveryInfo;
	}

	/**
	 * 배송지 정보 Value Object List를 설정한다.
	 * 
	 * @param searchDeliveryInfo
	 *            배송지 정보 Value Object List
	 */
	public void setSearchDeliveryInfoList(List<SearchDeliveryInfo> searchDeliveryInfo) {
		this.searchDeliveryInfo = searchDeliveryInfo;
	}

	/**
	 * 사용자 key를 리턴한다.
	 * 
	 * @return userKey - 사용자 key
	 */
	public String getUserKey() {
		return this.userKey;
	}

	/**
	 * 사용자 key를 셋팅한다.
	 * 
	 * @param userKey
	 *            사용자 key
	 */
	public void setUserKey(String userKey) {
		this.userKey = userKey;
	}

	/**
	 * Returns the serial version UID.
	 * 
	 * @return serialVersionUID - the serial version UID
	 */
	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return Utils.printKeyValues(this);
	}

}
