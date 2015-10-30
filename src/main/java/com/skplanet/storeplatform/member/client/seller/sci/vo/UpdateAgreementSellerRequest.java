package com.skplanet.storeplatform.member.client.seller.sci.vo;

import java.util.List;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;
import com.skplanet.storeplatform.member.client.common.util.Utils;
import com.skplanet.storeplatform.member.client.common.vo.CommonRequest;
import com.skplanet.storeplatform.member.client.common.vo.MbrClauseAgree;

/**
 * 판매자 약관동의 등록/수정 요청 Value Object
 * 
 * Updated on : 2015. 3. 6. Updated by : Rejoice, Burkhan
 */
public class UpdateAgreementSellerRequest extends CommonInfo {

	/** The Constant serial Version UID. */
	private static final long serialVersionUID = 1L;

	/** 공통 요청 Value Object. */
	private CommonRequest commonRequest;

	/** 판매자 Key. */
	private String sellerKey;

	/** 약관동의 항목 Value Object List. */
	private List<MbrClauseAgree> mbrClauseAgreeList;

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
	 * 사용자 약관동의 항목 Value Object List를 리턴한다.
	 * 
	 * @return mbrClauseAgreeList - 사용자 약관동의 항목 Value Object List
	 */
	public List<MbrClauseAgree> getMbrClauseAgreeList() {
		return this.mbrClauseAgreeList;
	}

	/**
	 * 사용자 약관동의 항목 Value Object List를 설정한다.
	 * 
	 * @param mbrClauseAgreeList
	 *            사용자 약관동의 항목 Value Object List
	 */
	public void setMbrClauseAgreeList(List<MbrClauseAgree> mbrClauseAgreeList) {
		this.mbrClauseAgreeList = mbrClauseAgreeList;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.skplanet.storeplatform.framework.core.common.vo.CommonInfo#toString()
	 */
	@Override
	public String toString() {
		return Utils.printKeyValues(this);
	}
}
