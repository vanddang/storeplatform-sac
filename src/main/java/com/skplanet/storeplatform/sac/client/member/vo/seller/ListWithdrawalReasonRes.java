package com.skplanet.storeplatform.sac.client.member.vo.seller;

import java.util.List;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;
import com.skplanet.storeplatform.sac.client.member.vo.common.SecedeReson;

/**
 * 탈퇴 사유 목록 조회
 * 
 * Updated on : 2014. 1. 7. Updated by : 한서구, 부르칸.
 */
@JsonSerialize(include = Inclusion.NON_NULL)
public class ListWithdrawalReasonRes extends CommonInfo {

	private static final long serialVersionUID = 1L;

	List<SecedeReson> secedeResonList;

	public List<SecedeReson> getSecedeResonList() {
		return this.secedeResonList;
	}

	public void setSecedeResonList(List<SecedeReson> secedeResonList) {
		this.secedeResonList = secedeResonList;
	}

}
