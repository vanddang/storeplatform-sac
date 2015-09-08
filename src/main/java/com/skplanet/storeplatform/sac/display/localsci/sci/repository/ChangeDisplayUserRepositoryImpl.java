package com.skplanet.storeplatform.sac.display.localsci.sci.repository;

import com.skplanet.storeplatform.framework.core.persistence.dao.CommonDAO;
import com.skplanet.storeplatform.sac.display.localsci.sci.vo.ChangeDisplayUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

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
	public Object getMbrAvgProdId(ChangeDisplayUser changeDisplayUser) {
		return this.commonDAO.queryForList("LocalSci.getMbrAvgProdId", changeDisplayUser);
	}

	@Override
	public Object deleteMbrAvg(ChangeDisplayUser changeDisplayUser) {
		return this.commonDAO.delete("LocalSci.deleteMbrAvg", changeDisplayUser);
	}

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
	public Object searchSocialLike(ChangeDisplayUser changeDisplayUser) {
		return this.commonDAO.queryForList("LocalSci.searchSocialLike", changeDisplayUser);
	}
	
	@Override
	public Object deleteSocialLike(ChangeDisplayUser changeDisplayUser) {
		return this.commonDAO.update("LocalSci.deleteSocialLike", changeDisplayUser);
	}
	
	@Override
	public Object changeSocialLike(ChangeDisplayUser changeDisplayUser) {
		return this.commonDAO.update("LocalSci.changeSocialLike", changeDisplayUser);
	}

	@Override
	public Object changeMsgMbrMapg(ChangeDisplayUser changeDisplayUser) {
		return this.commonDAO.update("LocalSci.changeMsgMbrMapg", changeDisplayUser);
	}
	
	@Override
	public Object searchMsgMbrMapg(ChangeDisplayUser changeDisplayUser) {
		return this.commonDAO.queryForObject("LocalSci.searchMsgMbrMapg", changeDisplayUser);
	}

	@Override
	public Object deleteMsgMbrMapg(ChangeDisplayUser changeDisplayUser) {
		return this.commonDAO.delete("LocalSci.deleteMsgMbrMapg", changeDisplayUser);
	}
}
