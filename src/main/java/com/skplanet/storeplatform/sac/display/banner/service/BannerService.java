package com.skplanet.storeplatform.sac.display.banner.service;

import com.skplanet.storeplatform.sac.client.display.vo.banner.BannerSacReq;
import com.skplanet.storeplatform.sac.client.display.vo.banner.BannerSacRes;
import com.skplanet.storeplatform.sac.common.header.vo.SacRequestHeader;

/**
 * Banner Service 인터페이스(CoreStoreBusiness)
 * 
 * Updated on : 2014. 2. 21. Updated by : 이태희
 */
public interface BannerService {
	/**
	 * <pre>
	 * 배너 리스트 조회.
	 * </pre>
	 * 
	 * @param header
	 *            header
	 * @param bannerReq
	 *            bannerReq
	 * @return BannerSacRes
	 */
	public BannerSacRes searchBannerList(SacRequestHeader header, BannerSacReq bannerReq);
}
