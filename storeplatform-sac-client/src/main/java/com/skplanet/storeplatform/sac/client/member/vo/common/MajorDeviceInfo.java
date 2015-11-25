package com.skplanet.storeplatform.sac.client.member.vo.common;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;

/**
 * 단말 주요정보 셋팅
 * 
 * Updated on : 2014. 1. 3. Updated by : 심대진, 다모아 솔루션.
 */
public class MajorDeviceInfo extends CommonInfo {

	private static final long serialVersionUID = 1L;

	/**
	 * UA 코드
	 */
	private String uacd;

	/**
	 * OMD UA 코드
	 */
	private String omdUacd;

	/**
	 * 이동 통신사
	 */
	private String deviceTelecom;

	/**
	 * 단말 모델
	 */
	private String deviceModelNo;

	/**
	 * 단말명
	 */
	private String deviceNickName;

	/**
	 * SKT 회원관리번호
	 */
	private String svcMangNum;

	/**
	 * @return String : uacd
	 */
	public String getUacd() {
		return this.uacd;
	}

	/**
	 * @param uacd
	 *            String : the uacd to set
	 */
	public void setUacd(String uacd) {
		this.uacd = uacd;
	}

	/**
	 * @return String : omdUacd
	 */
	public String getOmdUacd() {
		return this.omdUacd;
	}

	/**
	 * @param omdUacd
	 *            String : the omdUacd to set
	 */
	public void setOmdUacd(String omdUacd) {
		this.omdUacd = omdUacd;
	}

	/**
	 * @return String : deviceTelecom
	 */
	public String getDeviceTelecom() {
		return this.deviceTelecom;
	}

	/**
	 * @param deviceTelecom
	 *            String : the deviceTelecom to set
	 */
	public void setDeviceTelecom(String deviceTelecom) {
		this.deviceTelecom = deviceTelecom;
	}

	/**
	 * @return String : deviceModelNo
	 */
	public String getDeviceModelNo() {
		return this.deviceModelNo;
	}

	/**
	 * @param deviceModelNo
	 *            String : the deviceModelNo to set
	 */
	public void setDeviceModelNo(String deviceModelNo) {
		this.deviceModelNo = deviceModelNo;
	}

	/**
	 * @return String : deviceNickName
	 */
	public String getDeviceNickName() {
		return this.deviceNickName;
	}

	/**
	 * @param deviceNickName
	 *            String : the deviceNickName to set
	 */
	public void setDeviceNickName(String deviceNickName) {
		this.deviceNickName = deviceNickName;
	}

	public String getSvcMangNum() {
		return this.svcMangNum;
	}

	public void setSvcMangNum(String svcMangNum) {
		this.svcMangNum = svcMangNum;
	}

}
