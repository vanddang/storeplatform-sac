package com.skplanet.storeplatform.sac.client.display.vo.other;

import org.hibernate.validator.constraints.NotBlank;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;

/**
 * Package 정보로 상품ID 조회 Request Value Object.
 * 
 * Updated on : 2014. 3. 11. Updated by : 오승민, 인크로스.
 */
public class OtherPakcageListReq extends CommonInfo {
	private static final long serialVersionUID = 1L;

	/**
	 * Package 이름 리스트.
	 */
	@NotBlank
	private String list;

	/**
	 * @return the list
	 */
	public String getList() {
		return this.list;
	}

	/**
	 * @param list
	 *            the list to set
	 */
	public void setList(String list) {
		this.list = list;
	}

}
