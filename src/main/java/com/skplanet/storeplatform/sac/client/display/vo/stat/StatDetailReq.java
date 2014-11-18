package com.skplanet.storeplatform.sac.client.display.vo.stat;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

public class StatDetailReq {
	@NotNull @NotBlank
	private String key;

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}	
}
