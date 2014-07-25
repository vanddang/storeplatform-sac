package com.skplanet.storeplatform.sac.client.purchase.cancel.vo;

import org.hibernate.validator.constraints.NotBlank;

import com.skplanet.storeplatform.sac.client.purchase.common.vo.PurchaseHeaderSacReq;

/**
 * 즉시이용정지 요청 VO
 * 
 * Updated on : 2013. 12. 13. Updated by : ntels_yjw
 */
public class ImmediatelyUseStopSacReq extends PurchaseHeaderSacReq {

	private static final long serialVersionUID = 1L;

	@NotBlank
	private String prchsId; // 구매ID
	@NotBlank
	private String adminId; // AdminID
	@NotBlank
	private String reqPathCd; // 요청경로코드
	@NotBlank
	private String drawbackAmt; // 환불금액

	/**
	 * @return the prchsId
	 */
	public String getPrchsId() {
		return this.prchsId;
	}

	/**
	 * @param prchsId
	 *            the prchsId to set
	 */
	public void setPrchsId(String prchsId) {
		this.prchsId = prchsId;
	}

	/**
	 * @return the adminId
	 */
	public String getAdminId() {
		return this.adminId;
	}

	/**
	 * @param adminId
	 *            the adminId to set
	 */
	public void setAdminId(String adminId) {
		this.adminId = adminId;
	}

	/**
	 * @return the reqPathCd
	 */
	public String getReqPathCd() {
		return this.reqPathCd;
	}

	/**
	 * @param reqPathCd
	 *            the reqPathCd to set
	 */
	public void setReqPathCd(String reqPathCd) {
		this.reqPathCd = reqPathCd;
	}

	/**
	 * @return the drawbackAmt
	 */
	public String getDrawbackAmt() {
		return this.drawbackAmt;
	}

	/**
	 * @param drawbackAmt
	 *            the drawbackAmt to set
	 */
	public void setDrawbackAmt(String drawbackAmt) {
		this.drawbackAmt = drawbackAmt;
	}

}
