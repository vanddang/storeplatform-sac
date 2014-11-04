package com.skplanet.storeplatform.sac.client.member.vo.common;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;

/**
 * PayPlanet InApp용 인증 Tstore 기타정보.
 * 
 * Updated on : 2014. 11. 3. Updated by : 반범진, SK 플래닛.
 */
public class TstoreEtcInfo extends CommonInfo {

	/**
	 * SKT 통신과금 부가서비스 가입여부.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * SKT 통신과금 부가서비스 가입여부.
	 */
	private String svcJoinResult;

	/**
	 * OneId 실명인증 여부.
	 */
	private String isRealName;

	/**
	 * OneId CI 존재여부.
	 */
	private String ciYn;

	/**
	 * OCB 이용동의 여부.
	 */
	private String isMemberPoint;

	/**
	 * @return svcJoinResult
	 */
	public String getSvcJoinResult() {
		return this.svcJoinResult;
	}

	/**
	 * @param svcJoinResult
	 *            String
	 */
	public void setSvcJoinResult(String svcJoinResult) {
		this.svcJoinResult = svcJoinResult;
	}

	/**
	 * @return isRealName
	 */
	public String getIsRealName() {
		return this.isRealName;
	}

	/**
	 * @param isRealName
	 *            String
	 */
	public void setIsRealName(String isRealName) {
		this.isRealName = isRealName;
	}

	/**
	 * @return ciYn
	 */
	public String getCiYn() {
		return this.ciYn;
	}

	/**
	 * @param ciYn
	 *            String
	 */
	public void setCiYn(String ciYn) {
		this.ciYn = ciYn;
	}

	/**
	 * @return isMemberPoint
	 */
	public String getIsMemberPoint() {
		return this.isMemberPoint;
	}

	/**
	 * @param isMemberPoint
	 *            String
	 */
	public void setIsMemberPoint(String isMemberPoint) {
		this.isMemberPoint = isMemberPoint;
	}

}
