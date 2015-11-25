/**
 * 
 */
package com.skplanet.storeplatform.sac.client.member.vo.user;

import java.util.List;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;

/**
 * UserKey 별 회원 정보.
 * 
 * Updated on : 2014. 2. 24. Updated by : 김다슬, 인크로스.
 */
public class UserInfoByUserKey extends CommonInfo {

	private static final long serialVersionUID = 1L;

	/**
	 * 사용자 구분 코드. - US011501 기기 사용자 - US011502 IDP 사용자 - US011503 OneID 사용자
	 */
	private String userType;

	/**
	 * 사용자 메인 상태 코드. - US010201 정상 - US010202 자의탈퇴/직권탈퇴 - US010203 가가입 - US010204 계정잠금/7일이용정지/30일이용정지/영구이용정지
	 */
	private String userMainStatus;

	/**
	 * 사용자 서브 상태 코드. - US010301 정상 - US010302 탈퇴신청 - US010303 탈퇴완료 - US010304 가입승인 만료 - US010305 가입승인 대기 - US010306
	 * 이메일변경승인대기 - US010307 비밀번호 횟수 제한에 따른 계정잠금 - US010308 7일이용정지 - US010309 30일이용정지 - US010310 영구이용정지 - US010311 전환신청 -
	 * US010312 전환재신청 - US010313 전환거절 - US010314 변동처리대상 - US010315 가입거절 - US010316 탈퇴거절 - US010317 ID재사용
	 */
	private String userSubStatus;

	/**
	 * 사용자 ID.
	 */
	private String userId;

	/**
	 * 기기 ID. ( mdn, uuid, macAddress )
	 */
	private List<String> deviceIdList;

	/**
	 * 회원키.
	 */
	private String userKey;

	/**
	 * @return the userType
	 */
	public String getUserType() {
		return this.userType;
	}

	/**
	 * @param userType
	 *            the userType to set
	 */
	public void setUserType(String userType) {
		this.userType = userType;
	}

	/**
	 * @return the userMainStatus
	 */
	public String getUserMainStatus() {
		return this.userMainStatus;
	}

	/**
	 * @param userMainStatus
	 *            the userMainStatus to set
	 */
	public void setUserMainStatus(String userMainStatus) {
		this.userMainStatus = userMainStatus;
	}

	/**
	 * @return the userSubStatus
	 */
	public String getUserSubStatus() {
		return this.userSubStatus;
	}

	/**
	 * @param userSubStatus
	 *            the userSubStatus to set
	 */
	public void setUserSubStatus(String userSubStatus) {
		this.userSubStatus = userSubStatus;
	}

	/**
	 * @return the userId
	 */
	public String getUserId() {
		return this.userId;
	}

	/**
	 * @param userId
	 *            the userId to set
	 */
	public void setUserId(String userId) {
		this.userId = userId;
	}

	/**
	 * @return the deviceIdList
	 */
	public List<String> getDeviceIdList() {
		return this.deviceIdList;
	}

	/**
	 * @param deviceIdList
	 *            the deviceIdList to set
	 */
	public void setDeviceIdList(List<String> deviceIdList) {
		this.deviceIdList = deviceIdList;
	}

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

}
