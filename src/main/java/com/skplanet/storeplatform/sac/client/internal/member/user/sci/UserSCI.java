package com.skplanet.storeplatform.sac.client.internal.member.user.sci;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.skplanet.storeplatform.framework.core.proxy.SCI;
import com.skplanet.storeplatform.sac.client.internal.member.user.vo.RemoveSSOCredentialSacReq;
import com.skplanet.storeplatform.sac.client.internal.member.user.vo.RemoveSSOCredentialSacRes;

/**
 * 회원 내부 메소드 호출 Interface.
 * 
 * Updated on : 2015. 9. 22. Updated by : 반범진.
 */
@SCI
@RequestMapping(value = "/member/user/sci")
public interface UserSCI {
	/**
	 * <pre>
	 * 회원의 SSOCredential 정보를 삭제 한다.
	 * </pre>
	 * 
	 * @param request
	 *            RemoveSSOCredentialSacReq
	 * @return RemoveSSOCredentialSacRes
	 */
	@RequestMapping(value = "/removeSSOCredential", method = RequestMethod.POST)
	@ResponseBody
	public RemoveSSOCredentialSacRes removeSSOCredential(@RequestBody @Validated RemoveSSOCredentialSacReq request);
}
