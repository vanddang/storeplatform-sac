package com.skplanet.storeplatform.sac.client.member.vo.user;

import java.util.List;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;

/**
 * [RESPONSE] 약관 목록 상세 조회
 * 
 * Updated on : 2014. 2. 3. Updated by : 강신완, 부르칸.
 */
@JsonSerialize(include = Inclusion.NON_NULL)
public class DetailClauseSacRes extends CommonInfo {

	private static final long serialVersionUID = 1L;

	List<ClauseSacRes> detailClauseList;
	List<ClauseSacRes> listClauseList;

	public List<ClauseSacRes> getDetailClauseList() {
		return this.detailClauseList;
	}

	public void setDetailClauseList(List<ClauseSacRes> detailClauseList) {
		this.detailClauseList = detailClauseList;
	}

	public List<ClauseSacRes> getListClauseList() {
		return this.listClauseList;
	}

	public void setListClauseList(List<ClauseSacRes> listClauseList) {
		this.listClauseList = listClauseList;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
