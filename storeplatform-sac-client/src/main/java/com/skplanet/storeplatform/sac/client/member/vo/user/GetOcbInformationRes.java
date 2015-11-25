package com.skplanet.storeplatform.sac.client.member.vo.user;

import java.util.List;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;
import com.skplanet.storeplatform.sac.client.member.vo.common.OcbInfo;

/**
 * [RESPONSE] 회원 OCB 정보 조회
 * 
 * Updated on : 2014. 1. 6. Updated by : 심대진, 다모아 솔루션.
 */
@JsonSerialize(include = Inclusion.NON_NULL)
public class GetOcbInformationRes extends CommonInfo {

	private static final long serialVersionUID = 1L;

	private List<OcbInfo> ocbInfoList = null;

	/**
	 * @return List<OcbInfo> : ocbInfoList
	 */
	public List<OcbInfo> getOcbInfoList() {
		return this.ocbInfoList;
	}

	/**
	 * @param ocbInfoList
	 *            List<OcbInfo> : the ocbInfoList to set
	 */
	public void setOcbInfoList(List<OcbInfo> ocbInfoList) {
		this.ocbInfoList = ocbInfoList;
	}
}
