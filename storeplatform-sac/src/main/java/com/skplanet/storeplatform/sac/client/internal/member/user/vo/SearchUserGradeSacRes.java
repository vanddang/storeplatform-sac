package com.skplanet.storeplatform.sac.client.internal.member.user.vo;

import java.io.Serializable;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;

/**
 * 회원 등급 조회 [RESPONSE].
 * 
 * Updated on : 2014. 7. 11. Updated by : Rejoice, Burkhan
 */
@JsonSerialize(include = Inclusion.NON_NULL)
public class SearchUserGradeSacRes extends CommonInfo implements Serializable {

	private static final long serialVersionUID = 1L;

	/** 회원 키. */
	private String userKey;

	/** 회원 등급 정보. */
	private GradeInfoSac gradeInfoSac;

	/**
	 * @return the userKey
	 */
	public String getUserKey() {
		return this.userKey;
	}

	/**
	 * @param userKey
	 *            the userKey to set
	 */
	public void setUserKey(String userKey) {
		this.userKey = userKey;
	}

	/**
	 * @return the gradeInfoSac
	 */
	public GradeInfoSac getGradeInfoSac() {
		return this.gradeInfoSac;
	}

	/**
	 * @param gradeInfoSac
	 *            the gradeInfoSac to set
	 */
	public void setGradeInfoSac(GradeInfoSac gradeInfoSac) {
		this.gradeInfoSac = gradeInfoSac;
	}

}
