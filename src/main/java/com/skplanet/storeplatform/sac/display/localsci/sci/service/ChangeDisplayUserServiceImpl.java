package com.skplanet.storeplatform.sac.display.localsci.sci.service;

import com.skplanet.storeplatform.sac.display.localsci.sci.repository.ChangeDisplayUserRepository;
import com.skplanet.storeplatform.sac.display.localsci.sci.vo.ChangeDisplayUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

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
		
		// 평점 테이블 회원 ID, KEY 변경
		affectedRow = (Integer) this.changeDisplayUserRepository.changeMbrAvg(changeDisplayUser);
		LOGGER.info("## changeDisplayUserRepository.changeMbrAvg : {}", affectedRow);
		
		// 사용후기 테이블 회원 ID, KEY 변경
		affectedRow = (Integer) this.changeDisplayUserRepository.changeProdNoti(changeDisplayUser);
		LOGGER.info("## changeDisplayUserRepository.changeProdNoti : {}", affectedRow);
		
		// 사용후기 신고 테이블 회원 ID, KEY 변경
		affectedRow = (Integer) this.changeDisplayUserRepository.changeBadNoti(changeDisplayUser);
		LOGGER.info("## changeDisplayUserRepository.changeBadNoti : {}", affectedRow);
		
		// 사용후기 추천 테이블 회원 ID, KEY 변경
		affectedRow = (Integer) this.changeDisplayUserRepository.changeProdNotiGood(changeDisplayUser);
		LOGGER.info("## changeDisplayUserRepository.changeProdNotiGood : {}", affectedRow);
		
		// 메시지 회원 맵핑 테이블 회원ID, KEY 변경
		affectedRow = (Integer) this.changeDisplayUserRepository.changeMsgMbrMapg(changeDisplayUser);
		LOGGER.info("## changeDisplayUserRepository.changeMsgMbrMapg : {}", affectedRow);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public void updateDisplayUserKey(ChangeDisplayUser changeDisplayUser) {
		Integer affectedRow = null;
		
		// 회원 평점 테이블 userKey 변경 전 변경 하고자 하는 prodId 별 userKey 가 존재하는지 확인
		List<Map> prodId = (List<Map>) this.changeDisplayUserRepository.getMbrAvgProdId(changeDisplayUser);
		
		if (prodId != null) {
			for (int i = 0; i < prodId.size(); i++) {
				changeDisplayUser.setProdId((String) prodId.get(i).get("PROD_ID"));
				LOGGER.info("## delete prodId : {}", changeDisplayUser.getProdId());

				// 변경 하고자 하는 prodId 별 userKey 가 존재하면, 해당 데이터를 삭제 함
				affectedRow = (Integer) this.changeDisplayUserRepository.deleteMbrAvg(changeDisplayUser);
				LOGGER.info("## changeDisplayUserRepository.deleteMbrAvg : {}", affectedRow);
			}
		}

		// 평점 테이블 회원 ID, KEY 변경
		affectedRow = (Integer) this.changeDisplayUserRepository.changeMbrAvg(changeDisplayUser);
		LOGGER.info("## changeDisplayUserRepository.changeMbrAvg : {}", affectedRow);
		
		// 사용후기 테이블 회원 ID, KEY 변경
		affectedRow = (Integer) this.changeDisplayUserRepository.changeProdNoti(changeDisplayUser);
		LOGGER.info("## changeDisplayUserRepository.changeProdNoti : {}", affectedRow);
		
		// 사용후기 신고 테이블 회원 ID, KEY 변경
		affectedRow = (Integer) this.changeDisplayUserRepository.changeBadNoti(changeDisplayUser);
		LOGGER.info("## changeDisplayUserRepository.changeBadNoti : {}", affectedRow);
		
		// 사용후기 추천 테이블 회원 ID, KEY 변경
		affectedRow = (Integer) this.changeDisplayUserRepository.changeProdNotiGood(changeDisplayUser);
		LOGGER.info("## changeDisplayUserRepository.changeProdNotiGood : {}", affectedRow);
		
		// 좋아요 테이블 회원 KEY 변경
		affectedRow = (Integer) this.changeDisplayUserRepository.changeSocialLike(changeDisplayUser);
		LOGGER.info("## changeDisplayUserRepository.changeSocialLike : {}", affectedRow);
		
		// 메시지 회원 맵핑 테이블에 이미 등록된 데이터가 있는지 확인
		Integer changeCnt = (Integer) this.changeDisplayUserRepository.searchMsgMbrMapg(changeDisplayUser);
		LOGGER.info("## changeDisplayUserRepository.searchMsgMbrMapg : {}", changeCnt);
		
		if (changeCnt == 0) {
			// 메시지 회원 맵핑 테이블 회원ID, KEY 변경
			affectedRow = (Integer) this.changeDisplayUserRepository.changeMsgMbrMapg(changeDisplayUser);
			LOGGER.info("## changeDisplayUserRepository.changeMsgMbrMapg : {}", affectedRow);
		} else {
			// 메시지 회원 맵핑 테이블 삭제
			affectedRow = (Integer) this.changeDisplayUserRepository.deleteMsgMbrMapg(changeDisplayUser);
			LOGGER.info("## changeDisplayUserRepository.deleteMsgMbrMapg : {}", affectedRow);
		}
	}
}
