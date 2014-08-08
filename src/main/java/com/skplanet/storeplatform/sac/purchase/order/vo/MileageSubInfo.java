package com.skplanet.storeplatform.sac.purchase.order.vo;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;

public class MileageSubInfo extends CommonInfo {
	private static final long serialVersionUID = 1L;

	private String typeCd; // 마일리지 타입
	private String userGrdCd; // 회원등급코드
	private int prodSaveRate;// 상품적립율
	private double targetPaymentAmt; // 적립대상결제금액
	private double saveExpectAmt; // 적립예정금액
	private double saveResultAmt; // 적립결과금액
	private String prchsReqPathCd; // 적립금요청경로
	private String saveTypeCd; // 처리타입코드
	private String procStatusCd; // 처리상태코드

	/**
	 * @return the typeCd
	 */
	public String getTypeCd() {
		return this.typeCd;
	}

	/**
	 * @param typeCd
	 *            the typeCd to set
	 */
	public void setTypeCd(String typeCd) {
		this.typeCd = typeCd;
	}

	/**
	 * @return the userGrdCd
	 */
	public String getUserGrdCd() {
		return this.userGrdCd;
	}

	/**
	 * @param userGrdCd
	 *            the userGrdCd to set
	 */
	public void setUserGrdCd(String userGrdCd) {
		this.userGrdCd = userGrdCd;
	}

	/**
	 * @return the prodSaveRate
	 */
	public int getProdSaveRate() {
		return this.prodSaveRate;
	}

	/**
	 * @param prodSaveRate
	 *            the prodSaveRate to set
	 */
	public void setProdSaveRate(int prodSaveRate) {
		this.prodSaveRate = prodSaveRate;
	}

	/**
	 * @return the targetPaymentAmt
	 */
	public double getTargetPaymentAmt() {
		return this.targetPaymentAmt;
	}

	/**
	 * @param targetPaymentAmt
	 *            the targetPaymentAmt to set
	 */
	public void setTargetPaymentAmt(double targetPaymentAmt) {
		this.targetPaymentAmt = targetPaymentAmt;
	}

	/**
	 * @return the saveExpectAmt
	 */
	public double getSaveExpectAmt() {
		return this.saveExpectAmt;
	}

	/**
	 * @param saveExpectAmt
	 *            the saveExpectAmt to set
	 */
	public void setSaveExpectAmt(double saveExpectAmt) {
		this.saveExpectAmt = saveExpectAmt;
	}

	/**
	 * @return the saveResultAmt
	 */
	public double getSaveResultAmt() {
		return this.saveResultAmt;
	}

	/**
	 * @param saveResultAmt
	 *            the saveResultAmt to set
	 */
	public void setSaveResultAmt(double saveResultAmt) {
		this.saveResultAmt = saveResultAmt;
	}

	/**
	 * @return the prchsReqPathCd
	 */
	public String getPrchsReqPathCd() {
		return this.prchsReqPathCd;
	}

	/**
	 * @param prchsReqPathCd
	 *            the prchsReqPathCd to set
	 */
	public void setPrchsReqPathCd(String prchsReqPathCd) {
		this.prchsReqPathCd = prchsReqPathCd;
	}

	/**
	 * @return the saveTypeCd
	 */
	public String getSaveTypeCd() {
		return this.saveTypeCd;
	}

	/**
	 * @param saveTypeCd
	 *            the saveTypeCd to set
	 */
	public void setSaveTypeCd(String saveTypeCd) {
		this.saveTypeCd = saveTypeCd;
	}

	/**
	 * @return the procStatusCd
	 */
	public String getProcStatusCd() {
		return this.procStatusCd;
	}

	/**
	 * @param procStatusCd
	 *            the procStatusCd to set
	 */
	public void setProcStatusCd(String procStatusCd) {
		this.procStatusCd = procStatusCd;
	}

}
