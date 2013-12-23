package com.skplanet.storeplatform.sac.client.user.vo;

import java.io.Serializable;

import com.skplanet.storeplatform.framework.core.annotation.ProtobufMapping;
import com.skplanet.storeplatform.sac.client.user.miscellaneous.vo.AuthRealNameSmsSendRequestProto;

/**
 * 실명 인증용 휴대폰 인증 SMS 발송 RequestVO
 * 
 * Updated on : 2013-12-20 Updated by : 김다슬, 인크로스.
 */
@ProtobufMapping(AuthRealNameSmsSendRequestProto.AuthRealNameSmsSendRequest.class)
public class AuthRealNameSmsSendRequestVO implements Serializable {
	private static final long serialVersionUID = 1L;

	private RealNameInfoVO realNameInfo;

	public RealNameInfoVO getRealNameInfo() {
		return this.realNameInfo;
	}

	public void setRealNameInfo(RealNameInfoVO realNameInfo) {
		this.realNameInfo = realNameInfo;
	}
}
