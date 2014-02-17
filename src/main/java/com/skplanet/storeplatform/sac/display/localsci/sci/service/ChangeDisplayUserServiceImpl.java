package com.skplanet.storeplatform.sac.display.localsci.sci.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.skplanet.storeplatform.sac.display.localsci.sci.repository.ChangeDisplayUserRepository;
import com.skplanet.storeplatform.sac.display.localsci.sci.vo.ChangeDisplayUser;

/**
 * 
 * ChangeDisplayUser Service 구현체
 * 
 * 전시 회원 ID, KEY 변경 서비스.
 * 
 * Updated on : 2014. 2. 13. Updated by : 김현일, 인크로스
 */
@Service
public class ChangeDisplayUserServiceImpl implements ChangeDisplayUserService {

	@Autowired
	private ChangeDisplayUserRepository changeDisplayUserRepository;

	@Override
	public void changeDisplayUser(ChangeDisplayUser changeDisplayUser) {
		this.changeDisplayUserRepository.changeMbrAvg(changeDisplayUser);
		this.changeDisplayUserRepository.changeProdNoti(changeDisplayUser);
		this.changeDisplayUserRepository.changeProdNotiGood(changeDisplayUser);
		this.changeDisplayUserRepository.changeTenantProdStats(changeDisplayUser);
		this.changeDisplayUserRepository.changeMsgMbrMapg(changeDisplayUser);
	}
}
