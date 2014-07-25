package com.skplanet.storeplatform.sac.client.purchase.cancel.vo;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;

/**
 * 즉시이용정지 응답 VO
 * 
 * Updated on : 2013. 12. 13. Updated by : ntels_yjw
 */
public class ImmediatelyUseStopSacRes extends CommonInfo {

	private static final long serialVersionUID = 1L;

	private String prchsId;

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
