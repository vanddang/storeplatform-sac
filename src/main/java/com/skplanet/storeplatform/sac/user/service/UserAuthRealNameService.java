package com.skplanet.storeplatform.sac.user.service;

import com.skplanet.storeplatform.sac.client.user.vo.AuthRealNameSmsSendRequestVO;
import com.skplanet.storeplatform.sac.client.user.vo.AuthRealNameSmsSendResponseVO;

/**
 * 휴대폰 인증 SMS Service 인터페이스(CoreStoreBusiness)
 * 
 * Updated on : 2013. 12. 23. Updated by : 김다슬, 인크로스.
 */
public interface UserAuthRealNameService {

	AuthRealNameSmsSendResponseVO smsSend(AuthRealNameSmsSendRequestVO request);

}
