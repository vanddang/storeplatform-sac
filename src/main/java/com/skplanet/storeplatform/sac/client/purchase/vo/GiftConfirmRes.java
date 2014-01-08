package com.skplanet.storeplatform.sac.client.purchase.vo;

import java.io.Serializable;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;

public class GiftConfirmRes extends CommonInfo implements Serializable {
	private static final long serialVersionUID = 1L;

	private Integer count; // update count

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
