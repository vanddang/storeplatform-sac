/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 * 
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.member.client.seller.sci.vo;

import java.io.Serializable;
import java.util.List;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;
import com.skplanet.storeplatform.member.client.common.util.Utils;
import com.skplanet.storeplatform.member.client.common.vo.CommonResponse;

/**
 * 판매자회원 제공자 정보 조회 응답 Value Object.
 * 
 * Updated on : 2015. 11. 13. Updated by : 최진호, 보고지티.
 */
public class SearchProviderResponse extends CommonInfo implements Serializable {

	/** The Constant serial Version UID. */
	private static final long serialVersionUID = 1L;
	/** 공통 응답 Value Object. */
	private CommonResponse commonResponse;
	/** 판매자 Key. */
	private String sellerKey;
	/** 제공자 정보 리스트 Value Object 목록. */
	private List<ProviderMbrInfo> providerMbrInfoList;

	/**
	 * 공통 응답 Value Object를 리턴한다.
	 * 
	 * @return commonResponse - 공통 응답 Value Object
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
	 * 판매자 Key를 리턴한다.
	 * 
	 * @return sellerKey - 판매자 Key
	 */
	public String getSellerKey() {
		return this.sellerKey;
	}

	/**
	 * 판매자 Key를 설정한다.
	 * 
	 * @param sellerKey
	 *            판매자 Key
	 */
	public void setSellerKey(String sellerKey) {
		this.sellerKey = sellerKey;
	}

	/**
	 * 제공자 정보 리스트 Value Object를 리턴한다.
	 * 
	 * @return the providerMbrInfoList - 제공자 정보 리스트 Value Object
	 */
	public List<ProviderMbrInfo> getProviderMbrInfoList() {
		return this.providerMbrInfoList;
	}

	/**
	 * 제공자 정보 리스트 Value Object를 셋팅한다.
	 * 
	 * @param providerMbrInfoList
	 *            제공자 정보 리스트 Value Object
	 */
	public void setProviderMbrInfoList(List<ProviderMbrInfo> providerMbrInfoList) {
		this.providerMbrInfoList = providerMbrInfoList;
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
