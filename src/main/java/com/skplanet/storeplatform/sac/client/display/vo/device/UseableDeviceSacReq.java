package com.skplanet.storeplatform.sac.client.display.vo.device;

import org.hibernate.validator.constraints.NotBlank;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;

/**
 * 상품 ID에 대한 단말 Provisioning Request Value Object.
 * 
 * Updated on : 2014. 2. 3. Updated by : 오승민, 인크로스.
 */
public class UseableDeviceSacReq extends CommonInfo {
	private static final long serialVersionUID = 1L;

	private String langCd;

	private String productId; // 상품 ID
	@NotBlank
	private String svcGrpCd; // 서비스 그룹코드
	private String mnftCompCd;
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
	 * @return the productId
	 */
	public String getProductId() {
		return this.productId;
	}

	/**
	 * @param productId
	 *            the productId to set
	 */
	public void setProductId(String productId) {
		this.productId = productId;
	}

	/**
	 * @return the svcGrpCd
	 */
	public String getSvcGrpCd() {
		return this.svcGrpCd;
	}

	/**
	 * @param svcGrpCd
	 *            the svcGrpCd to set
	 */
	public void setSvcGrpCd(String svcGrpCd) {
		this.svcGrpCd = svcGrpCd;
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
