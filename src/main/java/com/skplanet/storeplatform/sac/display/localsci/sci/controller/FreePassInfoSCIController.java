package com.skplanet.storeplatform.sac.display.localsci.sci.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;

import com.skplanet.storeplatform.framework.integration.bean.LocalSCI;
import com.skplanet.storeplatform.sac.client.internal.display.localsci.sci.FreePassInfoSCI;
import com.skplanet.storeplatform.sac.client.internal.display.localsci.vo.EpisodeInfoReq;
import com.skplanet.storeplatform.sac.client.internal.display.localsci.vo.EpisodeInfoSacRes;
import com.skplanet.storeplatform.sac.client.internal.display.localsci.vo.FreePassInfo;
import com.skplanet.storeplatform.sac.client.internal.display.localsci.vo.FreePassInfoSacReq;
import com.skplanet.storeplatform.sac.display.localsci.sci.service.FreePassInfoService;

/**
 * 
 * FreePassInfoSCIController Controller
 * 
 * 정액제 상품 DRM 정보 조회.
 * 
 * Updated on : 2014. 2. 28. Updated by : 김형식 , 지티소프트
 */
@LocalSCI
public class FreePassInfoSCIController implements FreePassInfoSCI {
	@Autowired
	private FreePassInfoService freePassInfoService;

	/**
	 * <pre>
	 * 정액제 상품 DRM 정보 조회.
	 * </pre>
	 * 
	 * @param req
	 *            파라미터
	 * @return PaymentInfoSacRes 상품 메타 정보 리스트
	 */
	@Override
	public FreePassInfo searchFreePassDrmInfo(@Validated FreePassInfoSacReq req) {
		return this.freePassInfoService.searchFreePassDrmInfo(req);
	}

	/**
	 * <pre>
	 * 정액권의 에피소드 상품 목록 조회.
	 * </pre>
	 * 
	 * @param req
	 *            파라미터
	 * @return FreePassInfoRes 상품 메타 정보 리스트
	 */
	@Override
	public EpisodeInfoSacRes searchEpisodeList(@Validated EpisodeInfoReq req) {
		return this.freePassInfoService.searchEpisodeList(req);
	}

}
