package com.skplanet.storeplatform.sac.client.member.vo.common;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;

/**
 * 탈퇴 사유 목록 조회 탈퇴사유 리스트
 * 
 * Updated on : 2014. 1. 7. Updated by : 한서구, 부르칸.
 */
@JsonSerialize(include = Inclusion.NON_NULL)
public class SecedeReson extends CommonInfo {

	private static final long serialVersionUID = 1L;

	private String secedeReasonCd;
	private String secedeReasonDes;

	public String getSecedeReasonCd() {
		return this.secedeReasonCd;
	}

	public void setSecedeReasonCd(String secedeReasonCd) {
		this.secedeReasonCd = secedeReasonCd;
	}

	public String getSecedeReasonDes() {
		return this.secedeReasonDes;
	}

	public void setSecedeReasonDes(String secedeReasonDes) {
		this.secedeReasonDes = secedeReasonDes;
	}

}
