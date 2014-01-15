package com.skplanet.storeplatform.sac.client.member.vo.seller;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;

/**
 * 탈퇴 사유 목록 조회
 * 
 * Updated on : 2014. 1. 7. Updated by : 한서구, 부르칸.
 */
@JsonSerialize(include = Inclusion.NON_NULL)
public class ListWithdrawalReasonReq extends CommonInfo {

	private static final long serialVersionUID = 1L;

	private String koUsWhether;

	public String getKoUsWhether() {
		return this.koUsWhether;
	}

	public void setKoUsWhether(String koUsWhether) {
		this.koUsWhether = koUsWhether;
	}

}
