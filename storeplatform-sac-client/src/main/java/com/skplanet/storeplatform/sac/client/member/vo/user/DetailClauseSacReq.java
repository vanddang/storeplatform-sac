package com.skplanet.storeplatform.sac.client.member.vo.user;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;
import org.hibernate.validator.constraints.NotEmpty;

/**
 * [RESPONSE] 약관 목록 상세 조회
 * 
 * Updated on : 2014. 2. 3. Updated by : 강신완, 부르칸.
 */
public class DetailClauseSacReq extends CommonInfo {

	private static final long serialVersionUID = 1L;

    /**
     * 약관코드
     */
    @NotEmpty
	private String clauseItemCd;

	public String getClauseItemCd() {
		return this.clauseItemCd;
	}

	public void setClauseItemCd(String clauseItemCd) {
		this.clauseItemCd = clauseItemCd;
	}
}
