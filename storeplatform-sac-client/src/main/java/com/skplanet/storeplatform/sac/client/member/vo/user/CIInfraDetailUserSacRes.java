package com.skplanet.storeplatform.sac.client.member.vo.user;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;

/**
 * [RESPONSE] CI Infra 회원 상세 정보 조회.
 * 
 * Updated on : 2014. 10. 2. Updated by : 반범진, SK 플래닛.
 */
@JsonSerialize(include = Inclusion.NON_NULL)
public class CIInfraDetailUserSacRes extends CommonInfo {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * CI정보.
	 */
	private String ci = "";
	/**
	 * OneId 에서 관리하는 회원 Identity Key.
	 */
	private String imMbrNo = "";
	/**
	 * T Store가 관리하는 회원 Identity Key.
	 */
	private String userKey = "";
	/**
	 * 가입자 아이디(아이디 회원).
	 */
	private String userId = "";
	/**
	 * 가입자의 MDN(모바일 회원).
	 */
	private String mdn = "";
	/**
	 * 가입자 이름.
	 */
	private String name = "";
	/**
	 * 가입자 생년월일.
	 */
	private String birth = "";
	/**
	 * 성별.
	 */
	private String sex = "";
	/**
	 * 전화번호(연락처).
	 */
	private String phone = "";
	/**
	 * DI 정보.
	 */
	private String di = "";
	/**
	 * 처리 시간.
	 */
	private String lastTime = "";

	/**
	 * @return ci
	 */
	public String getCi() {
		return this.ci;
	}

	/**
	 * @param ci
	 *            String
	 */
	public void setCi(String ci) {
		this.ci = ci;
	}

	/**
	 * @return imMbrNo
	 */
	public String getImMbrNo() {
		return this.imMbrNo;
	}

	/**
	 * @param imMbrNo
	 *            String
	 */
	public void setImMbrNo(String imMbrNo) {
		this.imMbrNo = imMbrNo;
	}

	/**
	 * @return userKey
	 */
	public String getUserKey() {
		return this.userKey;
	}

	/**
	 * @param userKey
	 *            String
	 */
	public void setUserKey(String userKey) {
		this.userKey = userKey;
	}

	/**
	 * @return userId
	 */
	public String getUserId() {
		return this.userId;
	}

	/**
	 * @param userId
	 *            String
	 */
	public void setUserId(String userId) {
		this.userId = userId;
	}

	/**
	 * @return mdn
	 */
	public String getMdn() {
		return this.mdn;
	}

	/**
	 * @param mdn
	 *            String
	 */
	public void setMdn(String mdn) {
		this.mdn = mdn;
	}

	/**
	 * @return name
	 */
	public String getName() {
		return this.name;
	}

	/**
	 * @param name
	 *            String
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return birth
	 */
	public String getBirth() {
		return this.birth;
	}

	/**
	 * @param birth
	 *            String
	 */
	public void setBirth(String birth) {
		this.birth = birth;
	}

	/**
	 * @return sex
	 */
	public String getSex() {
		return this.sex;
	}

	/**
	 * @param sex
	 *            String
	 */
	public void setSex(String sex) {
		this.sex = sex;
	}

	/**
	 * @return phone
	 */
	public String getPhone() {
		return this.phone;
	}

	/**
	 * @param phone
	 *            String
	 */
	public void setPhone(String phone) {
		this.phone = phone;
	}

	/**
	 * @return di
	 */
	public String getDi() {
		return this.di;
	}

	/**
	 * @param di
	 *            String
	 */
	public void setDi(String di) {
		this.di = di;
	}

	/**
	 * @return lastTime
	 */
	public String getLastTime() {
		return this.lastTime;
	}

	/**
	 * @param lastTime
	 *            String
	 */
	public void setLastTime(String lastTime) {
		this.lastTime = lastTime;
	}

}
