package com.skplanet.storeplatform.sac.display.localsci.sci.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;

import com.skplanet.storeplatform.framework.core.exception.StorePlatformException;
import com.skplanet.storeplatform.framework.integration.bean.LocalSCI;
import com.skplanet.storeplatform.sac.client.internal.display.localsci.sci.FreePassInfoSCI;
import com.skplanet.storeplatform.sac.client.internal.display.localsci.vo.EpisodeInfoReq;
import com.skplanet.storeplatform.sac.client.internal.display.localsci.vo.EpisodeInfoSacRes;
import com.skplanet.storeplatform.sac.client.internal.display.localsci.vo.FreePassBasicInfoSacReq;
import com.skplanet.storeplatform.sac.client.internal.display.localsci.vo.FreePassBasicInfoSacRes;
import com.skplanet.storeplatform.sac.client.internal.display.localsci.vo.FreePassDrmInfo;
import com.skplanet.storeplatform.sac.client.internal.display.localsci.vo.FreePassInfo;
import com.skplanet.storeplatform.sac.client.internal.display.localsci.vo.FreePassInfoSacReq;
import com.skplanet.storeplatform.sac.display.common.constant.DisplayConstants;
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
	 * 정액제 상품 DRM 정보 조회 V2.
	 * </pre>
	 * 
	 * @param req
	 *            파라미터
	 * @return FreePassDrmInfo 상품 메타 정보 리스트
	 */
	@Override
	public FreePassDrmInfo searchFreePassDrmInfoV2(@Validated FreePassInfoSacReq req) {
		return this.freePassInfoService.searchFreePassDrmInfoV2(req);
	}	

	/**
	 * <pre>
	 * 정액권의 에피소드 상품 목록 조회.
	 * </pre>
	 * 
	 * @param req
	 *            파라미터
	 * @return EpisodeInfoSacRes 상품 메타 정보 리스트
	 */
	@Override
	public EpisodeInfoSacRes searchEpisodeList(@Validated EpisodeInfoReq req) {
		return this.freePassInfoService.searchEpisodeList(req);
	}

	/**
	 * <pre>
	 * 정액권 기본 정보 조회.
	 * </pre>
	 * 
	 * @param req
	 *            파라미터
	 * @return FreePassBasicInfoSacRes 상품 메타 정보 리스트
	 */
	@Override
	public FreePassBasicInfoSacRes searchFreepassBasicList(@Validated FreePassBasicInfoSacReq req) {
		if (req.getList().size() > DisplayConstants.DP_PRODUCT_INFO_PARAMETER_LIMIT) {
			throw new StorePlatformException("SAC_DSP_0004", "list", DisplayConstants.DP_PRODUCT_INFO_PARAMETER_LIMIT);
		}
		return this.freePassInfoService.searchFreepassBasicList(req);
	}

}
