package com.skplanet.storeplatform.sac.client.internal.display.localsci.vo;

import java.util.List;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;

/**
 * PossLendProductInfoSacReq Value Object
 * 
 * 소장/대여 상품 정보 조회 VO
 * 
 * Updated on : 2014. 4. 15. Updated by : 홍지호, 엔텔스
 */
public class PossLendProductInfoSacReq extends CommonInfo {

	private static final long serialVersionUID = 1L;
	private List<String> prodIdList;
	private String prodId;
	private String tenantId;
	private String langCd;
	private List<String> possLendClsfCdList;
	private String possLendClsfCd;
	private String topMenuId;

	/**
	 * @return the prodIdList
	 */
	public List<String> getProdIdList() {
		return this.prodIdList;
	}

	/**
	 * @param prodIdList
	 *            the prodIdList to set
	 */
	public void setProdIdList(List<String> prodIdList) {
		this.prodIdList = prodIdList;
	}

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
	 * @return the langCd
	 */
	public String getLangCd() {
		return this.langCd;
	}

	/**
	 * @param langCd
	 *            the langCd to set
	 */
	public void setLangCd(String langCd) {
		this.langCd = langCd;
	}

	/**
	 * @return the possLendClsfCdList
	 */
	public List<String> getPossLendClsfCdList() {
		return this.possLendClsfCdList;
	}

	/**
	 * @param possLendClsfCdList
	 *            the possLendClsfCdList to set
	 */
	public void setPossLendClsfCdList(List<String> possLendClsfCdList) {
		this.possLendClsfCdList = possLendClsfCdList;
	}

	/**
	 * @return the possLendClsfCd
	 */
	public String getPossLendClsfCd() {
		return this.possLendClsfCd;
	}

	/**
	 * @param possLendClsfCd
	 *            the possLendClsfCd to set
	 */
	public void setPossLendClsfCd(String possLendClsfCd) {
		this.possLendClsfCd = possLendClsfCd;
	}

	/**
	 * @return the topMenuId
	 */
	public String getTopMenuId() {
		return this.topMenuId;
	}

	/**
	 * @param topMenuId
	 *            the topMenuId to set
	 */
	public void setTopMenuId(String topMenuId) {
		this.topMenuId = topMenuId;
	}

}
