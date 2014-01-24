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
	public ServiceAuth getPhoneAuthYn(ServiceAuth serviceAuthInfo) { // confirmPhoneAuthCode
		return (ServiceAuth) this.commonDao.queryForObject("Miscellaneous.getPhoneAuthYn", serviceAuthInfo);
	}

	@Override
	public void updateServiceAuthYn(String authSeq) {
		this.commonDao.update("Miscellaneous.updateServiceAuthYn", authSeq);
	}

	@Override
	public void insertServiceAuthCode(ServiceAuth serviceAuthInfo) {
		this.commonDao.insert("Miscellaneous.insertServiceAuthCode", serviceAuthInfo);
	}

	@Override
	public ServiceAuth getEmailAuthYn(String mbrNo) { // confirmEmailAuthYn
		return (ServiceAuth) this.commonDao.queryForObject("Miscellaneous.getEmailAuthYn", mbrNo);
	}

	@Override
	public ServiceAuth getEmailAuthInfo(String authValue) {

		return (ServiceAuth) this.commonDao.queryForObject("Miscellaneous.getEmailAuthInfo", authValue);
	}
}
