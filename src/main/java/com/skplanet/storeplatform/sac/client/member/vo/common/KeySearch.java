package com.skplanet.storeplatform.sac.client.member.vo.common;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;

/**
 * 판매자회원 기본 정보 조회 req시 넘겨주는 key값
 * 
 * Updated on : 2014. 1. 7. Updated by : 한서구, 부르칸.
 */
@JsonSerialize(include = Inclusion.NON_NULL)
public class KeySearch extends CommonInfo {

	private static final long serialVersionUID = 1L;

	/** 검색값. */
	private String keyString;
	/** 검색 구분값. */
	private String keyType;

	/**
	 * @return the keyString
	 */
	public String getKeyString() {
		return this.keyString;
	}

	/**
	 * @param keyString
	 *            the keyString to set
	 */
	public void setKeyString(String keyString) {
		this.keyString = keyString;
	}

	/**
	 * @return the keyType
	 */
	public String getKeyType() {
		return this.keyType;
	}

	/**
	 * @param keyType
	 *            the keyType to set
	 */
	public void setKeyType(String keyType) {
		this.keyType = keyType;
	}

}
