package com.skplanet.storeplatform.sac.member.common.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

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
public class MemberCommonRepositoryImpl implements MemberCommonRepository {

	@Autowired
	@Qualifier("sac")
	private CommonDAO commonDAO;

	@Override
	public List<Clause> searchMandatoryAgreeList(String tenantId) {
		Clause dto = new Clause();
		dto.setTenantId(tenantId);
		return (List<Clause>) this.commonDAO.queryForList("MemberCommon.getMandAgreeList", dto);
	}

	@Override
	public Device searchPhoneInfo(String deviceModelCd) {
		Device dto = new Device();
		dto.setDeviceModelCd(deviceModelCd);
		return (Device) this.commonDAO.queryForObject("MemberCommon.getPhoneInfo", dto);
	}

	@Override
	public int searchOmdCount(String uacd) {
		return (Integer) this.commonDAO.queryForObject("MemberCommon.getOmdCount", uacd);
	}

	@Override
	public Device searchPhoneInfoByUacd(String uacd) {
		Device dto = new Device();
		dto.setUaCd(uacd);
		return (Device) this.commonDAO.queryForObject("MemberCommon.getPhoneInfo", dto);
	}

	@Override
	public List<Clause> searchClauseList(String tenantId) {
		Clause dto = new Clause();
		dto.setTenantId(tenantId);
		return (List<Clause>) this.commonDAO.queryForList("MemberCommon.getClauseList", dto);
	}

	@Override
	public List<Clause> searchClauseDetail(String clauseId) {
		Clause dto = new Clause();
		dto.setClauseId(clauseId);
		return (List<Clause>) this.commonDAO.queryForList("MemberCommon.getClauseDetail", dto);
	}

	@Override
	public Clause searchTenantClauseCode(String clauseId) {
		Clause dto = new Clause();
		dto.setClauseId(clauseId);
		return (Clause) this.commonDAO.queryForObject("MemberCommon.getTenantClauseCode", dto);
	}

	@Override
	public Clause getClauseItemInfo(String tenantId, String clauseId) {
		Clause dto = new Clause();
		dto.setTenantId(tenantId);
		dto.setClauseId(clauseId);
		return (Clause) this.commonDAO.queryForObject("MemberCommon.getClauseItemInfo", dto);
	}
}
