package com.skplanet.storeplatform.sac.client.display.vo.category;

import org.hibernate.validator.constraints.NotBlank;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;

public class CategorySpecificReq extends CommonInfo {
	private static final long serialVersionUID = 1L;

	@NotBlank
	private String list;

    public String getList() {
		return this.list;
	}

	public void setList(String list) {
		this.list = list;
	}

}
