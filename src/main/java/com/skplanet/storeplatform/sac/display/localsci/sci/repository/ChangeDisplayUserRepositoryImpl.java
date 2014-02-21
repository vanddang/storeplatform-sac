package com.skplanet.storeplatform.sac.display.localsci.sci.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.skplanet.storeplatform.framework.core.persistence.dao.CommonDAO;
import com.skplanet.storeplatform.sac.display.localsci.sci.vo.ChangeDisplayUser;

/**
 * 
 * ChangeDisplayUser Repository 구현체
 * 
 * 전시 회원 ID, KEY 변경 DAO.
 * 
 * Updated on : 2014. 2. 13. Updated by : 김현일, 인크로스
 */
@Component
public class ChangeDisplayUserRepositoryImpl implements ChangeDisplayUserRepository {

	@Autowired
	@Qualifier("sac")
	private CommonDAO commonDAO;

	@Override
	public Object changeMbrAvg(ChangeDisplayUser changeDisplayUser) {
		return this.commonDAO.update("LocalSci.changeMbrAvg", changeDisplayUser);
	}

	@Override
	public Object changeProdNoti(ChangeDisplayUser changeDisplayUser) {
		return this.commonDAO.update("LocalSci.changeProdNoti", changeDisplayUser);
	}

	@Override
	public Object changeBadNoti(ChangeDisplayUser changeDisplayUser) {
		return this.commonDAO.update("LocalSci.changeBadNoti", changeDisplayUser);
	}

	@Override
	public Object changeProdNotiGood(ChangeDisplayUser changeDisplayUser) {
		return this.commonDAO.update("LocalSci.changeProdNotiGood", changeDisplayUser);
	}

	@Override
	public Object changeTenantProdStats(ChangeDisplayUser changeDisplayUser) {
		return this.commonDAO.update("LocalSci.changeTenantProdStats", changeDisplayUser);
	}

	@Override
	public Object changeMsgMbrMapg(ChangeDisplayUser changeDisplayUser) {
		return this.commonDAO.update("LocalSci.changeMsgMbrMapg", changeDisplayUser);
	}
}
