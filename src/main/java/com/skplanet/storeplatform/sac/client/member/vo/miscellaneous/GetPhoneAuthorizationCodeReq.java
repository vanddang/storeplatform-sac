package com.skplanet.storeplatform.sac.client.member.vo.miscellaneous;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;

/**
 * 
 * [REQUEST] 휴대폰 인증 SMS 발송
 * 
 * Updated on : 2014. 1. 6. Updated by : 김다슬, 인크로스.
 */
@JsonSerialize(include = Inclusion.NON_NULL)
public class GetPhoneAuthorizationCodeReq {
	private static final long serialVersionUID = 1L;

	private String userPhone; // 휴대폰 번호
	private String userTelecom; // 통신사 코드
	private String srcId; // 요청측ID
	private String teleSvcId; // 텔레서비스ID

	public String getUserPhone() {
		return this.userPhone;
	}

	public void setUserPhone(String userPhone) {
		this.userPhone = userPhone;
	}

	public String getUserTelecom() {
		return this.userTelecom;
	}

	public void setUserTelecom(String userTelecom) {
		this.userTelecom = userTelecom;
	}

	public String getSrcId() {
		return this.srcId;
	}

	public void setSrcId(String srcId) {
		this.srcId = srcId;
	}

	public String getTeleSvcId() {
		return this.teleSvcId;
	}

	public void setTeleSvcId(String teleSvcId) {
		this.teleSvcId = teleSvcId;
	}

}
