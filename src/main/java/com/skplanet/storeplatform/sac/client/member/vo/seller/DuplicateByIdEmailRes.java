package com.skplanet.storeplatform.sac.client.member.vo.seller;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;

/**
 * 판매자회원 ID/이메일 중복 조회
 * 
 * Updated on : 2014. 1. 7. Updated by : 한서구, 부르칸.
 */
@JsonSerialize(include = Inclusion.NON_NULL)
public class DuplicateByIdEmailRes extends CommonInfo {

	private static final long serialVersionUID = 1L;

	public DuplicateByIdEmailRes() {
		super();
	}

	public DuplicateByIdEmailRes(String isRegistered) {
		super();
		this.isRegistered = isRegistered;
	}

	private String isRegistered;

	public String getIsRegistered() {
		return this.isRegistered;
	}

	public void setIsRegistered(String isRegistered) {
		this.isRegistered = isRegistered;
	}

}
