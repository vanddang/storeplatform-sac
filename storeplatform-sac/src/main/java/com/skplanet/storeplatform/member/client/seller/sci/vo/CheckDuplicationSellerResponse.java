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

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;
import com.skplanet.storeplatform.member.client.common.util.Utils;
import com.skplanet.storeplatform.member.client.common.vo.CommonResponse;

// TODO: Auto-generated Javadoc
/**
 * 판매자회원 ID/이메일 존재여부 확인 응답 Value Object.
 * 
 * Updated on : 2013. 12. 16. Updated by : wisestone_mikepark
 */
public class CheckDuplicationSellerResponse extends CommonInfo implements Serializable {
	/** The Constant serial Version UID. */
	private static final long serialVersionUID = 1L;

	/** 공통 응답 Value Object. */
	private CommonResponse commonResponse;

	/** ID/Email 존재 여부. Example : Y/N */
	private String isRegistered;

	/** (if isRegistered = Y) 판매자 ID. */
	private String sellerID;

	/** 판매자 정보 Value Object. */
	private SellerMbr sellerMbr;

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
	 * 판매자 ID를 리턴한다.
	 * 
	 * @return sellerID - 판매자 ID
	 */
	public String getSellerID() {
		return this.sellerID;
	}

	/**
	 * 판매자 ID를 설정한다.
	 * 
	 * @param sellerID
	 *            매자 ID
	 */
	public void setSellerID(String sellerID) {
		this.sellerID = sellerID;
	}

	/**
	 * 판매자 정보 Value Object를 리턴한다.
	 * 
	 * @return sellerMbr - 판매자 정보 Value Object
	 */
	public SellerMbr getSellerMbr() {
		return this.sellerMbr;
	}

	/**
	 * 판매자 정보 Value Object를 설정한다.
	 * 
	 * @param sellerMbr
	 *            판매자 정보 Value Object
	 */
	public void setSellerMbr(SellerMbr sellerMbr) {
		this.sellerMbr = sellerMbr;
	}

	/**
	 * ID/Email 존재 여부 (Y/N)를 리턴한다.
	 * 
	 * @return isRegistered - ID/Email 존재 여부 (Y/N).
	 */
	public String getIsRegistered() {
		return this.isRegistered;
	}

	/**
	 * D/Email 존재 여부 (Y/N)를 설정한다.
	 * 
	 * @param isRegistered
	 *            ID/Email 존재 여부 (Y/N)
	 */
	public void setIsRegistered(String isRegistered) {
		this.isRegistered = isRegistered;
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
