package com.skplanet.storeplatform.sac.member.idp.vo;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;

/**
 * 
 * IDP에서 전달되는 Provisioning 및 Rx 처리를 위해 SAC와 통신하는 Response VO
 * 
 * Updated on : 2014. 1. 8. Updated by : 임재호, 인크로스.
 */
public class ProvisioningRes extends CommonInfo {

	private static final long serialVersionUID = 1L;
	/**
	 * 기존 IDP Provisioning 수신시 처리 결과 코드
	 */
	private String result;
	/**
	 * One ID Rx 수신시 처리 결과 코드
	 */
	private ImResult imResult;

	/**
	 * @return the result
	 */
	public String getResult() {
		return this.result;
	}

	/**
	 * @param result
	 *            the result to set
	 */
	public void setResult(String result) {
		this.result = result;
	}

	/**
	 * @return the imResult
	 */
	public ImResult getImResult() {
		return this.imResult;
	}

	/**
	 * @param imResult
	 *            the imResult to set
	 */
	public void setImResult(ImResult imResult) {
		this.imResult = imResult;
	}
}
