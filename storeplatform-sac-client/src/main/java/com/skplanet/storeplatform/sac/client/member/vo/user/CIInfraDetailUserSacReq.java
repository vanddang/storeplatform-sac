package com.skplanet.storeplatform.sac.client.member.vo.user;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;

/**
 * [REQUEST] CI Infra 회원 상세 정보 조회.
 * 
 * Updated on : 2014. 10. 2. Updated by : 반범진, SK 플래닛.
 */
public class CIInfraDetailUserSacReq extends CommonInfo {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 검색 구분 값(통합서비스관리번호 : K01 product서비스관리번호: K02 Login ID: K03 MDN: K04).
	 */
	private String keyType;

	/**
	 * 검색 값.
	 */
	private String key;

	/**
	 * <pre>
	 * 검색 구분 값 리턴.
	 * </pre>
	 * 
	 * @return keyType
	 */
	public String getKeyType() {
		return this.keyType;
	}

	/**
	 * <pre>
	 * 검색 구분 값 셋팅.
	 * </pre>
	 * 
	 * @param keyType
	 *            String
	 */
	public void setKeyType(String keyType) {
		this.keyType = keyType;
	}

	/**
	 * <pre>
	 * 검색 값 리턴.
	 * </pre>
	 * 
	 * @return key
	 */
	public String getKey() {
		return this.key;
	}

	/**
	 * <pre>
	 * 검색 값 셋팅.
	 * </pre>
	 * 
	 * @param key
	 *            String
	 */
	public void setKey(String key) {
		this.key = key;
	}

}
