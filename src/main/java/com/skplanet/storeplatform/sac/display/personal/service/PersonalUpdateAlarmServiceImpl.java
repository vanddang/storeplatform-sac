/**
 * 
 */
package com.skplanet.storeplatform.sac.display.personal.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.skplanet.storeplatform.framework.core.persistence.dao.CommonDAO;
import com.skplanet.storeplatform.sac.client.display.vo.personal.PersonalUpdateAlarmReq;
import com.skplanet.storeplatform.sac.client.display.vo.personal.PersonalUpdateAlarmRes;
import com.skplanet.storeplatform.sac.common.header.vo.SacRequestHeader;

/**
 * Application Update 알림 설정 Service 구현체
 * 
 * Updated on : 2014. 2. 10. Updated by : 오승민, 인크로스.
 */
@Service
public class PersonalUpdateAlarmServiceImpl implements PersonalUpdateAlarmService {
	private final Logger log = LoggerFactory.getLogger(this.getClass());

	@Autowired
	@Qualifier("sac")
	private CommonDAO commonDAO;

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.skplanet.storeplatform.sac.display.personal.service.PersonalUpdateAlarmService#updateAlarm(com.skplanet.
	 * storeplatform.sac.client.display.vo.personal.PersonalAutoUpgradeReq,
	 * com.skplanet.storeplatform.sac.common.header.vo.SacRequestHeader)
	 */
	@Override
	public PersonalUpdateAlarmRes updateAlarm(PersonalUpdateAlarmReq req, SacRequestHeader header) {
		// TODO Auto-generated method stub
		return null;
	}

}
