package com.skplanet.storeplatform.sac.client.member.vo.user;

import java.io.Serializable;

import org.hibernate.validator.constraints.NotEmpty;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;

/**
 * [REQUEST] Save&Sync 인증.
 * 
 * Updated on : 2014. 3. 5. Updated by : 반범진. 지티소프트.
 */
public class AuthorizeSaveAndSyncByMacReq extends CommonInfo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * mac 주소
	 */
	@NotEmpty(message = "파라미터가 존재하지 않습니다.")
	private String macAddress;

	/**
	 * @return macAddress
	 */
	public String getMacAddress() {
		return this.macAddress;
	}

	/**
	 * @param macAddress
	 *            String
	 */
	public void setMacAddress(String macAddress) {
		this.macAddress = macAddress;
	}

}
