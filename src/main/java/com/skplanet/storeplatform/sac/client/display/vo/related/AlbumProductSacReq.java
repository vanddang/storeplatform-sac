package com.skplanet.storeplatform.sac.client.display.vo.related;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

public class AlbumProductSacReq {
	@NotNull @NotBlank
	private String albumId;

	public String getAlbumId() {
		return albumId;
	}

	public void setAlbumId(String albumId) {
		this.albumId = albumId;
	}
}
