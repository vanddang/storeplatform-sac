package com.skplanet.storeplatform.sac.display.localsci.sci.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;

import com.skplanet.storeplatform.framework.integration.bean.LocalSCI;
import com.skplanet.storeplatform.sac.client.internal.display.localsci.sci.ChangeDisplayUserSCI;
import com.skplanet.storeplatform.sac.client.internal.display.localsci.vo.ChangeDisplayUserSacReq;
import com.skplanet.storeplatform.sac.client.internal.display.localsci.vo.ChangeDisplayUserSacReq.GroupChangeDisplayUserId;
import com.skplanet.storeplatform.sac.client.internal.display.localsci.vo.ChangeDisplayUserSacReq.GroupChangeDisplayUserKey;
import com.skplanet.storeplatform.sac.display.localsci.sci.service.ChangeDisplayUserService;
import com.skplanet.storeplatform.sac.display.localsci.sci.vo.ChangeDisplayUser;

/**
 * 
 * ChangeDisplayUserSCI Controller
 * 
 * 전시 회원 ID, 회원 KEY 변경.
 * 
 * Updated on : 2014. 2. 13. Updated by : 김현일, 인크로스
 */
@LocalSCI
public class ChangeDisplayUserSCIController implements ChangeDisplayUserSCI {
	@Autowired
	private ChangeDisplayUserService changeDisplayUserService;

	@Override
	public void changeUserId(@Validated(GroupChangeDisplayUserId.class) ChangeDisplayUserSacReq changeDisplayUserSacReq) {
		ChangeDisplayUser changeDisplayUser = new ChangeDisplayUser();
		changeDisplayUser.setTenantId(changeDisplayUserSacReq.getTenantId());
		changeDisplayUser.setNewUserId(changeDisplayUserSacReq.getNewUserId());
		changeDisplayUser.setOldUserKey(changeDisplayUserSacReq.getOldUserKey());
		
		this.changeDisplayUserService.updateDisplayUserId(changeDisplayUser);
	}

	@Override
	public void changeUserKey(
			@Validated(GroupChangeDisplayUserKey.class) ChangeDisplayUserSacReq changeDisplayUserSacReq) {
		ChangeDisplayUser changeDisplayUser = new ChangeDisplayUser();
		changeDisplayUser.setTenantId(changeDisplayUserSacReq.getTenantId());
		changeDisplayUser.setNewUserKey(changeDisplayUserSacReq.getNewUseKey());
		changeDisplayUser.setOldUserKey(changeDisplayUserSacReq.getOldUserKey());
		
		this.changeDisplayUserService.updateDisplayUserKey(changeDisplayUser);
	}
}
