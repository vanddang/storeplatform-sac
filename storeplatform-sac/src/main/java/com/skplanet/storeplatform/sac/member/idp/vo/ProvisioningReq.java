package com.skplanet.storeplatform.sac.member.idp.vo;

import java.util.HashMap;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;

/**
 * 
 * IDP에서 전달되는 Provisioning 및 Rx 처리를 위해 SAC와 통신하는 Request VO
 * 
 * Updated on : 2014. 1. 8. Updated by : 임재호, 인크로스.
 */
public class ProvisioningReq extends CommonInfo {

	private static final long serialVersionUID = 1L;

	/**
	 * Provisioning 및 Rx기능을 구분하기 위한 cmd
	 */
	private String cmd;
	/**
	 * IDP에서 전달된 Query String (key=value&key=value)
	 */
	private HashMap<String, String> reqParam;

	/**
	 * @return the cmd
	 */
	public String getCmd() {
		return this.cmd;
	}

	/**
	 * @param cmd
	 *            the cmd to set
	 */
	public void setCmd(String cmd) {
		this.cmd = cmd;
	}

	/**
	 * @return the reqParam
	 */
	public HashMap<String, String> getReqParam() {
		return this.reqParam;
	}

	/**
	 * @param reqParam
	 *            the reqParam to set
	 */
	public void setReqParam(HashMap<String, String> reqParam) {
		this.reqParam = reqParam;
	}

}
