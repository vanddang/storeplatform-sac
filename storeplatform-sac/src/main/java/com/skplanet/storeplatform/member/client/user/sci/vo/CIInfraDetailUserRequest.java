package com.skplanet.storeplatform.member.client.user.sci.vo;

import java.io.Serializable;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;
import com.skplanet.storeplatform.member.client.common.vo.CommonRequest;

/**
 * [REQUEST] CI Infra 회원 상세 정보 조회.
 * 
 * Updated on : 2014. 10. 2. Updated by : 반범진, SK 플래닛.
 */
public class CIInfraDetailUserRequest extends CommonInfo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/** 공통 요청 Value Object. */
	private CommonRequest commonRequest;

	/**
	 * 검색 구분 값(통합서비스관리번호 : K01 product서비스관리번호: K02 Login ID: K03 MDN: K04).
	 */
	private String keyType;

	/**
	 * 검색 값.
	 */
	private String key;

	/**
	 * 공통 요청 Value Object를 리턴한다.
	 * 
	 * @return commonRequest - 공통 요청 Value Object
	 */
	public CommonRequest getCommonRequest() {
		return this.commonRequest;
	}

	/**
	 * 공통 요청 Value Object를 설정한다.
	 * 
	 * @param commonRequest
	 *            공통 요청 Value Object
	 */
	public void setCommonRequest(CommonRequest commonRequest) {
		this.commonRequest = commonRequest;
	}

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
