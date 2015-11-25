package com.skplanet.storeplatform.sac.client.display.vo.stat;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.NotBlank;

public class StatDetailV2Req {
	@NotNull @NotBlank
	private String key;

	@NotNull @NotBlank
	@Pattern(regexp = "DP01210001|DP01210002|DP01210003|DP01210004")
	private String clsf;
	
	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getClsf() {
		return clsf;
	}

	public void setClsf(String clsf) {
		this.clsf = clsf;
	}	
	
}
