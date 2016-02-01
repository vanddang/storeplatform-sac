package com.skplanet.storeplatform.sac.client.purchase.cancel.vo;

import org.hibernate.validator.constraints.NotBlank;

import com.skplanet.storeplatform.sac.client.purchase.common.vo.PurchaseHeaderSacReq;

/**
 * 즉시이용정지 요청 VO
 * 
 * Updated on : 2013. 12. 13. Updated by : ntels_yjw
 * Updated on : 2016. 01. 14. Updated by : eastaim
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
    private String userKey; // 사용자 Key
    private String reasonCd; // 환불 사유 코드
    private String reasonMsg; // 환불 사유 메세지

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
     * @return 환불 사유 코드
     */
    public String getReasonCd() {
        return reasonCd;
    }

    /**
     * 환불 사유 코드 저장
     * @param reasonCd
     */
    public void setReasonCd(String reasonCd) {
        this.reasonCd = reasonCd;
    }

    /**
     * @return 환불 사유 기타 메세지
     */
    public String getReasonMsg() {
        return reasonMsg;
    }

    /**
     * 환불 사유 기타 메세지 저장
     * @param reasonMsg
     */
    public void setReasonMsg(String reasonMsg) {
        this.reasonMsg = reasonMsg;
    }
}
