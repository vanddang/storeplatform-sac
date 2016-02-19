package com.skplanet.storeplatform.sac.client.member.vo.user;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;

/**
 * [REQUEST] 회원 정보 조회
 * 
 * Updated on : 2016. 2. 18. Updated by : 최진호, 보고지티.
 */
@JsonSerialize(include = Inclusion.NON_NULL)
public class DetailReq extends CommonInfo {

	private static final long serialVersionUID = 1L;

	/** 사용자 ID. */
	private String userId;
	/** 기기 ID. */
	private String deviceId;
	/** 사용자 키. */
	private String userKey;
	/** 기기 키. */
	private String deviceKey;
	/** 사용자 타입. */
	private String userType;

	private SearchExtentReq searchExtent;

	public String getUserId() {
		return this.userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getDeviceId() {
		return this.deviceId;
	}

	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}

	public String getUserKey() {
		return this.userKey;
	}

	public void setUserKey(String userKey) {
		this.userKey = userKey;
	}

	public String getDeviceKey() {
		return this.deviceKey;
	}

	public void setDeviceKey(String deviceKey) {
		this.deviceKey = deviceKey;
	}

	public SearchExtentReq getSearchExtent() {
		return this.searchExtent;
	}

	public void setSearchExtent(SearchExtentReq searchExtent) {
		this.searchExtent = searchExtent;
	}

	public String getUserType() {
		return this.userType;
	}

	public void setUserType(String userType) {
		this.userType = userType;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
