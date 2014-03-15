package com.skplanet.storeplatform.sac.client.display.vo.other;

import org.hibernate.validator.constraints.NotBlank;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;

/**
 * Package 정보로 상품ID 조회 Request Value Object.
 * 
 * Updated on : 2014. 3. 11. Updated by : 오승민, 인크로스.
 */
public class OtherPackageListReq extends CommonInfo {
	private static final long serialVersionUID = 1L;

	/**
	 * Package 정보.
	 */
	@NotBlank
	private String packageInfo;

	/**
	 * @return the packageInfo
	 */
	public String getPackageInfo() {
		return this.packageInfo;
	}

	/**
	 * @param packageInfo
	 *            the packageInfo to set
	 */
	public void setPackageInfo(String packageInfo) {
		this.packageInfo = packageInfo;
	}

}
