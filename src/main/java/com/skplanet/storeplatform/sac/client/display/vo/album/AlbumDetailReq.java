package com.skplanet.storeplatform.sac.client.display.vo.album;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

public class AlbumDetailReq {
	
	@NotNull @NotBlank
	private String albumId;
	
	private String userKey;

	public String getAlbumId() {
		return albumId;
	}

	public void setAlbumId(String albumId) {
		this.albumId = albumId;
	}

	public String getUserKey() {
		return userKey;
	}

	public void setUserKey(String userKey) {
		this.userKey = userKey;
	}
	
	
}
