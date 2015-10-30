package com.skplanet.storeplatform.member.client.user.sci.vo;

import java.util.List;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;
import com.skplanet.storeplatform.member.client.common.vo.CommonRequest;

/**
 * 회원 가입여부 리스트 조회 요청 Value Object
 * 
 * Updated on : 2014. 12. 12. Updated by : Rejoice, Burkhan
 */
public class ExistListRequest extends CommonInfo {

	/** The Constant serial Version UID. */
	private static final long serialVersionUID = 1L;

	/** 공통 요청 Value Object. */
	private CommonRequest commonRequest;

	/** 휴대기기 ID 리스트. */
	private List<String> deviceIdList;

	/**
	 * @return the commonRequest
	 */
	public CommonRequest getCommonRequest() {
		return this.commonRequest;
	}

	/**
	 * @param commonRequest
	 *            the commonRequest to set
	 */
	public void setCommonRequest(CommonRequest commonRequest) {
		this.commonRequest = commonRequest;
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

}
