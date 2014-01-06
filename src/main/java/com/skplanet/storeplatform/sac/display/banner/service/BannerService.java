package com.skplanet.storeplatform.sac.display.banner.service;

import com.skplanet.storeplatform.sac.client.display.vo.banner.BannerReq;
import com.skplanet.storeplatform.sac.client.display.vo.banner.BannerRes;

public interface BannerService {
	public BannerRes searchBannerList(BannerReq req);
}
