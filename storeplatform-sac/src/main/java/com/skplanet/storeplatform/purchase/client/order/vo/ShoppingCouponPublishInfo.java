package com.skplanet.storeplatform.purchase.client.order.vo;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;

/**
 * 
 * 발급된 쇼핑 쿠폰 정보
 * 
 * Updated on : 2014. 2. 25. Updated by : 이승택, nTels.
 */
public class ShoppingCouponPublishInfo extends CommonInfo {
	private static final long serialVersionUID = 201402251L;

	private int prchsDtlId; // 구매 상세 ID

	private String publishCode; // 구매자에게 발급 된 쿠폰 발급코드
	private String shippingUrl; // 배송상품의 경우 배송지 URL
	private String addInfo; // 발급 시 추가정보
	private String availStartDt; // 쿠폰사용 유효 시작일시 YYYYMMDDHHMISS
	private String availEndDt; // 쿠폰사용 유효 종료일시 YYYYMMDDHHMISS
	private String bizOrderNo; // BIZ 오더번호
	private String bizSeq; // BIZ 오더번호에 대한 순번

	/**
	 * @return the prchsDtlId
	 */
	public int getPrchsDtlId() {
		return this.prchsDtlId;
	}

	/**
	 * @param prchsDtlId
	 *            the prchsDtlId to set
	 */
	public void setPrchsDtlId(int prchsDtlId) {
		this.prchsDtlId = prchsDtlId;
	}

	/**
	 * @return the publishCode
	 */
	public String getPublishCode() {
		return this.publishCode;
	}

	/**
	 * @param publishCode
	 *            the publishCode to set
	 */
	public void setPublishCode(String publishCode) {
		this.publishCode = publishCode;
	}

	/**
	 * @return the shippingUrl
	 */
	public String getShippingUrl() {
		return this.shippingUrl;
	}

	/**
	 * @param shippingUrl
	 *            the shippingUrl to set
	 */
	public void setShippingUrl(String shippingUrl) {
		this.shippingUrl = shippingUrl;
	}

	/**
	 * @return the addInfo
	 */
	public String getAddInfo() {
		return this.addInfo;
	}

	/**
	 * @param addInfo
	 *            the addInfo to set
	 */
	public void setAddInfo(String addInfo) {
		this.addInfo = addInfo;
	}

	/**
	 * @return the availStartDt
	 */
	public String getAvailStartDt() {
		return this.availStartDt;
	}

	/**
	 * @param availStartDt
	 *            the availStartDt to set
	 */
	public void setAvailStartDt(String availStartDt) {
		this.availStartDt = availStartDt;
	}

	/**
	 * @return the availEndDt
	 */
	public String getAvailEndDt() {
		return this.availEndDt;
	}

	/**
	 * @param availEndDt
	 *            the availEndDt to set
	 */
	public void setAvailEndDt(String availEndDt) {
		this.availEndDt = availEndDt;
	}

	/**
	 * @return the bizOrderNo
	 */
	public String getBizOrderNo() {
		return this.bizOrderNo;
	}

	/**
	 * @param bizOrderNo
	 *            the bizOrderNo to set
	 */
	public void setBizOrderNo(String bizOrderNo) {
		this.bizOrderNo = bizOrderNo;
	}

	/**
	 * @return the bizSeq
	 */
	public String getBizSeq() {
		return this.bizSeq;
	}

	/**
	 * @param bizSeq
	 *            the bizSeq to set
	 */
	public void setBizSeq(String bizSeq) {
		this.bizSeq = bizSeq;
	}

}
