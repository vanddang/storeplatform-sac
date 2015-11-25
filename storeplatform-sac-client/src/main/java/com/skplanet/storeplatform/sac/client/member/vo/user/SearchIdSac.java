package com.skplanet.storeplatform.sac.client.member.vo.user;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;

/**
 * [RESPONSE] ID 찾기
 * 
 * Updated on : 2014. 2. 10. Updated by : 강신완, 부르칸.
 */
@JsonSerialize(include = Inclusion.NON_NULL)
public class SearchIdSac extends CommonInfo {
	private static final long serialVersionUID = 1L;

	/* 사용자 아이디 */
	private String userId;

	/* 회원타입 IDP/OneId */
	private String userType;

	/* 등록일 */
	private String regDate;

	/* 통합 서비스 관리 번호 */
	private String imSvcNo;

	/* UserEmail */
	private String userEmail;

	public String getUserId() {
		return this.userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUserType() {
		return this.userType;
	}

	public void setUserType(String userType) {
		this.userType = userType;
	}

	public String getRegDate() {
		return this.regDate;
	}

	public void setRegDate(String regDate) {
		this.regDate = regDate;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getImSvcNo() {
		return this.imSvcNo;
	}

	public void setImSvcNo(String imSvcNo) {
		this.imSvcNo = imSvcNo;
	}

	public String getUserEmail() {
		return this.userEmail;
	}

	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}

}
