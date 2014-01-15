package com.skplanet.storeplatform.sac.client.purchase.vo.history;

import java.io.Serializable;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;

/**
 * 
 * 선물수신확인 처리 응답.
 * 
 * Updated on : 2013. 12. 28. Updated by : 조용진, 엔텔스.
 */
public class GiftConfirmRes extends CommonInfo implements Serializable {
	private static final long serialVersionUID = 1L;

	private String prodId; // 상품 아이디
	private String prchsId; // 구매ID
	private String resultYn; // update 성공여부

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

}
