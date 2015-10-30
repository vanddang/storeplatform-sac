package com.skplanet.storeplatform.sac.client.internal.display.localsci.vo;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;

/**
 * 정액제 상품 정보 조회 VO.
 * 
 * Updated on : 2014. 12. 28. Updated by : 김형식 , 지티소프트
 */
public class FreePass extends CommonInfo {
	private static final long serialVersionUID = 1L;

	private String prodId;
	private String possLendClsfCd;
	private String cmpxProdBookClsfCd;
	private String cmpxProdClsfCd;
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
	 * @return the possLendClsfCd
	 */
	public String getPossLendClsfCd() {
		return possLendClsfCd;
	}

	/**
	 * @param possLendClsfCd the possLendClsfCd to set
	 */
	public void setPossLendClsfCd(String possLendClsfCd) {
		this.possLendClsfCd = possLendClsfCd;
	}

	/**
	 * @return the cmpxProdBookClsfCd
	 */
	public String getCmpxProdBookClsfCd() {
		return cmpxProdBookClsfCd;
	}

	/**
	 * @param cmpxProdBookClsfCd the cmpxProdBookClsfCd to set
	 */
	public void setCmpxProdBookClsfCd(String cmpxProdBookClsfCd) {
		this.cmpxProdBookClsfCd = cmpxProdBookClsfCd;
	}

	/**
	 * @return the cmpxProdClsfCd
	 */
	public String getCmpxProdClsfCd() {
		return cmpxProdClsfCd;
	}

	/**
	 * @param cmpxProdClsfCd the cmpxProdClsfCd to set
	 */
	public void setCmpxProdClsfCd(String cmpxProdClsfCd) {
		this.cmpxProdClsfCd = cmpxProdClsfCd;
	}
	
	
}
