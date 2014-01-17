package com.skplanet.storeplatform.sac.member.miscellaneous.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.skplanet.storeplatform.framework.core.persistence.dao.CommonDAO;
import com.skplanet.storeplatform.sac.member.miscellaneous.vo.ServiceAuthDTO;

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
	public void insertPhoneAuthCode(ServiceAuthDTO serviceAuthInfo) {
		// serviceAuthInfo.setAuthSeq((String) this.commonDao.queryForObject("Miscellaneous.getNexValueForSvcAuth",
		// null));
		this.commonDao.insert("Miscellaneous.insertPhoneAuthCode", serviceAuthInfo);
	}

	@Override
	public ServiceAuthDTO getPhoneAuthYn(ServiceAuthDTO serviceAuthInfo) {
		return (ServiceAuthDTO) this.commonDao.queryForObject("Miscellaneous.getPhoneAuthYn", serviceAuthInfo);
	}

	@Override
	public void updatePhoneAuthYn(String authSeq) {
		this.commonDao.update("Miscellaneous.updatePhoneAuthYn", authSeq);
	}

}
