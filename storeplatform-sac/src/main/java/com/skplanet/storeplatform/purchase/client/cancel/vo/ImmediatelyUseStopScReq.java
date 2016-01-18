package com.skplanet.storeplatform.purchase.client.cancel.vo;

import com.skplanet.storeplatform.purchase.client.common.vo.PurchaseCommonScReq;

/**
 * 즉시이용정지 요청 VO
 * 
 * Updated on : 2013. 12. 13. Updated by : ntels_yjw
 */
public class ImmediatelyUseStopScReq extends PurchaseCommonScReq {

	private static final long serialVersionUID = 1L;

	private String prchsId; // 구매ID
	private String adminId; // AdminID
	private String reqPathCd; // 요청경로코드
	private String drawbackAmt; // 환불금액

	/* 내부에서 SETTING */
	private String prchsStatusCd; // 구매상태
	private String paymentMtdCd; // 결제수단코드

	private String statusCd;
	private String userKey;

    private String dwldStartDt; // 다운로드 시작 일시
    private String dwldExprDt; // 다운로드 만료 일시

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
	 * @return the prchsStatusCd
	 */
	public String getPrchsStatusCd() {
		return this.prchsStatusCd;
	}

	/**
	 * @param prchsStatusCd
	 *            the prchsStatusCd to set
	 */
	public void setPrchsStatusCd(String prchsStatusCd) {
		this.prchsStatusCd = prchsStatusCd;
	}

	/**
	 * @return the paymentMtdCd
	 */
	public String getPaymentMtdCd() {
		return this.paymentMtdCd;
	}

	/**
	 * @param paymentMtdCd
	 *            the paymentMtdCd to set
	 */
	public void setPaymentMtdCd(String paymentMtdCd) {
		this.paymentMtdCd = paymentMtdCd;
	}

	/**
	 * @return the statusCd
	 */
	public String getStatusCd() {
		return this.statusCd;
	}

	/**
	 * @param statusCd
	 *            the statusCd to set
	 */
	public void setStatusCd(String statusCd) {
		this.statusCd = statusCd;
	}

	/**
	 * @return the userKey
	 */
	@Override
	public String getUserKey() {
		return this.userKey;
	}

	/**
	 * @param userKey
	 *            the userKey to set
	 */
	@Override
	public void setUserKey(String userKey) {
		this.userKey = userKey;
	}

    /**
     * 환불 사유 코드
     * @return
     */
    public String getReasonCd() {
        return reasonCd;
    }

    /**
     * 환불 사유 코드 설정
     * @param reasonCd
     */
    public void setReasonCd(String reasonCd) {
        this.reasonCd = reasonCd;
    }

    /**
     * 환불 사유 메세지
     * @return
     */
    public String getReasonMsg() {
        return reasonMsg;
    }

    /**
     * 환불 사유 메세지 설정
     * @param reasonMsg
     */
    public void setReasonMsg(String reasonMsg) {
        this.reasonMsg = reasonMsg;
    }

    /**
     * 다운로드 시작일시
     * @return
     */
    public String getDwldStartDt() {
        return dwldStartDt;
    }

    /**
     * 다운로드 시작일시 설정
     * @param dwldStartDt
     */
    public void setDwldStartDt(String dwldStartDt) {
        this.dwldStartDt = dwldStartDt;
    }

    /**
     * 다운로드 만료일시
     * @return
     */
    public String getDwldExprDt() {
        return dwldExprDt;
    }

    /**
     * 다운로드 만료일시 설정
     * @param dwldExprDt
     */
    public void setDwldExprDt(String dwldExprDt) {
        this.dwldExprDt = dwldExprDt;
    }
}
