package com.skplanet.storeplatform.sac.client.internal.display.localsci.sci;

import org.springframework.validation.annotation.Validated;

import com.skplanet.storeplatform.framework.core.proxy.SCI;
import com.skplanet.storeplatform.sac.client.internal.display.localsci.vo.ChangeDisplayUserSacReq;
import com.skplanet.storeplatform.sac.client.internal.display.localsci.vo.ChangeDisplayUserSacReq.GroupChangeDisplayUserId;
import com.skplanet.storeplatform.sac.client.internal.display.localsci.vo.ChangeDisplayUserSacReq.GroupChangeDisplayUserKey;

/**
 * 
 * ChangeDisplayUser SCI
 * 
 * 전시 테이블에 사용되고있는 UserId, UserKey를 변경하기 위해서 회원에 SCI를 제공
 * 
 * Updated on : 2014. 2. 13. Updated by : 김현일, 인크로스
 */
@SCI
public interface ChangeDisplayUserSCI {

	/**
	 * 
	 * <pre>
	 * 전시테이블 중 회원 ID 변경.
	 * </pre>
	 * 
	 * @param changeDisplayUserSacReq
	 *            changeDisplayUserSacReq
	 * 
	 */
	public void changeUserId(
			@Validated({ GroupChangeDisplayUserId.class }) ChangeDisplayUserSacReq changeDisplayUserSacReq);

	/**
	 * 
	 * <pre>
	 * 전시테이블 중 회원 KEY 변경.
	 * </pre>
	 * 
	 * @param changeDisplayUserSacReq
	 *            changeDisplayUserSacReq
	 * 
	 */
	public void changeUserKey(
			@Validated({ GroupChangeDisplayUserKey.class }) ChangeDisplayUserSacReq changeDisplayUserSacReq);

}
