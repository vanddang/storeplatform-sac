package com.skplanet.storeplatform.member.client.seller.sci.vo;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;
import com.skplanet.storeplatform.member.client.common.util.Utils;
import com.skplanet.storeplatform.member.client.common.vo.CommonResponse;

/**
 * 판매자 약관동의 등록/수정 응답 Value Object
 * 
 * Updated on : 2015. 3. 6. Updated by : Rejoice, Burkhan
 */
public class UpdateAgreementSellerResponse extends CommonInfo {

	private static final long serialVersionUID = 1L;

	/**
	 * 판매자 key.
	 */
	private String sellerKey;

	/**
	 * 사용자 약관동의 항목 개수.
	 */
	private int agreementCount;

	/** 공통 요청 Value Object. */
	private CommonResponse commonResponse;

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
	 * 판매자 약관동의 항목의 갯수를 리턴한다.
	 * 
	 * @return agreementCount - 사용자 약관동의 항목의 갯수
	 */
	public int getAgreementCount() {
		return this.agreementCount;
	}

	/**
	 * 판매자 약관동의 항목의 갯수를 설정한다.
	 * 
	 * @param agreementCount
	 *            사용자 약관동의 항목의 갯수
	 */
	public void setAgreementCount(int agreementCount) {
		this.agreementCount = agreementCount;
	}

	@Override
	public String toString() {
		return Utils.printKeyValues(this);
	}

}
