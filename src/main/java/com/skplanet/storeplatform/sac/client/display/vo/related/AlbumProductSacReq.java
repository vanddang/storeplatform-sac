package com.skplanet.storeplatform.sac.client.display.vo.related;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

public class AlbumProductSacReq {
	@NotNull @NotBlank
	private String albumId;
	
	private String prodGradeCd;

	private Integer offset;
	private Integer count;
	
	public String getAlbumId() {
		return albumId;
	}

	public void setAlbumId(String albumId) {
		this.albumId = albumId;
	}

	public String getProdGradeCd() {
		return prodGradeCd;
	}

	public void setProdGradeCd(String prodGradeCd) {
		this.prodGradeCd = prodGradeCd;
	}

	public Integer getOffset() {
		return offset;
	}

	public void setOffset(Integer offset) {
		this.offset = offset;
	}

	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}
	
}
