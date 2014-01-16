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

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.skplanet.storeplatform.member.client.user.sci.vo.SearchAgreementListRequest;
import com.skplanet.storeplatform.sac.api.util.StringUtil;
import com.skplanet.storeplatform.sac.client.member.vo.common.MbrClauseAgreeList;
import com.skplanet.storeplatform.sac.common.header.vo.SacRequestHeader;
import com.skplanet.storeplatform.sac.member.common.ParameterExceptionHandling;
import com.skplanet.storeplatform.sac.member.user.service.UserSelectServiceImpl;

/**
 * Store 약관동의 목록조회 서비스 Controller
 * 
 * Updated on : 2014. 1. 16. Updated by : 강신완, 부르칸.
 */
@RequestMapping(value = "/dev/member/user")
@Controller
public class ListTermsAgreementController extends ParameterExceptionHandling {

	private static final Logger logger = LoggerFactory.getLogger(ListTermsAgreementController.class);

	@Autowired
	private UserSelectServiceImpl svc;

	@RequestMapping(value = "/listTermsAgreement/v1", method = RequestMethod.GET)
	@ResponseBody
	public MbrClauseAgreeList listTermsAgreement(SearchAgreementListRequest req, SacRequestHeader sacHeader)
			throws Exception {
		logger.info("####################################################");
		logger.info("##### 5.1.10. 약관 동의 목록 조회 #####");
		logger.info("####################################################");

		MbrClauseAgreeList res = new MbrClauseAgreeList();

		SearchAgreementListRequest schAgreementListReq = new SearchAgreementListRequest();
		schAgreementListReq.setUserKey(req.getUserKey());
		schAgreementListReq.setCommonRequest(this.svc.commonRequest());

		List<MbrClauseAgreeList> mbrClauseAgreeList = new ArrayList<MbrClauseAgreeList>();
		MbrClauseAgreeList mbrClause = this.svc.mbrClauseAgreeList(schAgreementListReq);
		mbrClauseAgreeList.add(mbrClause);

		String userKey = StringUtil.nvl(req.getUserKey(), "");

		if (userKey.equals("")) {
			throw new Exception("필수요청 파라메터 부족");
		}

		res = this.svc.mbrClauseAgreeList(schAgreementListReq);

		return res;
	}

}
