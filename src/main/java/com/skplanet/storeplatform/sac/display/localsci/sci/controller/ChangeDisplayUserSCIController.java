package com.skplanet.storeplatform.sac.display.localsci.sci.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;

import com.skplanet.storeplatform.framework.integration.bean.LocalSCI;
import com.skplanet.storeplatform.sac.client.internal.display.localsci.sci.ChangeDisplayUserSCI;
import com.skplanet.storeplatform.sac.client.internal.display.localsci.vo.ChangeDisplayUserSacReq;
import com.skplanet.storeplatform.sac.client.internal.display.localsci.vo.ChangeDisplayUserSacRes;
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

	private static final Logger LOGGER = LoggerFactory.getLogger(ChangeDisplayUserSCIController.class);

	@Autowired
	private ChangeDisplayUserService changeDisplayUserService;

	@Override
	public ChangeDisplayUserSacRes changeUserId(
			@Validated(GroupChangeDisplayUserId.class) ChangeDisplayUserSacReq changeDisplayUserSacReq) {

		LOGGER.debug("## 회원 ID 변경 ##");
		LOGGER.debug("## req : {}", changeDisplayUserSacReq);

		ChangeDisplayUser changeDisplayUser = new ChangeDisplayUser();
		changeDisplayUser.setTenantId(changeDisplayUserSacReq.getTenantId());
		changeDisplayUser.setNewUserId(changeDisplayUserSacReq.getNewUserId());
		changeDisplayUser.setOldUserId(changeDisplayUserSacReq.getOldUserId());
		this.changeDisplayUserService.changeDisplayUser(changeDisplayUser);

		ChangeDisplayUserSacRes changeDisplayUserSacRes = new ChangeDisplayUserSacRes();
		changeDisplayUserSacRes.setResultStatus("success");

		return changeDisplayUserSacRes;
	}

	@Override
	public ChangeDisplayUserSacRes changeUserKey(
			@Validated(GroupChangeDisplayUserKey.class) ChangeDisplayUserSacReq changeDisplayUserSacReq) {

		LOGGER.debug("## 회원 KEY 변경 ##");
		LOGGER.debug("## req : {}", changeDisplayUserSacReq);

		ChangeDisplayUser changeDisplayUser = new ChangeDisplayUser();
		changeDisplayUser.setTenantId(changeDisplayUserSacReq.getTenantId());
		changeDisplayUser.setNewUserKey(changeDisplayUserSacReq.getNewUseKey());
		changeDisplayUser.setOldUserKey(changeDisplayUserSacReq.getOldUserKey());

		this.changeDisplayUserService.changeDisplayUser(changeDisplayUser);

		ChangeDisplayUserSacRes changeDisplayUserSacRes = new ChangeDisplayUserSacRes();
		changeDisplayUserSacRes.setResultStatus("success");

		return changeDisplayUserSacRes;
	}

}
