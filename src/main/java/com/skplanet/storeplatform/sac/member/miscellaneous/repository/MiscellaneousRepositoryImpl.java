package com.skplanet.storeplatform.sac.member.miscellaneous.repository;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.skplanet.storeplatform.framework.core.persistence.dao.CommonDAO;

/**
 * 기타 기능 관련 인터페이스 구현체
 * 
 * Updated on : 2014. 1. 9. Updated by : 김다슬, 인크로스.
 */
@SuppressWarnings("unchecked")
@Component
@Transactional
public class MiscellaneousRepositoryImpl implements MiscellaneousRepository {
	@Autowired
	@Qualifier("sac")
	private CommonDAO commonDao;

	@Override
	public String getUaCode(String deviceModelNo) {
		return (String) this.commonDao.queryForObject("Miscellaneous.getUaCode", deviceModelNo);
	}

	@Override
	public void insertPhoneAuthCode(HashMap<String, String> phoneAuthCodeInfo) {
		this.commonDao.insert("Miscellaneous.insertPhoneAuthCode", phoneAuthCodeInfo);
	}

	@Override
	public HashMap<String, String> getPhoneAuthYn(String userKey) {
		return (HashMap<String, String>) this.commonDao.queryForObject("Miscellaneous.getSmsAuthYn", userKey);
	}

	@Override
	public void mergeIntoPhoneAuthCode(HashMap<String, String> phoneAuthCodeInfo) {
		this.commonDao.update("Miscellanous.mergeIntoPhoneAuthCode", phoneAuthCodeInfo);
	}
}
