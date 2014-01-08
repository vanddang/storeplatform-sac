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

	private String keyString;
	private String keyType;

	public String getKeyString() {
		return this.keyString;
	}

	public void setKeyString(String keyString) {
		this.keyString = keyString;
	}

	public String getKeyType() {
		return this.keyType;
	}

	public void setKeyType(String keyType) {
		this.keyType = keyType;
	}

}
