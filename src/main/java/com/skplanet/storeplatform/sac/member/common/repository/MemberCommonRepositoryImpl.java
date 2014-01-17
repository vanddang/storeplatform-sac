package com.skplanet.storeplatform.sac.member.common.repository;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.skplanet.storeplatform.framework.core.persistence.dao.CommonDAO;
import com.skplanet.storeplatform.sac.member.common.vo.Clause;
import com.skplanet.storeplatform.sac.member.common.vo.Device;

/**
 * 공통 관련 인터페이스 구현체
 * 
 * Updated on : 2014. 1. 9. Updated by : 심대진, 다모아 솔루션.
 */
@SuppressWarnings("unchecked")
@Component
@Transactional
public class MemberCommonRepositoryImpl implements MemberCommonRepository {

	private static final Logger LOGGER = LoggerFactory.getLogger(MemberCommonRepositoryImpl.class);

	@Autowired
	@Qualifier("sac")
	private CommonDAO commonDAO;

	@Override
	public List<Clause> getMandAgreeList(String tenantId) throws Exception {
		Clause dto = new Clause();
		dto.setTenantId(tenantId);
		return (List<Clause>) this.commonDAO.queryForList("MemberCommon.getMandAgreeList", dto);
	}

	@Override
	public Device getPhoneInfo(String deviceModelCd) throws Exception {
		Device dto = new Device();
		dto.setDeviceModelCd(deviceModelCd);
		return (Device) this.commonDAO.queryForObject("MemberCommon.getPhoneInfo", dto);
	}

	@Override
	public int getOmdCount(String uacd) throws Exception {
		return (Integer) this.commonDAO.queryForObject("MemberCommon.getOmdCount", uacd);
	}

}
