package com.skplanet.storeplatform.sac.client.purchase.vo.history;

import java.io.Serializable;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;

/**
 * 
 * 선물수신확인 응답.
 * 
 * Updated on : 2013. 12. 28. Updated by : 조용진, 엔텔스.
 */
public class GiftReceiveSacRes extends CommonInfo implements Serializable {
	private static final long serialVersionUID = 1L;

	private String recvDt; // 선물수신일시

	/**
	 * @return the recvDt
	 */
	public String getRecvDt() {
		return this.recvDt;
	}

	/**
	 * @param recvDt
	 *            the recvDt to set
	 */
	public void setRecvDt(String recvDt) {
		this.recvDt = recvDt;
	}

}
