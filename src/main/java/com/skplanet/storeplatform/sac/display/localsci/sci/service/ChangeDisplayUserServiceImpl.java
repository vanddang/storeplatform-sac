package com.skplanet.storeplatform.sac.display.localsci.sci.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

	private static final Logger LOGGER = LoggerFactory.getLogger(ChangeDisplayUserServiceImpl.class);

	@Autowired
	private ChangeDisplayUserRepository changeDisplayUserRepository;

	@Override
	public void updateDisplayUserId(ChangeDisplayUser changeDisplayUser) {
		LOGGER.info("## changeDisplayUserId start ##");
		Integer affectedRow = null;
		affectedRow = (Integer) this.changeDisplayUserRepository.changeMbrAvg(changeDisplayUser);
		LOGGER.info("## changeDisplayUserRepository.changeMbrAvg : {}", affectedRow);
		affectedRow = (Integer) this.changeDisplayUserRepository.changeProdNoti(changeDisplayUser);
		LOGGER.info("## changeDisplayUserRepository.changeProdNoti : {}", affectedRow);
		affectedRow = (Integer) this.changeDisplayUserRepository.changeBadNoti(changeDisplayUser);
		LOGGER.info("## changeDisplayUserRepository.changeBadNoti : {}", affectedRow);
		affectedRow = (Integer) this.changeDisplayUserRepository.changeProdNotiGood(changeDisplayUser);
		LOGGER.info("## changeDisplayUserRepository.changeProdNotiGood : {}", affectedRow);
		affectedRow = (Integer) this.changeDisplayUserRepository.changeTenantProdStats(changeDisplayUser);
		LOGGER.info("## changeDisplayUserRepository.changeTenantProdStats : {}", affectedRow);
		affectedRow = (Integer) this.changeDisplayUserRepository.changeMsgMbrMapg(changeDisplayUser);
		LOGGER.info("## changeDisplayUserRepository.changeMsgMbrMapg : {}", affectedRow);
		LOGGER.info("## changeDisplayUserId end ##");
	}

	@Override
	public void updateDisplayUserKey(ChangeDisplayUser changeDisplayUser) {
		LOGGER.info("## changeDisplayUserKey start ##");
		Integer affectedRow = null;
		affectedRow = (Integer) this.changeDisplayUserRepository.changeMbrAvg(changeDisplayUser);
		LOGGER.info("## changeDisplayUserRepository.changeMbrAvg : {}", affectedRow);
		affectedRow = (Integer) this.changeDisplayUserRepository.changeProdNoti(changeDisplayUser);
		LOGGER.info("## changeDisplayUserRepository.changeProdNoti : {}", affectedRow);
		affectedRow = (Integer) this.changeDisplayUserRepository.changeBadNoti(changeDisplayUser);
		LOGGER.info("## changeDisplayUserRepository.changeBadNoti : {}", affectedRow);
		affectedRow = (Integer) this.changeDisplayUserRepository.changeProdNotiGood(changeDisplayUser);
		LOGGER.info("## changeDisplayUserRepository.changeProdNotiGood : {}", affectedRow);
		affectedRow = (Integer) this.changeDisplayUserRepository.changeMsgMbrMapg(changeDisplayUser);
		LOGGER.info("## changeDisplayUserRepository.changeMsgMbrMapg : {}", affectedRow);
		LOGGER.info("## changeDisplayUserKey end ##");
	}
}
