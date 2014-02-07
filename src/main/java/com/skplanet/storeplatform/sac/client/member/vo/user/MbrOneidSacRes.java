package com.skplanet.storeplatform.sac.client.member.vo.user;

import java.io.Serializable;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;

/**
 * [REQUEST] OneId 회원정보
 * 
 * Updated on : 2014. 2. 7. Updated by : 강신완. 부르칸.
 */
@JsonSerialize(include = Inclusion.NON_NULL)
public class MbrOneidSacRes extends CommonInfo implements Serializable {

	private static final long serialVersionUID = 1L;

	/* 실명인증여부 */
	private String isRealName;

	/* CI 존재여부 */
	private String isCi;

	/* OCB 이용동의 여부 */
	private String isMemberPoint;

	public String getIsRealName() {
		return this.isRealName;
	}

	public void setIsRealName(String isRealName) {
		this.isRealName = isRealName;
	}

	public String getIsCi() {
		return this.isCi;
	}

	public void setIsCi(String isCi) {
		this.isCi = isCi;
	}

	public String getIsMemberPoint() {
		return this.isMemberPoint;
	}

	public void setIsMemberPoint(String isMemberPoint) {
		this.isMemberPoint = isMemberPoint;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
