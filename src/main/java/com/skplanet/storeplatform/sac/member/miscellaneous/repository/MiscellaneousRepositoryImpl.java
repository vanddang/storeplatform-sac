package com.skplanet.storeplatform.sac.member.miscellaneous.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.skplanet.storeplatform.framework.core.persistence.dao.CommonDAO;
import com.skplanet.storeplatform.sac.member.miscellaneous.vo.ServiceAuth;

/**
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
	public void insertPhoneAuthCode(ServiceAuth serviceAuthInfo) {
		this.commonDao.insert("Miscellaneous.insertPhoneAuthCode", serviceAuthInfo);
	}

	@Override
	public ServiceAuth getPhoneAuthYn(ServiceAuth serviceAuthInfo) {
		return (ServiceAuth) this.commonDao.queryForObject("Miscellaneous.getPhoneAuthYn", serviceAuthInfo);
	}

	@Override
	public void updatePhoneAuthYn(String authSeq) {
		this.commonDao.update("Miscellaneous.updatePhoneAuthYn", authSeq);
	}

	@Override
	public String getEmailAuthYn(String mbrNo) {
		return null;
	}

	@Override
	public void insertServiceAuthCode(ServiceAuth serviceAuthInfo) {
		this.commonDao.insert("Miscellaneous.insertServiceAuthCode", serviceAuthInfo);
	}
}
