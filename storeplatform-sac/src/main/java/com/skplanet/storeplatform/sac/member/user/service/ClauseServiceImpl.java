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

import com.google.common.base.Function;
import com.google.common.collect.Lists;
import com.skplanet.storeplatform.framework.core.exception.StorePlatformException;
import com.skplanet.storeplatform.sac.client.member.vo.user.ClauseSacRes;
import com.skplanet.storeplatform.sac.client.member.vo.user.DetailClauseSacReq;
import com.skplanet.storeplatform.sac.client.member.vo.user.DetailClauseSacRes;
import com.skplanet.storeplatform.sac.client.member.vo.user.ListClauseSacRes;
import com.skplanet.storeplatform.sac.common.header.vo.SacRequestHeader;
import com.skplanet.storeplatform.sac.member.common.MemberCommonComponent;
import com.skplanet.storeplatform.sac.member.common.constant.MemberConstants;
import com.skplanet.storeplatform.sac.member.common.vo.Clause;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Nullable;
import java.util.List;

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
        String systemId = sacHeader.getTenantHeader().getSystemId();
        List<Clause> clauseList = this.commService.getListClause();

        return new ListClauseSacRes(convert(systemId, clauseList));
	}

	/**
	 * 약관목록 상세조회
	 */
	@Override
	public DetailClauseSacRes detailClauseList(SacRequestHeader sacHeader, DetailClauseSacReq req) {
		String clauseItemCd = req.getClauseItemCd(),
                systemId = sacHeader.getTenantHeader().getSystemId();

		/* Tenant에 등록된 코드인지 확인 */
		Clause clauseCode = this.commService.getTenantClauseCode(clauseItemCd);
		if (clauseCode == null) {
			throw new StorePlatformException("SAC_MEM_1105", clauseItemCd);
		}

		/* Tenant에 등록된 코드면 TB_CM_CLAUSE 조회 */
		List<Clause> clauseList = this.commService.getDetailClauseList(clauseItemCd);

		if (clauseList.size() == 0) {
			throw new StorePlatformException("SAC_MEM_0002", req.getClauseItemCd());
		}

		return new DetailClauseSacRes(convert(systemId, clauseList));
	}

    private List<ClauseSacRes> convert(final String systemId, final List<Clause> clauseList) {
        return Lists.transform(clauseList, new Function<Clause, ClauseSacRes>() {
            @Nullable
            @Override
            public ClauseSacRes apply(Clause clause) {
                ClauseSacRes clauseRes = new ClauseSacRes();
                clauseRes.setClauseId(StringUtils.trim(clause.getClauseId()));
                clauseRes.setClauseItemCd(StringUtils.trim(clause.getClauseItemCd()));
                clauseRes.setClauseVer(StringUtils.trim(clause.getClauseVer()));
                clauseRes.setEndDay(StringUtils.trim(clause.getEndDay()));

                // 티스토어 포털 웹인경우와 아닌 경우의 약관의 경로 systemId를 가지고 분기 처리.
                if (MemberConstants.SYSTEM_ID_TSTORE_PORTAL_WEB.equals(systemId)) {
                    clauseRes.setFileNm(StringUtils.trim(clause.getFileNm()));
                    clauseRes.setFilePath(StringUtils.trim(clause.getFilePath()));
                } else {
                    clauseRes.setFileNm(StringUtils.trim(clause.getMwFileNm()));
                    clauseRes.setFilePath(StringUtils.trim(clause.getMwFilePath()));
                }

                clauseRes.setStartDay(StringUtils.trim(clause.getStartDay()));
                clauseRes.setReAgreeYn(StringUtils.trim(clause.getReAgreeYn()));

                return clauseRes;
            }
        });
    }

}
