package com.skplanet.storeplatform.member.client.seller.sci.vo;

import java.util.List;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;
import com.skplanet.storeplatform.member.client.common.util.Utils;
import com.skplanet.storeplatform.member.client.common.vo.CommonResponse;
import com.skplanet.storeplatform.member.client.common.vo.MbrClauseAgree;

/**
 * Class 설명
 * 
 * Updated on : 2015. 3. 6. Updated by : Rejoice, Burkhan
 */
public class SearchAgreementListSellerResponse extends CommonInfo {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** 공통 응답 Value Object. */
	private CommonResponse commonResponse;

	/** 사용자 약관동의 Value Object List. */
	private List<MbrClauseAgree> mbrClauseAgreeList;

	/** 판매자 Key. */
	private String sellerKey;

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
	 * 사용자 약관동의 Value Object List를 리턴한다.
	 * 
	 * @return mbrClauseAgreeList - 사용자 약관동의 Value Object List
	 */
	public List<MbrClauseAgree> getMbrClauseAgreeList() {
		return this.mbrClauseAgreeList;
	}

	/**
	 * 사용자 약관동의 Value Object List를 설정한다.
	 * 
	 * @param mbrClauseAgreeList
	 *            사용자 약관동의 Value Object List
	 */
	public void setMbrClauseAgreeList(List<MbrClauseAgree> mbrClauseAgreeList) {
		this.mbrClauseAgreeList = mbrClauseAgreeList;
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
