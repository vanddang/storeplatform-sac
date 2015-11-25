package com.skplanet.storeplatform.sac.client.member.vo.user;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;

/**
 * CI Infra 에 제공할 회원 정보.
 * 
 * Updated on : 2014. 10. 2. Updated by : 반범진, SK 플래닛.
 */
@JsonSerialize(include = Inclusion.NON_NULL)
public class CIInfraUserInfoSac extends CommonInfo {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * IDP/OneId 에서 관리하는 회원 Identity Key.
	 */
	private String imMbrNo;

	/**
	 * T Store가 관리하는 회원 Identity Key.
	 */
	private String userKey;

	/**
	 * 회원 구분.
	 */
	private String userType;

	/**
	 * 처리시간(신규 : 가입일, 정보변경 : 수정일, 탈퇴 : 탈퇴일)
	 */
	private String lastTime;

	/**
	 * <pre>
	 * IDP/OneId 에서 관리하는 회원 Identity Key 리턴.
	 * </pre>
	 * 
	 * @return imMbrNo
	 */
	public String getImMbrNo() {
		return this.imMbrNo;
	}

	/**
	 * <pre>
	 * IDP/OneId 에서 관리하는 회원 Identity Key 셋팅.
	 * </pre>
	 * 
	 * @param imMbrNo
	 *            String
	 */
	public void setImMbrNo(String imMbrNo) {
		this.imMbrNo = imMbrNo;
	}

	/**
	 * <pre>
	 * T Store가 관리하는 회원 Identity Key 리턴.
	 * </pre>
	 * 
	 * @return userKey
	 */
	public String getUserKey() {
		return this.userKey;
	}

	/**
	 * <pre>
	 * T Store가 관리하는 회원 Identity Key 셋팅.
	 * </pre>
	 * 
	 * @param userKey
	 *            String
	 */
	public void setUserKey(String userKey) {
		this.userKey = userKey;
	}

	/**
	 * <pre>
	 * 회원 구분 리턴.
	 * </pre>
	 * 
	 * @return userType
	 */
	public String getUserType() {
		return this.userType;
	}

	/**
	 * <pre>
	 * 회원 구분 셋팅.
	 * </pre>
	 * 
	 * @param userType
	 *            String
	 */
	public void setUserType(String userType) {
		this.userType = userType;
	}

	/**
	 * <pre>
	 * 처리시간 리턴.
	 * </pre>
	 * 
	 * @return lastTime
	 */
	public String getLastTime() {
		return this.lastTime;
	}

	/**
	 * <pre>
	 * 처리시간 셋팅.
	 * </pre>
	 * 
	 * @param lastTime
	 *            String
	 */
	public void setLastTime(String lastTime) {
		this.lastTime = lastTime;
	}

}
