package com.skplanet.storeplatform.sac.client.display.vo.other;

import org.hibernate.validator.constraints.NotBlank;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;

public class OtherArtistReq extends CommonInfo {
	private static final long serialVersionUID = 1L;

	@NotBlank
	private String artistId;
	
	private String userKey;

	public String getArtistId() {
		return this.artistId;
	}

	public void setArtistId(String artistId) {
		this.artistId = artistId;
	}

	public String getUserKey() {
		return userKey;
	}

	public void setUserKey(String userKey) {
		this.userKey = userKey;
	}

}
