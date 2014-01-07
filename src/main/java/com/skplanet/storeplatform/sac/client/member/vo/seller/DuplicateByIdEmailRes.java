package com.skplanet.storeplatform.sac.client.member.vo.seller;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;

@JsonSerialize(include = Inclusion.NON_NULL)
public class DuplicateByIdEmailRes extends CommonInfo {

	private static final long serialVersionUID = 1L;

	private String isRegistered;

	public String getIsRegistered() {
		return this.isRegistered;
	}

	public void setIsRegistered(String isRegistered) {
		this.isRegistered = isRegistered;
	}

}
