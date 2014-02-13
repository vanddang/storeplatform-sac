package com.skplanet.storeplatform.sac.client.display.vo.banner;

import java.util.List;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.CommonResponse;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Banner;

@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class BannerRes extends CommonInfo {
	private static final long serialVersionUID = 1L;
	private CommonResponse commonResponse;
	private List<Banner> bannerList;

	public CommonResponse getCommonResponse() {
		return this.commonResponse;
	}

	public void setCommonResponse(CommonResponse commonResponse) {
		this.commonResponse = commonResponse;
	}

	public List<Banner> getBannerList() {
		return this.bannerList;
	}

	public void setBannerList(List<Banner> bannerList) {
		this.bannerList = bannerList;
	}

}
