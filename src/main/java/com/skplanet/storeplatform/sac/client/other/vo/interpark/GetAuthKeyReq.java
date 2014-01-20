package com.skplanet.storeplatform.sac.client.other.vo.interpark;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;

/**
 * 
 * GetAuthKeyReq Value Object
 * 
 * Updated on : 2014. 1. 20. Updated by : 김현일, 인크로스.
 */
public class GetAuthKeyReq extends CommonInfo {

	private static final long serialVersionUID = 1L;

	private String type;
	private String revOrdNo;
	private String ebFileNo;

	/**
	 * @return String
	 */
	public String getType() {
		return this.type;
	}

	/**
	 * @param type
	 *            type
	 */
	public void setType(String type) {
		this.type = type;
	}

	/**
	 * @return String
	 */
	public String getRevOrdNo() {
		return this.revOrdNo;
	}

	/**
	 * @param revOrdNo
	 *            revOrdNo
	 */
	public void setRevOrdNo(String revOrdNo) {
		this.revOrdNo = revOrdNo;
	}

	/**
	 * @return String
	 */
	public String getEbFileNo() {
		return this.ebFileNo;
	}

	/**
	 * @param ebFileNo
	 *            ebFileNo
	 */
	public void setEbFileNo(String ebFileNo) {
		this.ebFileNo = ebFileNo;
	}

}
