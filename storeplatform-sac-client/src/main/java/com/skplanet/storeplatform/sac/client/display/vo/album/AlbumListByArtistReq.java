package com.skplanet.storeplatform.sac.client.display.vo.album;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

public class AlbumListByArtistReq {
	
	@NotNull @NotBlank
	private String artistId;
	
	public String getArtistId() {
		return artistId;
	}
	
	public void setArtistId(String artistId) {
		this.artistId = artistId;
	}
	
}
