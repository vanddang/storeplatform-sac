package com.skplanet.storeplatform.sac.client.other.vo.interpark;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;

/**
 * 
 * GetAuthKeyRes Value Object
 * 
 * Updated on : 2014. 1. 20. Updated by : 김현일, 인크로스.
 */
public class GetAuthKeyRes extends CommonInfo {

	private static final long serialVersionUID = 1L;

	private String certKey;

	/**
	 * @return String
	 */
	public String getCertKey() {
		return this.certKey;
	}

	/**
	 * @param certKey
	 *            certKey
	 */
	public void setCertKey(String certKey) {
		this.certKey = certKey;
	}

}
