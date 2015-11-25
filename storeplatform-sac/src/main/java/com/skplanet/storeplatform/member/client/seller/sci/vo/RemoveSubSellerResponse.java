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

/**
 * 서브 판매자 탈퇴에 대한 응답을 위한 value object
 * 
 * Updated on : 2013. 12. 10. Updated by : wisestone_mikepark
 */
public class RemoveSubSellerResponse extends CommonInfo implements Serializable {
	/** The Constant serial Version UID. */
	private static final long serialVersionUID = 1L;

	/** 공통 응답 Value Object. */
	private CommonResponse commonResponse;

	/** 삭제된 서브계정 카운트. */
	private int deletedNumber;

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
	 * 삭제된 서브계정 카운트를 리턴한다.
	 * 
	 * @return deletedNumber - 삭제된 서브계정 카운트
	 */
	public int getDeletedNumber() {
		return this.deletedNumber;
	}

	/**
	 * 삭제된 서브계정 카운트를 설정한다.
	 * 
	 * @param deletedNumber
	 *            삭제된 서브계정 카운트
	 */
	public void setDeletedNumber(int deletedNumber) {
		this.deletedNumber = deletedNumber;
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
