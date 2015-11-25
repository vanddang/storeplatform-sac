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
import com.skplanet.storeplatform.member.client.common.vo.CommonRequest;

/**
 * 서브 계정 탈퇴 요청 Value Object
 * 
 * Updated on : 2013. 12. 10. Updated by : wisestone_mikepark
 */
public class RemoveSubSellerRequest extends CommonInfo implements Serializable {
	/** The Constant serial Version UID. */
	private static final long serialVersionUID = 1L;

	/** 공통 요청 Value Object. */
	private CommonRequest commonRequest;

	/** 판매자 부모키 키. */
	private String parentSellerKey;

	/** 삭제할 서브계정 키 목록. */
	private List<String> sellerKeyList;

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
	 * 판매자 부모키를 리턴한다.
	 * 
	 * @return parentSellerKey - 판매자 부모키
	 */
	public String getParentSellerKey() {
		return this.parentSellerKey;
	}

	/**
	 * 판매자 부모키를 설정한다.
	 * 
	 * @param parentSellerKey
	 *            판매자 부모키를
	 */
	public void setParentSellerKey(String parentSellerKey) {
		this.parentSellerKey = parentSellerKey;
	}

	/**
	 * 삭제할 서브계정 키 목록을 리턴한다.
	 * 
	 * @return sellerKeyList - 삭제할 서브계정 키 목록
	 */
	public List<String> getSellerKeyList() {
		return this.sellerKeyList;
	}

	/**
	 * 삭제할 서브계정 키 목록을 설정한다.
	 * 
	 * @param sellerKeyList
	 *            삭제할 서브계정 키 목록
	 */
	public void setSellerKeyList(List<String> sellerKeyList) {
		this.sellerKeyList = sellerKeyList;
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
