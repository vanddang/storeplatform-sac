package com.skplanet.storeplatform.sac.client.member.vo.user;

import java.util.List;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;
import com.skplanet.storeplatform.sac.client.member.vo.common.GiftChargeInfoSac;

/**
 * [RESPONSE]회원 상품권 충전 정보 조회.
 * 
 * Updated on : 2015. 11. 11. Updated by : 반범진.
 */
@JsonSerialize(include = Inclusion.NON_NULL)
public class SearchGiftChargeInfoSacRes extends CommonInfo {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 사용자 Key.
	 */
	private String userKey;

	/**
	 * 상품권 충전 정보 리스트.
	 */
	private List<GiftChargeInfoSac> giftChargeInfoList;

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
	public List<GiftChargeInfoSac> getGiftChargeInfoList() {
		return this.giftChargeInfoList;
	}

	/**
	 * @param giftChargeInfoList
	 *            List<GiftChargeInfoSac>
	 */
	public void setGiftChargeInfoList(List<GiftChargeInfoSac> giftChargeInfoList) {
		this.giftChargeInfoList = giftChargeInfoList;
	}

}
