/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.member.user.controller;

import com.skplanet.storeplatform.sac.client.member.vo.user.DetailClauseSacReq;
import com.skplanet.storeplatform.sac.client.member.vo.user.DetailClauseSacRes;
import com.skplanet.storeplatform.sac.client.member.vo.user.ListClauseSacRes;
import com.skplanet.storeplatform.sac.common.header.vo.SacRequestHeader;
import com.skplanet.storeplatform.sac.member.user.service.ClauseService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.Valid;

/**
 * 약관 조회 서비스 Controller
 * - 단순 조회 API의 경우 req, res 로깅 제거함
 * Updated on : 2014. 2. 12. Updated by : 강신완, 부르칸.
 * Updated on : 2016. 1. 11. Updated by : 정희원/SKP
 */
@Controller
public class ClauseController {

	private static final Logger LOGGER = LoggerFactory.getLogger(ClauseController.class);

	@Autowired
	private ClauseService svc;

    /**
     * [I01000032] 2.1.31. 약관목록 조회
     * @param sacHeader
     * @return
     */
	@RequestMapping(value = "/member/user/listClause/v1", method = RequestMethod.GET)
	@ResponseBody
	public ListClauseSacRes listClause(SacRequestHeader sacHeader) {

		ListClauseSacRes res = this.svc.listClause(sacHeader);
		return res;
	}

    /**
     * [I01000033] 2.1.32. 약관목록 상세 조회
     * @param sacHeader
     * @param req
     * @return
     */
	@RequestMapping(value = "/member/user/detailClause/v1", method = RequestMethod.GET)
	@ResponseBody
	public DetailClauseSacRes detailClause(SacRequestHeader sacHeader, @Valid DetailClauseSacReq req) {

		/*
		 * 2014-04-08 데이터베이스 SPSAC.TB_CM_CLAUSE 칼럼 변경 clauseItemCd --> cluaseId로 변경 clauseId --> clauseItemCd로 변경 VO
		 * Request : cluaseItemCd는 변경 없이 그대로 적용시킴, 타부서에서 별도작업없이 진행시키기 위해. VO Request : cluaseItemCd로 받는 값은
		 * SPSAC.TB_CM_CLAUSE에서 clause_id 와 Mapping 된다.
		 */

		DetailClauseSacRes res = this.svc.detailClauseList(sacHeader, req);
		return res;
	}
}
