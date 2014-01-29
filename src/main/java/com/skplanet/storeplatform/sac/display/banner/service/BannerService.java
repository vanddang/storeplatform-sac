package com.skplanet.storeplatform.sac.display.banner.service;

import java.io.IOException;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;

import com.skplanet.storeplatform.sac.client.display.vo.banner.BannerReq;
import com.skplanet.storeplatform.sac.client.display.vo.banner.BannerRes;
import com.skplanet.storeplatform.sac.common.header.vo.SacRequestHeader;

public interface BannerService {

	/**
	 * <pre>
	 * 배너 리스트 조회
	 * </pre>
	 * 
	 * @param BannerReq
	 *            bannerReq
	 * @param SacRequestHeader
	 *            requestHeader
	 * @return BannerRes
	 */
	public BannerRes searchBannerList(BannerReq bannerReq, SacRequestHeader requestHeader)
			throws JsonGenerationException, JsonMappingException, IOException, Exception;
}
