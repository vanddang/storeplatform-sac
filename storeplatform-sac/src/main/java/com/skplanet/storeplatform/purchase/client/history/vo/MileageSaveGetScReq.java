package com.skplanet.storeplatform.purchase.client.history.vo;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;

/**
 * T마일리지 조회 요청 VO
 * 
 * Updated on : 2013. 12. 13. Updated by : ntels_yjw
 */
public class MileageSaveGetScReq extends CommonInfo {

	private static final long serialVersionUID = 1L;

	private String tenantId; // 테넌트ID
	private String typeCd; // 마일리지타입
	private String prchsId; // 구매ID

	/**
	 * @return the tenantId
	 */
	public String getTenantId() {
		return this.tenantId;
	}

	/**
	 * @param tenantId
	 *            the tenantId to set
	 */
	public void setTenantId(String tenantId) {
		this.tenantId = tenantId;
	}

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

}
