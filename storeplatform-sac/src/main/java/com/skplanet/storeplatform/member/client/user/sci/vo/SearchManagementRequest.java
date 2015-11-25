package com.skplanet.storeplatform.member.client.user.sci.vo;

import java.io.Serializable;
import java.util.List;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;
import com.skplanet.storeplatform.member.client.common.vo.CommonRequest;
import com.skplanet.storeplatform.member.client.common.vo.MbrMangItemPtcr;

/**
 * 회원 부가 정보 조회 Value Object
 * 
 * Updated on : 2015. 4. 9. Updated by : 반범진.
 */
public class SearchManagementRequest extends CommonInfo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 공통 Request.
	 */
	private CommonRequest commonRequest;

	/** 사용자 Key. */
	private String userKey;

	/** 사용자 부가속성 리스트. */
	private List<MbrMangItemPtcr> mbrMangItemPtcr;

	/**
	 * @return commonRequest
	 */
	public CommonRequest getCommonRequest() {
		return this.commonRequest;
	}

	/**
	 * @param commonRequest
	 *            CommonRequest
	 */
	public void setCommonRequest(CommonRequest commonRequest) {
		this.commonRequest = commonRequest;
	}

	/**
	 * @return the userKey
	 */
	public String getUserKey() {
		return this.userKey;
	}

	/**
	 * @param userKey
	 *            the userKey to set
	 */
	public void setUserKey(String userKey) {
		this.userKey = userKey;
	}

	/**
	 * @return the mbrMangItemPtcr
	 */
	public List<MbrMangItemPtcr> getMbrMangItemPtcr() {
		return this.mbrMangItemPtcr;
	}

	/**
	 * @param mbrMangItemPtcr
	 *            the mbrMangItemPtcr to set
	 */
	public void setMbrMangItemPtcr(List<MbrMangItemPtcr> mbrMangItemPtcr) {
		this.mbrMangItemPtcr = mbrMangItemPtcr;
	}

}
