package com.skplanet.storeplatform.member.client.user.sci.vo;

import java.io.Serializable;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;
import com.skplanet.storeplatform.member.client.common.util.Utils;
import com.skplanet.storeplatform.member.client.common.vo.CommonRequest;

/**
 * 배송지 삭제 요청 Value Object
 * 
 * Updated on : 2015. 10. 05. Updated by : 최진호, Bogogt.
 */
public class RemoveDeliveryInfoRequest extends CommonInfo implements Serializable {

	/** The Constant serial Version UID. */
	private static final long serialVersionUID = 1L;

	/** 공통 요청 Value Object. */
	private CommonRequest commonRequest;
	/** 사용자 Key. */
	private String userKey;
	/** 배송지 시퀀스. */
	private String deliverySeq;

	/**
	 * 공통 요청 Value Object를 리턴한다.
	 * 
	 * @return commonRequest - 공통 요청 Value Object
	 */
	public CommonRequest getCommonRequest() {
		return this.commonRequest;
	}

	/**
	 * 공통 요청 Value Object를 설정한다.
	 * 
	 * @param commonRequest
	 *            공통 요청 Value Object
	 */
	public void setCommonRequest(CommonRequest commonRequest) {
		this.commonRequest = commonRequest;
	}

	/**
	 * 사용자 Key(을)를 리턴한다.
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
	 *            사용자 Key
	 */
	public void setUserKey(String userKey) {
		this.userKey = userKey;
	}

	/**
	 * 배송지 시퀀스(을)를 리턴한다.
	 * 
	 * @return deliverySeq - 배송지 시퀀스
	 */
	public String getDeliverySeq() {
		return this.deliverySeq;
	}

	/**
	 * 배송지 시퀀스(을)를 셋팅한다.
	 * 
	 * @param deliverySeq
	 *            배송지 시퀀스
	 */
	public void setDeliverySeq(String deliverySeq) {
		this.deliverySeq = deliverySeq;
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
