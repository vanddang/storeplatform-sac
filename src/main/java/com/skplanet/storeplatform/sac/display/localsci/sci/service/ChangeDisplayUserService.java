package com.skplanet.storeplatform.sac.display.localsci.sci.service;

import com.skplanet.storeplatform.sac.display.localsci.sci.vo.ChangeDisplayUser;

/**
 * 
 * ChangeDisplayUser Service
 * 
 * 전시 회원 ID, KEY 변경 서비스.
 * 
 * Updated on : 2014. 2. 13. Updated by : 김현일, 인크로스
 */
public interface ChangeDisplayUserService {

	/**
	 * 
	 * <pre>
	 * 전시테이블 중 회원 ID 변경.
	 * </pre>
	 * 
	 * @param changeDisplayUser
	 *            changeDisplayUser
	 */
	public void changeDisplayUserId(ChangeDisplayUser changeDisplayUser);

	/**
	 * 
	 * <pre>
	 * 전시테이블 중 회원 KEY 변경.
	 * </pre>
	 * 
	 * @param changeDisplayUser
	 *            changeDisplayUser
	 */
	public void changeDisplayUserKey(ChangeDisplayUser changeDisplayUser);
}
