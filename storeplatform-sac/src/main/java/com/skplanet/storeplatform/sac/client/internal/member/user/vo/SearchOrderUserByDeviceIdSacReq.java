package com.skplanet.storeplatform.sac.client.internal.member.user.vo;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;

/**
 * deviceId 와 구매일시로 최근 userKey, deviceKey 조회 [REQUEST]
 * 
 * Updated on : 2016. 2. 2. Updated by : 최진호, 보고지티.
 */
@JsonSerialize(include = Inclusion.NON_NULL)
public class SearchOrderUserByDeviceIdSacReq extends CommonInfo {

	private static final long serialVersionUID = 1L;

	/** 기기 ID. */
	private String deviceId;

	/** 기기 MDN */
	private String mdn;

	/** 구매일시. */
	@NotBlank
	@Length(min = 14, max = 14)
	private String orderDt;

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
	 * @return the mdn
	 */
	public String getMdn() {
		return this.mdn;
	}

	/**
	 * @param mdn
	 *            the mdn to set
	 */
	public void setMdn(String mdn) {
		this.mdn = mdn;
	}

	/**
	 * @return the orderDt
	 */
	public String getOrderDt() {
		return this.orderDt;
	}

	/**
	 * @param orderDt
	 *            the orderDt to set
	 */
	public void setOrderDt(String orderDt) {
		this.orderDt = orderDt;
	}
}
