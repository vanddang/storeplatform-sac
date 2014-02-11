package com.skplanet.storeplatform.sac.client.member.vo.user;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;

/**
 * [RESPONSE] ID 찾기
 * 
 * Updated on : 2014. 1. 6. Updated by : 강신완, 부르칸.
 */
@JsonSerialize(include = Inclusion.NON_NULL)
public class SearchPasswordSacRes extends CommonInfo {

	private static final long serialVersionUID = 1L;
	private String userPw; // 사용자PW (임시)
	private String sendMean; // 발송수단
	private String sendInfo; // 발송정보

	public String getUserPw() {
		return this.userPw;
	}

	public void setUserPw(String userPw) {
		this.userPw = userPw;
	}

	public String getSendMean() {
		return this.sendMean;
	}

	public void setSendMean(String sendMean) {
		this.sendMean = sendMean;
	}

	public String getSendInfo() {
		return this.sendInfo;
	}

	public void setSendInfo(String sendInfo) {
		this.sendInfo = sendInfo;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
