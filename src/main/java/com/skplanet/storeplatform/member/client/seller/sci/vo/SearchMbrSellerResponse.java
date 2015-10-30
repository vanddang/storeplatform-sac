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
import java.util.Map;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;
import com.skplanet.storeplatform.member.client.common.util.Utils;
import com.skplanet.storeplatform.member.client.common.vo.CommonResponse;

/**
 * 판매자회원 기본정보만 조회 응답 Value Object.
 * 
 * Updated on : 2013. 12. 10. Updated by : wisestone_mikepark
 */
public class SearchMbrSellerResponse extends CommonInfo implements Serializable {

	/** The Constant serial Version UID. */
	private static final long serialVersionUID = 1L;

	/** 공통 응답 Value Object. */
	private CommonResponse commonResponse;

	/**
	 * 공통 응답 Value Object를 리턴한다.
	 * 
	 * @return commonResponse - 공통 응답 Value Object
	 */
	public CommonResponse getCommonResponse() {
		return this.commonResponse;
	}

	/** 판매자 정보 리스트 Value Object 목록. */
	private Map sellerMbrListMap;

	/**
	 * 공통 응답 Value Object를 설정한다.
	 * 
	 * @param commonResponse
	 *            공통 응답 Value Object
	 */
	public void setCommonResponse(CommonResponse commonResponse) {
		this.commonResponse = commonResponse;
	}

	// /**
	// * 약관동의 Value Object 목록를 리턴한다.
	// *
	// * @return mbrClauseAgree - 약관동의 Value Object 목록
	// */
	// public List<MbrClauseAgree> getMbrClauseAgreeList() {
	// return this.mbrClauseAgreeList;
	// }

	// /**
	// * 약관동의 Value Object 목록를 설정한다.
	// *
	// * @param mbrClauseAgreeList
	// * 약관동의 Value Object 목록
	// */
	// public void setMbrClauseAgreeList(List<MbrClauseAgree> mbrClauseAgreeList) {
	// this.mbrClauseAgreeList = mbrClauseAgreeList;
	// }

	/**
	 * Returns the serial version UID.
	 * 
	 * @return serialVersionUID - the serial version UID
	 */
	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	/**
	 * 판매자 정보 Value Object 목록을 리턴한다.
	 * 
	 * @return sellerMbrListMap - 판매자 정보 Value Object
	 */

	public Map getSellerMbrListMap() {
		return this.sellerMbrListMap;
	}

	/**
	 * 판매자 정보 Value Object 목록을 설정한다.
	 * 
	 * @param sellerMbrListMap
	 *            판매자 정보 Value Object
	 */
	public void setSellerMbrListMap(Map sellerMbrListMap) {
		this.sellerMbrListMap = sellerMbrListMap;
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
