package com.skplanet.storeplatform.sac.client.member.vo.seller;

import java.util.List;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;

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
