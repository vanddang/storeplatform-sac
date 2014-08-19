package com.skplanet.storeplatform.sac.client.member.vo.miscellaneous;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;
import org.hibernate.validator.constraints.NotBlank;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;

/**
 * [REQUEST] DCD 가입
 * 
 * Updated on : 2014. 8. 6. Updated by : 김영균, 지티소프트.
 */
@JsonSerialize(include = Inclusion.NON_NULL)
public class CreateDCDReq extends CommonInfo {
	private static final long serialVersionUID = 1L;

	/**
	 * 기기 ID.
	 */
	@NotBlank
	private String deviceId;

	/**
	 * 요청 구분 코드
	 */
	@NotBlank
	private String entryClass;

	/**
	 * DCD 가입처리 대상 상품 아이디
	 */
	@NotBlank
	private String prodId;

	/**
	 * @return the deviceId
	 */
	public String getDeviceId() {
		return this.deviceId;
	}

	/**
	 * @param deviceId
	 *            the deviceId to set
	 */
	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}

	/**
	 * @return the entryClass
	 */
	public String getEntryClass() {
		return this.entryClass;
	}

	/**
	 * @param entryClass
	 *            the entryClass to set
	 */
	public void setEntryClass(String entryClass) {
		this.entryClass = entryClass;
	}

	/**
	 * @return the pid
	 */
	public String getProdId() {
		return this.prodId;
	}

	/**
	 * @param pid
	 *            the pid to set
	 */
	public void setProdId(String prodId) {
		this.prodId = prodId;
	}

}
