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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.skplanet.storeplatform.sac.client.display.vo.openapi.BestDownloadAppSacReq;
import com.skplanet.storeplatform.sac.client.display.vo.openapi.BestDownloadAppSacRes;
import com.skplanet.storeplatform.sac.client.display.vo.openapi.DownloadBestSacReq;
import com.skplanet.storeplatform.sac.client.display.vo.openapi.DownloadBestSacRes;
import com.skplanet.storeplatform.sac.client.display.vo.openapi.NewAppRecommendSacReq;
import com.skplanet.storeplatform.sac.client.display.vo.openapi.NewAppRecommendSacRes;
import com.skplanet.storeplatform.sac.client.display.vo.openapi.NoProvisionSacReq;
import com.skplanet.storeplatform.sac.client.display.vo.openapi.NoProvisionSacRes;
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
import com.skplanet.storeplatform.sac.common.header.vo.SacRequestHeader;
import com.skplanet.storeplatform.sac.display.openapi.service.BestDownloadAppService;
import com.skplanet.storeplatform.sac.display.openapi.service.DownloadBestService;
import com.skplanet.storeplatform.sac.display.openapi.service.NewAppRecommendService;
import com.skplanet.storeplatform.sac.display.openapi.service.NoProvisionService;
import com.skplanet.storeplatform.sac.display.openapi.service.SalesAppService;
import com.skplanet.storeplatform.sac.display.openapi.service.SearchAppNameService;
import com.skplanet.storeplatform.sac.display.openapi.service.SearchSellerNameService;
import com.skplanet.storeplatform.sac.display.openapi.service.SellerAppDetailService;
import com.skplanet.storeplatform.sac.display.openapi.service.SellerAppListService;
import com.skplanet.storeplatform.sac.display.openapi.service.SellerIdAppListService;

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
	private DownloadBestService downloadBestService;

	@Autowired
	private NewAppRecommendService newAppRecommendService;

	@Autowired
	private NoProvisionService noProvisionService;

	@Autowired
	private BestDownloadAppService bestDownloadAppService;

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
	public SellerAppListRes searchSellerAppList(@Validated @RequestBody SellerAppListReq req, SacRequestHeader header) {
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
	 * PKG Name 기반 상품 정보 조회.
	 * </pre>
	 * 
	 * @param requestheader
	 *            requestheader
	 * @param salesAppSacReq
	 *            salesAppSacReq
	 * @return SalesAppSacRes
	 */
	@RequestMapping(value = "/salesApp/list/v1", method = RequestMethod.GET)
	@ResponseBody
	public SalesAppSacRes searchSalesAppList(SacRequestHeader requestheader, @Validated SalesAppSacReq salesAppSacReq) {
		this.log.debug("----------------------------------------------------------------");
		this.log.debug("[searchSalesAppList] requestheader\n", requestheader);
		this.log.debug("[searchSalesAppList] salesAppSacReq\n", salesAppSacReq);
		this.log.debug("----------------------------------------------------------------");

		return this.salesAppService.searchSalesAppList(requestheader, salesAppSacReq);
	}
}
