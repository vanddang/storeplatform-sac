package com.skplanet.storeplatform.sac.display.localsci.sci.service;

import java.util.List;
import java.util.Map;

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
	}

	@Override
	public void updateDisplayUserKey(ChangeDisplayUser changeDisplayUser) {
		// 회원 평점 테이블 userKey 변경 전 변경 하고자 하는 prodId 별 userKey 가 존재하는지 확인
		List<Map> prodId = (List<Map>) this.changeDisplayUserRepository.getMbrAvgProdId(changeDisplayUser);
		// LOGGER.info("## changeDisplayUserRepository.getMbrAvgProdId : {}", prodId.size());

		Integer affectedRow = null;
		if (prodId != null) {
			// 변경 하고자 하는 prodId 별 userKey 가 존재하면, 해당 데이터를 삭제 함
			for (int i = 0; i < prodId.size(); i++) {
				changeDisplayUser.setProdId((String) prodId.get(i).get("PROD_ID"));
				LOGGER.info("## delete prodId : {}", changeDisplayUser.getProdId());

				affectedRow = (Integer) this.changeDisplayUserRepository.deleteMbrAvg(changeDisplayUser);
				LOGGER.info("## changeDisplayUserRepository.deleteMbrAvg : {}", affectedRow);
			}
		}

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
	}
}
