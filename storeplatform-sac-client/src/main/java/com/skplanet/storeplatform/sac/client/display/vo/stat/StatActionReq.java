package com.skplanet.storeplatform.sac.client.display.vo.stat;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.NotBlank;

public class StatActionReq {
	@NotNull @NotBlank
	private String userKey;
	
	@NotNull @NotBlank
	private String key;
	
	@NotNull @NotBlank
	@Pattern(regexp = "DP01220001|DP01220002|DP01220003")
	private String regCaseCd;
	
	public String getUserKey() {
		return userKey;
	}
	public void setUserKey(String userKey) {
		this.userKey = userKey;
	}
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public String getRegCaseCd() {
		return regCaseCd;
	}
	public void setRegCaseCd(String regCaseCd) {
		this.regCaseCd = regCaseCd;
	}
	
	
}
