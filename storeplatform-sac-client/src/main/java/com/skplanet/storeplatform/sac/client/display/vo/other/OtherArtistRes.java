package com.skplanet.storeplatform.sac.client.display.vo.other;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.CommonResponse;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Contributor;

public class OtherArtistRes extends CommonInfo {
	private static final long serialVersionUID = 1L;

	private CommonResponse commonResponse;
	private Contributor contributor;

	public CommonResponse getCommonResponse() {
		return this.commonResponse;
	}

	public void setCommonResponse(CommonResponse commonResponse) {
		this.commonResponse = commonResponse;
	}

	public Contributor getContributor() {
		return this.contributor;
	}

	public void setContributor(Contributor contributor) {
		this.contributor = contributor;
	}
}
