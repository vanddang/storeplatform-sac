package com.skplanet.storeplatform.sac.member.common.vo;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;

/**
 * TB_DP_APP_PROD TB_DP_PROD
 * 
 * aid로 sellerKey 가져오기
 * 
 * Updated on : 2014. 1. 8. Updated by : 한서구, 부르칸.
 */
public class SellerDTO extends CommonInfo {

	private static final long serialVersionUID = 1L;

	private String sellerKey;
	private String koUsWhether;
	private String secedeReasonCode;
	private String secedeReasonMessage;

	public String getSecedeReasonCode() {
		return this.secedeReasonCode;
	}

	public void setSecedeReasonCode(String secedeReasonCode) {
		this.secedeReasonCode = secedeReasonCode;
	}

	public String getSecedeReasonMessage() {
		return this.secedeReasonMessage;
	}

	public void setSecedeReasonMessage(String secedeReasonMessage) {
		this.secedeReasonMessage = secedeReasonMessage;
	}

	/**
	 * @return String : sellerKey
	 */
	public String getSellerKey() {
		return this.sellerKey;
	}

	/**
	 * @param sellerKey
	 *            String : the sellerKey to set
	 */
	public void setSellerKey(String sellerKey) {
		this.sellerKey = sellerKey;
	}

	/**
	 * @return String : koUsWhether
	 */
	public String getKoUsWhether() {
		return this.koUsWhether;
	}

	/**
	 * @param koUsWhether
	 *            String : the koUsWhether to set
	 */
	public void setKoUsWhether(String koUsWhether) {
		this.koUsWhether = koUsWhether;
	}

}
