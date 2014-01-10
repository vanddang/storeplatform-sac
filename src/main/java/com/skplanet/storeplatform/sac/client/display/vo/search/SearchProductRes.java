package com.skplanet.storeplatform.sac.client.display.vo.search;

import java.util.ArrayList;
import java.util.List;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.CommonResponse;

public class SearchProductRes extends CommonInfo {
	private static final long serialVersionUID = 11123123142L;
	private CommonResponse commonRes;

	List<String> prodIdList = new ArrayList<String>();

	public CommonResponse getCommonRes() {
		return this.commonRes;
	}

	public void setCommonRes(CommonResponse commonRes) {
		this.commonRes = commonRes;
	}

	public List<String> getProdIdList() {
		return this.prodIdList;
	}

	public void setProdIdList(List<String> prodIdList) {
		this.prodIdList = prodIdList;
	}

}
