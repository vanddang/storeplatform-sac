package com.skplanet.storeplatform.sac.client.user.vo;

import java.io.Serializable;

import com.skplanet.storeplatform.framework.core.annotation.ProtobufMapping;
import com.skplanet.storeplatform.sac.client.user.miscellaneous.vo.AuthRealNameSmsSendResponseProto;

/**
 * 실명 인증용 휴대폰 인증 SMS 발송 response VO
 * 
 * Updated on : 2013-12-20 Updated by : 김다슬, 인크로스.
 */
@ProtobufMapping(AuthRealNameSmsSendResponseProto.AuthRealNameSmsSendResponse.class)
public class AuthRealNameSmsSendResponseVO implements Serializable {

	private static final long serialVersionUID = 1L;

	private String resultCode;
	private String resultMessage;
	private String kmcSmsAuth;
	private String checkParam1;
	private String checkParam2;
	private String checkParam3;

	public String getResultCode() {
		return this.resultCode;
	}

	public void setResultCode(String resultCode) {
		this.resultCode = resultCode;
	}

	public String getResultMessage() {
		return this.resultMessage;
	}

	public void setResultMessage(String resultMessage) {
		this.resultMessage = resultMessage;
	}

	public String getKmcSmsAuth() {
		return this.kmcSmsAuth;
	}

	public void setKmcSmsAuth(String kmcSmsAuth) {
		this.kmcSmsAuth = kmcSmsAuth;
	}

	public String getCheckParam1() {
		return this.checkParam1;
	}

	public void setCheckParam1(String checkParam1) {
		this.checkParam1 = checkParam1;
	}

	public String getCheckParam2() {
		return this.checkParam2;
	}

	public void setCheckParam2(String checkParam2) {
		this.checkParam2 = checkParam2;
	}

	public String getCheckParam3() {
		return this.checkParam3;
	}

	public void setCheckParam3(String checkParam3) {
		this.checkParam3 = checkParam3;
	}

}
