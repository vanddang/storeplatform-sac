package com.skplanet.storeplatform.sac.client.display.vo.device;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;
import org.hibernate.validator.constraints.NotBlank;

/**
 * 단말 모델코드 조회 (by UaCd) Request Value Object.
 */
public class DeviceUaCdSacReq extends CommonInfo {
	private static final long serialVersionUID = 1L;

	@NotBlank
	private String uaCd;

	/**
	 * @return the uaCd
	 */
	public String getUaCd() {
		return this.uaCd;
	}

	/**
	 * @param uaCd
	 *            the uaCd to set
	 */
	public void setUaCd(String uaCd) {
		this.uaCd = uaCd;
	}
}
