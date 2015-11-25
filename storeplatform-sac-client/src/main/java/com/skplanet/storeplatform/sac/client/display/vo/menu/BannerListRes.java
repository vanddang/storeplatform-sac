package com.skplanet.storeplatform.sac.client.display.vo.menu;

import java.util.List;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.CommonResponse;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Banner;

public class BannerListRes extends CommonInfo {
	private CommonResponse commonRes;
	private List<Banner> bannerList;

	public CommonResponse getCommonRes() {
		return this.commonRes;
	}

	public void setCommonRes(CommonResponse commonRes) {
		this.commonRes = commonRes;
	}

	public List<Banner> getBannerList() {
		return this.bannerList;
	}

	public void setBannerList(List<Banner> bannerList) {
		this.bannerList = bannerList;
	}

}
