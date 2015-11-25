package com.skplanet.storeplatform.sac.client.display.vo.device;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;

/**
 * 단말 리스트 조회 Request Value Object.
 * 
 */
public class DeviceModelListSacReq extends CommonInfo {
	private static final long serialVersionUID = 1L;

	private String langCd;

	private String cmntCompCd;
	private String mnftCompCd;
	private String deviceModelCd;
	private String deviceModelNm;
	private Integer offset;
	private Integer count;

	/**
	 * @return the langCd
	 */
	public String getLangCd() {
		return this.langCd;
	}

	/**
	 * @param langCd
	 *            the langCd to set
	 */
	public void setLangCd(String langCd) {
		this.langCd = langCd;
	}

	/**
	 * @return the cmntCompCd
	 */
	public String getCmntCompCd() {
		return cmntCompCd;
	}

	/**
	 * @param cmntCompCd
	 *            the cmntCompCd to set
	 */
	public void setCmntCompCd(String cmntCompCd) {
		this.cmntCompCd = cmntCompCd;
	}

	/**
	 * @return the mnftCompCd
	 */
	public String getMnftCompCd() {
		return this.mnftCompCd;
	}

	/**
	 * @param mnftCompCd
	 *            the mnftCompCd to set
	 */
	public void setMnftCompCd(String mnftCompCd) {
		this.mnftCompCd = mnftCompCd;
	}

	/**
	 * @return the deviceModelCd
	 */
	public String getDeviceModelCd() {
		return deviceModelCd;
	}

	/**
	 * @param deviceModelCd
	 *            the deviceModelCd to set
	 */
	public void setDeviceModelCd(String deviceModelCd) {
		this.deviceModelCd = deviceModelCd;
	}

	/**
	 * @return the deviceModelNm
	 */
	public String getDeviceModelNm() {
		return this.deviceModelNm;
	}

	/**
	 * @param deviceModelNm
	 *            the deviceModelNm to set
	 */
	public void setDeviceModelNm(String deviceModelNm) {
		this.deviceModelNm = deviceModelNm;
	}

	/**
	 * @return the offset
	 */
	public Integer getOffset() {
		return this.offset;
	}

	/**
	 * @param offset
	 *            the offset to set
	 */
	public void setOffset(Integer offset) {
		this.offset = offset;
	}

	/**
	 * @return the count
	 */
	public Integer getCount() {
		return this.count;
	}

	/**
	 * @param count
	 *            the count to set
	 */
	public void setCount(Integer count) {
		this.count = count;
	}

}
