package com.skplanet.storeplatform.sac.client.internal.purchase.history.vo;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;

import java.io.Serializable;

/**
 * 
 * 선물수신확인 처리 응답.
 * 
 * Updated on : 2013. 12. 28. Updated by : 조용진, 엔텔스.
 */
public class GiftConfirmSacInRes extends CommonInfo implements Serializable {
	private static final long serialVersionUID = 1L;

	private String prodId; // 상품 아이디
	private String prchsId; // 구매ID
	private String resultYn; // update 성공여부
	private String useStartDt; // 이용 시작 일시
	private String useExprDt; // 이용 만료 일시
	private String dwldStartDt; // 다운로드 시작 일시
	private String dwldExprDt; // 다운로드 만료 일시

	/**
	 * @return the prodId
	 */
	public String getProdId() {
		return this.prodId;
	}

	/**
	 * @param prodId
	 *            the prodId to set
	 */
	public void setProdId(String prodId) {
		this.prodId = prodId;
	}

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
	 * @return the resultYn
	 */
	public String getResultYn() {
		return this.resultYn;
	}

	/**
	 * @param resultYn
	 *            the resultYn to set
	 */
	public void setResultYn(String resultYn) {
		this.resultYn = resultYn;
	}

	/**
	 * Gets use start dt.
	 *
	 * @return the use start dt
	 */
	public String getUseStartDt() {
		return useStartDt;
	}

	/**
	 * Sets use start dt.
	 *
	 * @param useStartDt
	 *            the use start dt
	 */
	public void setUseStartDt(String useStartDt) {
		this.useStartDt = useStartDt;
	}

	/**
	 * Gets use expr dt.
	 *
	 * @return the use expr dt
	 */
	public String getUseExprDt() {
		return useExprDt;
	}

	/**
	 * Sets use expr dt.
	 *
	 * @param useExprDt
	 *            the use expr dt
	 */
	public void setUseExprDt(String useExprDt) {
		this.useExprDt = useExprDt;
	}

	/**
	 * Gets dwld start dt.
	 *
	 * @return the dwld start dt
	 */
	public String getDwldStartDt() {
		return dwldStartDt;
	}

	/**
	 * Sets dwld start dt.
	 *
	 * @param dwldStartDt
	 *            the dwld start dt
	 */
	public void setDwldStartDt(String dwldStartDt) {
		this.dwldStartDt = dwldStartDt;
	}

	/**
	 * Gets dwld expr dt.
	 *
	 * @return the dwld expr dt
	 */
	public String getDwldExprDt() {
		return dwldExprDt;
	}

	/**
	 * Sets dwld expr dt.
	 *
	 * @param dwldExprDt
	 *            the dwld expr dt
	 */
	public void setDwldExprDt(String dwldExprDt) {
		this.dwldExprDt = dwldExprDt;
	}
}
