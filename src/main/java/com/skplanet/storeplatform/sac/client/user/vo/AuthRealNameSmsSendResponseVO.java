package com.skplanet.storeplatform.sac.client.user.vo;

import java.io.Serializable;

import com.skplanet.storeplatform.framework.core.annotation.ProtobufMapping;
import com.skplanet.storeplatform.sac.client.user.miscellaneous.vo.AuthRealNameSmsSendRequestProto;

/**
 * 실명 인증용 휴대폰 인증 SMS 발송 response VO
 * 
 * Updated on : 2013-12-20 Updated by : 김다슬, 인크로스.
 */
@ProtobufMapping(AuthRealNameSmsSendRequestProto.AuthRealNameSmsSendRequest.class)
public class AuthRealNameSmsSendResponseVO implements Serializable {

	private static final long serialVersionUID = 1L;

	private String resultCode;
	private String resultMessage;

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

}
