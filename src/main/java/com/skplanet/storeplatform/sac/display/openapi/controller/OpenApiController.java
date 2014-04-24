/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.display.openapi.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.skplanet.storeplatform.framework.core.exception.StorePlatformException;
import com.skplanet.storeplatform.framework.core.util.StringUtils;
import com.skplanet.storeplatform.sac.client.display.vo.openapi.AppDetailByPackageNameSacReq;
import com.skplanet.storeplatform.sac.client.display.vo.openapi.AppDetailByPackageNameSacRes;
import com.skplanet.storeplatform.sac.client.display.vo.openapi.AppDetailByProductIdSacReq;
import com.skplanet.storeplatform.sac.client.display.vo.openapi.AppDetailByProductIdSacRes;
import com.skplanet.storeplatform.sac.client.display.vo.openapi.BestDownloadAppSacReq;
import com.skplanet.storeplatform.sac.client.display.vo.openapi.BestDownloadAppSacRes;
import com.skplanet.storeplatform.sac.client.display.vo.openapi.BestDownloadMMSacReq;
import com.skplanet.storeplatform.sac.client.display.vo.openapi.BestDownloadMMSacRes;
import com.skplanet.storeplatform.sac.client.display.vo.openapi.DownloadBestSacReq;
import com.skplanet.storeplatform.sac.client.display.vo.openapi.DownloadBestSacRes;
import com.skplanet.storeplatform.sac.client.display.vo.openapi.NewAppRecommendSacReq;
import com.skplanet.storeplatform.sac.client.display.vo.openapi.NewAppRecommendSacRes;
import com.skplanet.storeplatform.sac.client.display.vo.openapi.NoProvisionSacReq;
import com.skplanet.storeplatform.sac.client.display.vo.openapi.NoProvisionSacRes;
import com.skplanet.storeplatform.sac.client.display.vo.openapi.SalesAppInfoSacReq;
import com.skplanet.storeplatform.sac.client.display.vo.openapi.SalesAppInfoSacRes;
import com.skplanet.storeplatform.sac.client.display.vo.openapi.SalesAppSacReq;
import com.skplanet.storeplatform.sac.client.display.vo.openapi.SalesAppSacRes;
import com.skplanet.storeplatform.sac.client.display.vo.openapi.SearchAppNameSacReq;
import com.skplanet.storeplatform.sac.client.display.vo.openapi.SearchAppNameSacRes;
import com.skplanet.storeplatform.sac.client.display.vo.openapi.SearchSellerNameSacReq;
import com.skplanet.storeplatform.sac.client.display.vo.openapi.SearchSellerNameSacRes;
import com.skplanet.storeplatform.sac.client.display.vo.openapi.SellerAppDetailReq;
import com.skplanet.storeplatform.sac.client.display.vo.openapi.SellerAppDetailRes;
import com.skplanet.storeplatform.sac.client.display.vo.openapi.SellerAppListReq;
import com.skplanet.storeplatform.sac.client.display.vo.openapi.SellerAppListRes;
import com.skplanet.storeplatform.sac.client.display.vo.openapi.SellerIdAppListReq;
import com.skplanet.storeplatform.sac.client.display.vo.openapi.SellerIdAppListRes;
import com.skplanet.storeplatform.sac.client.display.vo.openapi.SupportGameCenterSacReq;
import com.skplanet.storeplatform.sac.client.display.vo.openapi.SupportGameCenterSacRes;
import com.skplanet.storeplatform.sac.common.header.vo.SacRequestHeader;
import com.skplanet.storeplatform.sac.display.device.service.DeviceProductProvisioningService;
import com.skplanet.storeplatform.sac.display.device.service.DeviceProfileService;
import com.skplanet.storeplatform.sac.display.openapi.service.AppDetailByPkgNmService;
import com.skplanet.storeplatform.sac.display.openapi.service.AppDetailByProdIdService;
import com.skplanet.storeplatform.sac.display.openapi.service.BestDownloadAppService;
import com.skplanet.storeplatform.sac.display.openapi.service.BestDownloadMMService;
import com.skplanet.storeplatform.sac.display.openapi.service.DownloadBestService;
import com.skplanet.storeplatform.sac.display.openapi.service.NewAppRecommendService;
import com.skplanet.storeplatform.sac.display.openapi.service.NoProvisionService;
import com.skplanet.storeplatform.sac.display.openapi.service.SalesAppService;
import com.skplanet.storeplatform.sac.display.openapi.service.SearchAppNameService;
import com.skplanet.storeplatform.sac.display.openapi.service.SearchSellerNameService;
import com.skplanet.storeplatform.sac.display.openapi.service.SellerAppDetailService;
import com.skplanet.storeplatform.sac.display.openapi.service.SellerAppListService;
import com.skplanet.storeplatform.sac.display.openapi.service.SellerIdAppListService;
import com.skplanet.storeplatform.sac.display.openapi.service.SupportGameCenterService;

/**
 * Open API 관련 Controller
 * 
 * Updated on : 2014. 1. 27. Updated by : 오승민, 인크로스
 */
@Controller
@RequestMapping("/display/openapi")
public class OpenApiController {
	private final Logger log = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private DeviceProfileService deviceProfileService;

	@Autowired
	private DeviceProductProvisioningService deviceProductProvisioningService;

	@Autowired
	private DownloadBestService downloadBestService;

	@Autowired
	private NewAppRecommendService newAppRecommendService;

	@Autowired
	private NoProvisionService noProvisionService;

	@Autowired
	private BestDownloadAppService bestDownloadAppService;

	@Autowired
	private BestDownloadMMService bestDownloadMMService;

	@Autowired
	private SearchAppNameService searchAppNameService;

	@Autowired
	private SearchSellerNameService searchSellerNameService;

	@Autowired
	private SalesAppService salesAppService;

	@Autowired
	private SellerAppListService sellerAppListService;

	@Autowired
	private SellerAppDetailService sellerAppDetailService;

	@Autowired
	private SellerIdAppListService sellerIdAppListService;

	@Autowired
	private AppDetailByPkgNmService appDetailByPkgNmService;

	@Autowired
	private AppDetailByProdIdService appDetailByProdIdService;

	@Autowired
	private SupportGameCenterService supportGameCenterService;

	@InitBinder("bestDownloadAppSacReq")
	public void initBestDownloadAppSacReqBinder(WebDataBinder dataBinder) {
		dataBinder.setValidator(new BestDownloadAppSacReqValidator());
	}

	@InitBinder("bestDownloadMMSacReq")
	public void initBestDownloadMMSacReqBinder(WebDataBinder dataBinder) {
		dataBinder.setValidator(new BestDownloadMMSacReqValidator());
	}

	/**
	 * <pre>
	 * App 목록 요청 - TBD.
	 * </pre>
	 * 
	 * @param req
	 *            req
	 * @param header
	 *            header
	 * @return DeviceProfileRes
	 */
	@RequestMapping(value = "/sellerApp/list/v1", method = RequestMethod.POST)
	@ResponseBody
	public SellerAppListRes searchSellerAppList(@RequestBody @Validated SellerAppListReq req, SacRequestHeader header) {
		if ("N".equals(req.getAdmin())) {
			if (StringUtils.isEmpty(req.getSellerId())) {
				throw new StorePlatformException("SAC_DSP_0002", "sellerId", req.getSellerId());
			}
			if (StringUtils.isEmpty(req.getSellerKey())) {
				throw new StorePlatformException("SAC_DSP_0002", "sellerKey", req.getSellerKey());
			}
		}
		return this.sellerAppListService.searchSellerAppList(req, header);
	}

	/**
	 * <pre>
	 * App 상세 정보 요청.
	 * </pre>
	 * 
	 * @param req
	 *            req
	 * @param header
	 *            header
	 * @return DeviceProductProvisioningRes
	 */
	@RequestMapping(value = "/sellerApp/detail/v1", method = RequestMethod.POST)
	@ResponseBody
	public SellerAppDetailRes getSellerAppDetail(@Validated @RequestBody SellerAppDetailReq req, SacRequestHeader header) {
		if ("N".equals(req.getAdmin())) {
			if (StringUtils.isEmpty(req.getSellerId())) {
				throw new StorePlatformException("SAC_DSP_0002", "sellerId", req.getSellerId());
			}
			if (StringUtils.isEmpty(req.getSellerKey())) {
				throw new StorePlatformException("SAC_DSP_0002", "sellerKey", req.getSellerKey());
			}
		}
		return this.sellerAppDetailService.getSellerAppDetail(req, header);
	}

	/**
	 * <pre>
	 * 개발 App 목록 요청(회원 ID 기반).
	 * </pre>
	 * 
	 * @param req
	 *            req
	 * @param header
	 *            header
	 * @return DeviceProfileRes
	 */
	@RequestMapping(value = "/sellerIdApp/list/v1", method = RequestMethod.POST)
	@ResponseBody
	public SellerIdAppListRes searchSellerIdAppList(@Validated @RequestBody SellerIdAppListReq req,
			SacRequestHeader header) {
		return this.sellerIdAppListService.searchSellerIdAppList(req, header);
	}

	/**
	 * 
	 * <pre>
	 * Download Best 리스트 조회.
	 * </pre>
	 * 
	 * @param requestheader
	 *            requestheader
	 * @param downloadBestSacReq
	 *            downloadBestSacReq
	 * @return DownloadBestSacRes
	 */
	@RequestMapping(value = "/bestDownload/list/v1", method = RequestMethod.POST)
	@ResponseBody
	public DownloadBestSacRes downloadBest(SacRequestHeader requestheader,
			@RequestBody @Validated DownloadBestSacReq downloadBestSacReq) {
		return this.downloadBestService.searchDownloadBestList(requestheader, downloadBestSacReq);
	}

	/**
	 * 
	 * <pre>
	 * 신규 앱 리스트 조회.
	 * </pre>
	 * 
	 * @param requestheader
	 *            requestheader
	 * @param newAppRecommendSacReq
	 *            newAppRecommendSacReq
	 * @return NewAppRecommendSacRes
	 */
	@RequestMapping(value = "/newAppRecommend/list/v1", method = RequestMethod.POST)
	@ResponseBody
	public NewAppRecommendSacRes newAppRecommend(SacRequestHeader requestheader,
			@RequestBody @Validated NewAppRecommendSacReq newAppRecommendSacReq) {
		return this.newAppRecommendService.searchNewAppRecommendList(requestheader, newAppRecommendSacReq);
	}

	/**
	 * 
	 * <pre>
	 * 유/무료 다운로드 BEST.
	 * </pre>
	 * 
	 * @param requestheader
	 *            requestheader
	 * @param bestDownloadAppSacReq
	 *            bestDownloadAppSacReq
	 * @return BestDownloadAppSacRes
	 */
	@RequestMapping(value = "/bestDownloadApp/list/v1", method = RequestMethod.GET)
	@ResponseBody
	public BestDownloadAppSacRes bestDownloadApp(SacRequestHeader requestheader,
			@Validated BestDownloadAppSacReq bestDownloadAppSacReq) {
		return this.bestDownloadAppService.searchBestDownloadAppList(requestheader, bestDownloadAppSacReq);
	}

	/**
	 * 
	 * <pre>
	 * 멀티미디어 상품 목록 조회.
	 * </pre>
	 * 
	 * @param requestheader
	 *            requestheader
	 * @param bestDownloadMMSacReq
	 *            bestDownloadMMSacReq
	 * @return BestDownloadMMSacRes
	 */
	@RequestMapping(value = "/bestDownloadMM/list/v1", method = RequestMethod.GET)
	@ResponseBody
	public BestDownloadMMSacRes bestDownloadMM(SacRequestHeader requestheader,
			@Validated BestDownloadMMSacReq bestDownloadMMSacReq) {
		return this.bestDownloadMMService.searchBestDownloadMMList(requestheader, bestDownloadMMSacReq);
	}

	/**
	 * 
	 * <pre>
	 * 상품 검색 요청 (상품명).
	 * </pre>
	 * 
	 * @param requestheader
	 *            requestheader
	 * @param SearchAppNameSacReq
	 *            searchAppNameSacReq
	 * @return SearchAppNameSacRes
	 */
	@RequestMapping(value = "/searchAppName/list/v1", method = RequestMethod.GET)
	@ResponseBody
	public SearchAppNameSacRes searchAppNameApp(SacRequestHeader requestheader,
			@Validated SearchAppNameSacReq searchAppNameSacReq) {
		return this.searchAppNameService.searchAppNameList(requestheader, searchAppNameSacReq);
	}

	/**
	 * 
	 * <pre>
	 * 상품 검색 요청 (판매자명).
	 * </pre>
	 * 
	 * @param requestheader
	 *            requestheader
	 * @param SearchAppNameSacReq
	 *            searchAppNameSacReq
	 * @return SearchAppNameSacRes
	 */
	@RequestMapping(value = "/searchSellerName/list/v1", method = RequestMethod.GET)
	@ResponseBody
	public SearchSellerNameSacRes searchSellerNameApp(SacRequestHeader requestheader,
			@Validated SearchSellerNameSacReq searchSellerNameSacReq) {
		return this.searchSellerNameService.searchSellerNameList(requestheader, searchSellerNameSacReq);
	}

	/**
	 * 
	 * <pre>
	 * 상품 검색 요청(BY 상품명) - No Provisioning
	 * </pre>
	 * 
	 * @param noProvisionSacReq
	 *            noProvisionSacReq
	 * @param requestheader
	 *            requestheader
	 * @return NoProvisionSacRes
	 */
	@RequestMapping(value = "/noProvision/list/v1", method = RequestMethod.GET)
	@ResponseBody
	public NoProvisionSacRes noProvision(@Validated NoProvisionSacReq noProvisionSacReq, SacRequestHeader requestheader) {
		return this.noProvisionService.searchProductByNameNoProvisioningList(noProvisionSacReq, requestheader);
	}

	/**
	 * <pre>
	 * PKG Name 기반 상품 리스트 조회.
	 * </pre>
	 * 
	 * @param header
	 *            header
	 * @param salesAppReq
	 *            salesAppReq
	 * @return SalesAppSacRes
	 */
	@RequestMapping(value = "/salesApp/list/v1", method = RequestMethod.GET)
	@ResponseBody
	public SalesAppSacRes searchSalesAppList(SacRequestHeader header, @Validated SalesAppSacReq salesAppReq) {
		this.log.debug("----------------------------------------------------------------");
		this.log.debug("[searchSalesAppList] SacRequestHeader\n{}", header.toString());
		this.log.debug("[searchSalesAppList] SalesAppSacReq\n{}", salesAppReq.toString());
		this.log.debug("----------------------------------------------------------------");

		return this.salesAppService.searchSalesAppList(header, salesAppReq);
	}

	/**
	 * <pre>
	 * PKG Name 기반 상품 정보 조회.
	 * </pre>
	 * 
	 * @param header
	 * @param salesAppReq
	 * @return
	 */
	@RequestMapping(value = "/salesApp/detail/v1", method = RequestMethod.GET)
	@ResponseBody
	public SalesAppInfoSacRes getSalesAppInfo(SacRequestHeader header, @Validated SalesAppInfoSacReq salesAppInfoReq) {
		this.log.debug("----------------------------------------------------------------");
		this.log.debug("[searchSalesAppInfo] SacRequestHeader\n{}", header.toString());
		this.log.debug("[searchSalesAppInfo] SalesAppInfoSacReq\n{}", salesAppInfoReq.toString());
		this.log.debug("----------------------------------------------------------------");

		return this.salesAppService.getSalesAppInfo(header, salesAppInfoReq);
	}

	/**
	 * 
	 * <pre>
	 * 상품 상세 정보 요청(Product Id)
	 * </pre>
	 * 
	 * @param appDetailByProductIdSacReq
	 *            appDetailByProductIdSacReq
	 * @param SacRequestheader
	 *            requestheader
	 * @return AppDetailByProductIdSacRes
	 */
	@RequestMapping(value = "/searchProductId/detail/v1", method = RequestMethod.GET)
	@ResponseBody
	public AppDetailByProductIdSacRes appDetailByProductId(
			@Validated AppDetailByProductIdSacReq appDetailByProductIdSacReq, SacRequestHeader requestheader) {
		return this.appDetailByProdIdService.searchProductByProductId(appDetailByProductIdSacReq, requestheader);
	}

	/**
	 * 
	 * <pre>
	 * 상품 상세 정보 요청(Package Name)
	 * </pre>
	 * 
	 * @param appDetailByPackageNameSacReq
	 *            appDetailByPackageNameSacReq
	 * @param SacRequestheader
	 *            requestheader
	 * @return AppDetailByPackageNameSacRes
	 */
	@RequestMapping(value = "/searchPackageName/detail/v1", method = RequestMethod.GET)
	@ResponseBody
	public AppDetailByPackageNameSacRes appDetailByPackageName(
			@Validated AppDetailByPackageNameSacReq appDetailByPackageNameSacReq, SacRequestHeader requestheader) {
		return this.appDetailByPkgNmService.searchProductByPackageName(appDetailByPackageNameSacReq, requestheader);
	}

	/**
	 * 
	 * <pre>
	 * 게임센터 상품 지원 여부 조회
	 * </pre>
	 * 
	 * @param supportGameCenterSacReq
	 *            supportGameCenterSacReq
	 * @param SacRequestheader
	 *            requestheader
	 * @return SupportGameCenterSacRes
	 */
	@RequestMapping(value = "/supportGameCenter/list/v1", method = RequestMethod.POST)
	@ResponseBody
	public SupportGameCenterSacRes getSupportGameCenter(
			@RequestBody @Validated SupportGameCenterSacReq supportGameCenterSacReq, SacRequestHeader requestheader) {
		return this.supportGameCenterService.searchSupportGameCenterByAid(supportGameCenterSacReq, requestheader);
	}

}
