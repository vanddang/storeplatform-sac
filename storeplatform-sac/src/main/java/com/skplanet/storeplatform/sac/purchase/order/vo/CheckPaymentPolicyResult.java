/**
 * 
 */
package com.skplanet.storeplatform.sac.purchase.order.vo;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;

/**
 * 결제 정책 체크 결과 VO
 * 
 * Updated on : 2014. 11. 24. Updated by : 이승택, nTels.
 */
public class CheckPaymentPolicyResult extends CommonInfo {
	private static final long serialVersionUID = 201401221L;

	private String paymentAdjInfo; // 결제수단 별 가능 거래금액/비율 조정 정보
	private String phoneLimitType; // 후불건 제한에 걸린 유형
	private String deferredPaymentType; // 후불 결제 유형

	private double phoneRestAmt; // 후불 잔여 금액

	private boolean telecomTestMdn; // 통신사 시험폰 여부
	private boolean telecomTestMdnWhiteList; // 통신사 시험폰 White List 등록 여부

	private boolean corporation; // 법인폰 여부
	private boolean skpCorporation; // SKP 법인폰 여부 (SKP후불 OCB적립 제한으로, 법인 구분 필요)

	private boolean mvno; // MVNO 회선 여부

	/**
	 * @return the paymentAdjInfo
	 */
	public String getPaymentAdjInfo() {
		return this.paymentAdjInfo;
	}

	/**
	 * @param paymentAdjInfo
	 *            the paymentAdjInfo to set
	 */
	public void setPaymentAdjInfo(String paymentAdjInfo) {
		this.paymentAdjInfo = paymentAdjInfo;
	}

	/**
	 * @return the phoneLimitType
	 */
	public String getPhoneLimitType() {
		return this.phoneLimitType;
	}

	/**
	 * @param phoneLimitType
	 *            the phoneLimitType to set
	 */
	public void setPhoneLimitType(String phoneLimitType) {
		this.phoneLimitType = phoneLimitType;
	}

	/**
	 * @return the deferredPaymentType
	 */
	public String getDeferredPaymentType() {
		return this.deferredPaymentType;
	}

	/**
	 * @param deferredPaymentType
	 *            the deferredPaymentType to set
	 */
	public void setDeferredPaymentType(String deferredPaymentType) {
		this.deferredPaymentType = deferredPaymentType;
	}

	/**
	 * @return the phoneRestAmt
	 */
	public double getPhoneRestAmt() {
		return this.phoneRestAmt;
	}

	/**
	 * @param phoneRestAmt
	 *            the phoneRestAmt to set
	 */
	public void setPhoneRestAmt(double phoneRestAmt) {
		this.phoneRestAmt = phoneRestAmt;
	}

	/**
	 * @return the telecomTestMdn
	 */
	public boolean isTelecomTestMdn() {
		return this.telecomTestMdn;
	}

	/**
	 * @param telecomTestMdn
	 *            the telecomTestMdn to set
	 */
	public void setTelecomTestMdn(boolean telecomTestMdn) {
		this.telecomTestMdn = telecomTestMdn;
	}

	/**
	 * @return the telecomTestMdnWhiteList
	 */
	public boolean isTelecomTestMdnWhiteList() {
		return this.telecomTestMdnWhiteList;
	}

	/**
	 * @param telecomTestMdnWhiteList
	 *            the telecomTestMdnWhiteList to set
	 */
	public void setTelecomTestMdnWhiteList(boolean telecomTestMdnWhiteList) {
		this.telecomTestMdnWhiteList = telecomTestMdnWhiteList;
	}

	/**
	 * @return the corporation
	 */
	public boolean isCorporation() {
		return this.corporation;
	}

	/**
	 * @param corporation
	 *            the corporation to set
	 */
	public void setCorporation(boolean corporation) {
		this.corporation = corporation;
	}

	/**
	 * @return the skpCorporation
	 */
	public boolean isSkpCorporation() {
		return this.skpCorporation;
	}

	/**
	 * @param skpCorporation
	 *            the skpCorporation to set
	 */
	public void setSkpCorporation(boolean skpCorporation) {
		this.skpCorporation = skpCorporation;
	}

	/**
	 * @return the mvno
	 */
	public boolean isMvno() {
		return this.mvno;
	}

	/**
	 * @param mvno
	 *            the mvno to set
	 */
	public void setMvno(boolean mvno) {
		this.mvno = mvno;
	}

}
