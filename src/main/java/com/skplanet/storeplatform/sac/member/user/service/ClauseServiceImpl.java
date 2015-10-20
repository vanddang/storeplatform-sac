/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.member.user.service;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.skplanet.storeplatform.framework.core.exception.StorePlatformException;
import com.skplanet.storeplatform.framework.core.util.StringUtils;
import com.skplanet.storeplatform.sac.api.util.StringUtil;
import com.skplanet.storeplatform.sac.client.member.vo.user.ClauseSacRes;
import com.skplanet.storeplatform.sac.client.member.vo.user.DetailClauseSacReq;
import com.skplanet.storeplatform.sac.client.member.vo.user.DetailClauseSacRes;
import com.skplanet.storeplatform.sac.client.member.vo.user.ListClauseSacRes;
import com.skplanet.storeplatform.sac.common.header.vo.SacRequestHeader;
import com.skplanet.storeplatform.sac.member.common.MemberCommonComponent;
import com.skplanet.storeplatform.sac.member.common.constant.MemberConstants;
import com.skplanet.storeplatform.sac.member.common.vo.Clause;

/**
 * 약관목록 관련 인터페이스.
 * 
 * Updated on : 2014. 2. 12. Updated by : 강신완, 부르칸.
 */
@Service
public class ClauseServiceImpl implements ClauseService {

	private static final Logger LOGGER = LoggerFactory.getLogger(ClauseServiceImpl.class);

	@Autowired
	private MemberCommonComponent commService; // 회원 공통 서비스

	/**
	 * 약관목록 조회
	 */
	@Override
	public ListClauseSacRes listClause(SacRequestHeader sacHeader) {
		List<Clause> clauseList = this.commService.getListClause(sacHeader.getTenantHeader().getTenantId());

		List<ClauseSacRes> clauseSacResList = new ArrayList<ClauseSacRes>();
		for (Clause clause : clauseList) {
			ClauseSacRes clauseRes = new ClauseSacRes();
			clauseRes.setClauseId(StringUtil.setTrim(clause.getClauseId()));
			clauseRes.setClauseItemCd(StringUtil.setTrim(clause.getClauseItemCd()));
			clauseRes.setClauseVer(StringUtil.setTrim(clause.getClauseVer()));
			clauseRes.setEndDay(StringUtil.setTrim(clause.getEndDay()));
			// 티스토어 포털 웹인경우와 아닌 경우의 약관의 경로 systemId를 가지고 분기 처리.
			if (MemberConstants.SYSTEM_ID_TSTORE_PORTAL_WEB.equals(sacHeader.getTenantHeader().getSystemId())) {
				clauseRes.setFileNm(StringUtil.setTrim(clause.getFileNm()));
				clauseRes.setFilePath(StringUtil.setTrim(clause.getFilePath()));
			} else {
				clauseRes.setFileNm(StringUtil.setTrim(clause.getMwFileNm()));
				clauseRes.setFilePath(StringUtil.setTrim(clause.getMwFilePath()));
			}

			clauseRes.setStartDay(StringUtil.setTrim(clause.getStartDay()));
			clauseRes.setReAgreeYn(StringUtil.setTrim(clause.getReAgreeYn()));

			clauseSacResList.add(clauseRes);
		}

		ListClauseSacRes res = new ListClauseSacRes();
		res.setClauseList(clauseSacResList);

		return res;
	}

	/**
	 * 약관목록 상세조회
	 */
	@Override
	public DetailClauseSacRes detailClauseList(SacRequestHeader sacHeader, DetailClauseSacReq req) {
		String clauseItemCd = req.getClauseItemCd();

		// Header에 tenantId가 없는 경우 S01로 디폴트값 설정.
		String tenantId = StringUtils.defaultIfBlank(sacHeader.getTenantHeader().getTenantId(),
				MemberConstants.TENANT_ID_TSTORE);

		/* Tenant에 등록된 코드인지 확인 */
		Clause clauseCode = this.commService.getTenantClauseCode(tenantId, clauseItemCd);
		if (clauseCode == null) {
			throw new StorePlatformException("SAC_MEM_1105", clauseItemCd);
		}

		/* Tenant에 등록된 코드면 TB_CM_CLAUSE 조회 */
		List<Clause> clauseList = this.commService.getDetailClauseList(tenantId, clauseItemCd);

		if (clauseList.size() == 0) {
			throw new StorePlatformException("SAC_MEM_0002", req.getClauseItemCd());
		}

		List<ClauseSacRes> detailClauseList = new ArrayList<ClauseSacRes>();
		for (Clause clause : clauseList) {
			ClauseSacRes clauseRes = new ClauseSacRes();
			clauseRes.setClauseId(StringUtil.setTrim(clause.getClauseId()));
			clauseRes.setClauseItemCd(StringUtil.setTrim(clause.getClauseItemCd()));
			clauseRes.setClauseVer(StringUtil.setTrim(clause.getClauseVer()));
			clauseRes.setEndDay(StringUtil.setTrim(clause.getEndDay()));

			// 티스토어 포털 웹인경우와 아닌 경우의 약관의 경로 systemId를 가지고 분기 처리.
			if (MemberConstants.SYSTEM_ID_TSTORE_PORTAL_WEB.equals(sacHeader.getTenantHeader().getSystemId())) {
				clauseRes.setFileNm(StringUtil.setTrim(clause.getFileNm()));
				clauseRes.setFilePath(StringUtil.setTrim(clause.getFilePath()));
			} else {
				clauseRes.setFileNm(StringUtil.setTrim(clause.getMwFileNm()));
				clauseRes.setFilePath(StringUtil.setTrim(clause.getMwFilePath()));
			}

			clauseRes.setStartDay(StringUtil.setTrim(clause.getStartDay()));
			clauseRes.setReAgreeYn(StringUtil.setTrim(clause.getReAgreeYn()));

			detailClauseList.add(clauseRes);
		}

		DetailClauseSacRes res = new DetailClauseSacRes();
		res.setDetailClauseList(detailClauseList);

		return res;
	}

}
