package com.skplanet.storeplatform.sac.client.member.vo.user;

import java.util.List;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;

/**
 * [RESPONSE] 약관 목록 조회
 * 
 * Updated on : 2014. 2. 3. Updated by : 강신완, 부르칸.
 */
@JsonSerialize(include = Inclusion.NON_NULL)
public class ListClauseSacRes extends CommonInfo {

	private static final long serialVersionUID = 1L;

	private List<ClauseSacRes> ClauseList;

    public ListClauseSacRes() {}

    public ListClauseSacRes(List<ClauseSacRes> clauseList) {
        ClauseList = clauseList;
    }

    public List<ClauseSacRes> getClauseList() {
		return this.ClauseList;
	}

	public void setClauseList(List<ClauseSacRes> clauseList) {
		this.ClauseList = clauseList;
	}

}
