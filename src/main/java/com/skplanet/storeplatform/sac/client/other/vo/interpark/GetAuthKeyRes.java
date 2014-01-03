package com.skplanet.storeplatform.sac.client.other.vo.interpark;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;

public class GetAuthKeyRes extends CommonInfo {

	private static final long serialVersionUID = 1L;

	private String certKey;

	public String getCertKey() {
		return this.certKey;
	}

	public void setCertKey(String certKey) {
		this.certKey = certKey;
	}

}
