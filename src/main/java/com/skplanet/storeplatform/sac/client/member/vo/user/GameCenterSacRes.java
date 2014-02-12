package com.skplanet.storeplatform.sac.client.member.vo.user;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;

/**
 * [RESPONSE] 회원 프로비저닝 이력 조회 - Game Center
 * 
 * Updated on : 2014. 2. 12. Updated by : 강신완, 부르칸.
 */
@JsonSerialize(include = Inclusion.NON_NULL)
public class GameCenterSacRes extends CommonInfo {

	private static final long serialVersionUID = 1L;

	private String deviceId;
	private String fileDate;
	private String gameCenterNo;
	private String preDeviceId;
	private String preUserKey;
	private String regDate;
	private String requestDate;
	private String requestType;
	private String statusCode;
	private String updateDate;
	private String userKey;
	private String workCode;

	public String getDeviceId() {
		return this.deviceId;
	}

	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}

	public String getFileDate() {
		return this.fileDate;
	}

	public void setFileDate(String fileDate) {
		this.fileDate = fileDate;
	}

	public String getGameCenterNo() {
		return this.gameCenterNo;
	}

	public void setGameCenterNo(String gameCenterNo) {
		this.gameCenterNo = gameCenterNo;
	}

	public String getPreDeviceId() {
		return this.preDeviceId;
	}

	public void setPreDeviceId(String preDeviceId) {
		this.preDeviceId = preDeviceId;
	}

	public String getPreUserKey() {
		return this.preUserKey;
	}

	public void setPreUserKey(String preUserKey) {
		this.preUserKey = preUserKey;
	}

	public String getRegDate() {
		return this.regDate;
	}

	public void setRegDate(String regDate) {
		this.regDate = regDate;
	}

	public String getRequestDate() {
		return this.requestDate;
	}

	public void setRequestDate(String requestDate) {
		this.requestDate = requestDate;
	}

	public String getRequestType() {
		return this.requestType;
	}

	public void setRequestType(String requestType) {
		this.requestType = requestType;
	}

	public String getStatusCode() {
		return this.statusCode;
	}

	public void setStatusCode(String statusCode) {
		this.statusCode = statusCode;
	}

	public String getUpdateDate() {
		return this.updateDate;
	}

	public void setUpdateDate(String updateDate) {
		this.updateDate = updateDate;
	}

	public String getUserKey() {
		return this.userKey;
	}

	public void setUserKey(String userKey) {
		this.userKey = userKey;
	}

	public String getWorkCode() {
		return this.workCode;
	}

	public void setWorkCode(String workCode) {
		this.workCode = workCode;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
