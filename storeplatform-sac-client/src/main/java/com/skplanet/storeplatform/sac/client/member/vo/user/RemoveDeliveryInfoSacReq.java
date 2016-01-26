/**
 * 
 */
package com.skplanet.storeplatform.sac.client.member.vo.user;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;

/**
 * [REQUEST] 배송지 정보 삭제
 * 
 * Updated on : 2015. 10. 05. Updated by : 최진호, Bogogt.
 */
public class RemoveDeliveryInfoSacReq extends CommonInfo {

	private static final long serialVersionUID = 1L;

	/** 사용자 Key */
	@NotEmpty
	private String userKey;

	/** 배송지 시퀀스 */
	@NotNull
	private Long deliverySeq;

	/**
	 * 사용자 Key을(를) 리턴한다.
	 * 
	 * @return userKey - 사용자 Key
	 */
	public String getUserKey() {
		return this.userKey;
	}

	/**
	 * 사용자 Key(을)를 셋팅한다.
	 * 
	 * @param userKey
	 *            userKey 사용자 Key
	 */
	public void setUserKey(String userKey) {
		this.userKey = userKey;
	}

	/**
	 * 배송지 시퀀스을(를) 리턴한다.
	 * 
	 * @return deliverySeq - 배송지 시퀀스
	 */
	public Long getDeliverySeq() {
		return this.deliverySeq;
	}

	/**
	 * 배송지 시퀀스(을)를 셋팅한다.
	 * 
	 * @param deliverySeq
	 *            deliverySeq 배송지 시퀀스
	 */
	public void setDeliverySeq(Long deliverySeq) {
		this.deliverySeq = deliverySeq;
	}

}
