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
import com.skplanet.storeplatform.sac.api.util.StringUtil;
import com.skplanet.storeplatform.sac.client.member.vo.user.ClauseSacRes;
import com.skplanet.storeplatform.sac.client.member.vo.user.DetailClauseSacReq;
import com.skplanet.storeplatform.sac.client.member.vo.user.DetailClauseSacRes;
import com.skplanet.storeplatform.sac.client.member.vo.user.ListClauseSacRes;
import com.skplanet.storeplatform.sac.member.common.MemberCommonComponent;
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
	public ListClauseSacRes listClause() {
		List<Clause> clauseList = this.commService.getListClause();

		LOGGER.info("ListClauseSacRes : ", clauseList.toString());

		List<ClauseSacRes> clauseSacResList = new ArrayList<ClauseSacRes>();
		for (Clause clause : clauseList) {
			ClauseSacRes clauseRes = new ClauseSacRes();
			clauseRes.setClauseId(StringUtil.setTrim(clause.getClauseId()));
			clauseRes.setClauseItemCd(StringUtil.setTrim(clause.getClauseItemCd()));
			clauseRes.setClauseVer(StringUtil.setTrim(clause.getClauseVer()));
			clauseRes.setEndDay(StringUtil.setTrim(clause.getEndDay()));
			clauseRes.setFileNm(StringUtil.setTrim(clause.getFileNm()));
			clauseRes.setFilePath(StringUtil.setTrim(clause.getFilePath()));
			clauseRes.setStartDay(StringUtil.setTrim(clause.getStartDay()));

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
	public DetailClauseSacRes detailClauseList(DetailClauseSacReq req) {
		String clauseItemCd = req.getClauseItemCd();
		List<Clause> clauseList = this.commService.getDetailClauseList(clauseItemCd);

		LOGGER.info("ListClauseSacRes : ", clauseList.toString());

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
			clauseRes.setFileNm(StringUtil.setTrim(clause.getFileNm()));
			clauseRes.setFilePath(StringUtil.setTrim(clause.getFilePath()));
			clauseRes.setStartDay(StringUtil.setTrim(clause.getStartDay()));

			detailClauseList.add(clauseRes);
		}

		DetailClauseSacRes res = new DetailClauseSacRes();
		res.setDetailClauseList(detailClauseList);

		return res;
	}

}
