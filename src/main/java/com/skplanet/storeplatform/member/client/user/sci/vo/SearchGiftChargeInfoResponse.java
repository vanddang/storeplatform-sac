/**
 * 
 */
package com.skplanet.storeplatform.member.client.user.sci.vo;

import java.util.List;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;
import com.skplanet.storeplatform.member.client.common.vo.CommonResponse;

/**
 * 상품권 충전 정보 조회 응답 Value Object
 * 
 * Updated on : 2015. 11. 11. Updated by : 반범진.
 */
public class SearchGiftChargeInfoResponse extends CommonInfo {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/** 공통 응답 Value Object. */
	private CommonResponse commonResponse;

	/**
	 * 사용자 Key.
	 */
	private String userKey;

	/**
	 * 상품권 충전 정보 리스트.
	 */
	private List<GiftChargeInfo> giftChargeInfoList;

	/**
	 * @return commonResponse
	 */
	public CommonResponse getCommonResponse() {
		return this.commonResponse;
	}

	/**
	 * @param commonResponse
	 *            commonResponse
	 */
	public void setCommonResponse(CommonResponse commonResponse) {
		this.commonResponse = commonResponse;
	}

	/**
	 * @return userKey
	 */
	public String getUserKey() {
		return this.userKey;
	}

	/**
	 * @param userKey
	 *            String
	 */
	public void setUserKey(String userKey) {
		this.userKey = userKey;
	}

	/**
	 * @return giftChargeInfoList
	 */
	public List<GiftChargeInfo> getGiftChargeInfoList() {
		return this.giftChargeInfoList;
	}

	/**
	 * @param giftChargeInfoList
	 *            List<GiftChargeInfo>
	 */
	public void setGiftChargeInfoList(List<GiftChargeInfo> giftChargeInfoList) {
		this.giftChargeInfoList = giftChargeInfoList;
	}
}
