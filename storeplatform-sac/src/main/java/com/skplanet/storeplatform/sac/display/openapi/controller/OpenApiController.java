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

import com.skplanet.storeplatform.framework.core.exception.StorePlatformException;
import com.skplanet.storeplatform.sac.client.display.vo.openapi.*;
import com.skplanet.storeplatform.sac.common.header.vo.SacRequestHeader;
import com.skplanet.storeplatform.sac.display.openapi.service.*;
import com.skplanet.storeplatform.sac.display.openapi.vo.MusicDetail;
import com.skplanet.storeplatform.sac.display.openapi.vo.MusicDetailParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Validator;
import org.springframework.validation.annotation.Validated;
import org.springframework.validation.beanvalidation.SpringValidatorAdapter;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Validation;
import javax.validation.ValidatorFactory;

/**
 * Open API 관련 Controller
 * 
 * Updated on : 2014. 1. 27. Updated by : 오승민, 인크로스
 */
@Controller
@RequestMapping("/display/openapi")
public class OpenApiController {
	private static final Logger log = LoggerFactory.getLogger(OpenApiController.class);

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

    @Autowired
    private MusicService musicService;

	@InitBinder("bestDownloadAppSacReq")
	public void initBestDownloadAppSacReqBinder(WebDataBinder dataBinder) {
		dataBinder.setValidator(new BestDownloadAppSacReqValidator());
	}

	@InitBinder("bestDownloadMMSacReq")
	public void initBestDownloadMMSacReqBinder(WebDataBinder dataBinder) {
		dataBinder.setValidator(new BestDownloadMMSacReqValidator());
	}

	@InitBinder("sellerAppListReq")
	public void sellerAppListReqBinder(WebDataBinder dataBinder) {
		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
		Validator springValidatorAdapter = new SpringValidatorAdapter(factory.getValidator());
		dataBinder.setValidator(new SellerAppListReqValidator(springValidatorAdapter));
	}

	@InitBinder("sellerAppDetailReq")
	public void sellerAppDetailReqBinder(WebDataBinder dataBinder) {
		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
		Validator springValidatorAdapter = new SpringValidatorAdapter(factory.getValidator());
		dataBinder.setValidator(new SellerAppDetailReqValidator(springValidatorAdapter));
	}

	/**
	 * <pre>
	 * App 목록 요청 - TBD.
	 * </pre>
	 * 
	 * @param sellerAppListReq
	 *            req
	 * @param header
	 *            header
	 * @return DeviceProfileRes
	 */
	@RequestMapping(value = "/sellerApp/list/v1", method = RequestMethod.POST)
	@ResponseBody
	public SellerAppListRes searchSellerAppList(@RequestBody @Validated SellerAppListReq sellerAppListReq,
			SacRequestHeader header) {
		return this.sellerAppListService.searchSellerAppList(sellerAppListReq, header);
	}

	/**
	 * <pre>
	 * App 상세 정보 요청.
	 * </pre>
	 * 
	 * @param sellerAppDetailReq
	 *            req
	 * @param header
	 *            header
	 * @return DeviceProductProvisioningRes
	 */
	@RequestMapping(value = "/sellerApp/detail/v1", method = RequestMethod.POST)
	@ResponseBody
	public SellerAppDetailRes getSellerAppDetail(@Validated @RequestBody SellerAppDetailReq sellerAppDetailReq,
			SacRequestHeader header) {
		return this.sellerAppDetailService.getSellerAppDetail(sellerAppDetailReq, header);
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
	 * @param searchAppNameSacReq
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
	 * @param searchSellerNameSacReq
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
	 * @param salesAppInfoReq
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
	 * @param requestheader
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
	 * @param requestheader
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
	 * @param requestheader
	 *            requestheader
	 * @return SupportGameCenterSacRes
	 */
	@RequestMapping(value = "/supportGameCenter/list/v1", method = RequestMethod.POST)
	@ResponseBody
	public SupportGameCenterSacRes getSupportGameCenter(
			@RequestBody @Validated SupportGameCenterSacReq supportGameCenterSacReq, SacRequestHeader requestheader) {
		return this.supportGameCenterService.searchSupportGameCenterByAid(supportGameCenterSacReq, requestheader);
	}

    /**
     *
     * <pre>
     * 티스토어 음악 정보 조회
     * </pre>
     *
     * @param sacRequestHeader
     *            sacRequestHeader
     * @param musicDetailSacReq
     *            musicDetailSacReq
     * @return MusicDetailSacRes
     */
    @RequestMapping(value = "/music/detail/v1", method = RequestMethod.GET)
    @ResponseBody
    public MusicDetailSacRes getMusicDetail(
            SacRequestHeader sacRequestHeader, @Validated MusicDetailSacReq musicDetailSacReq) {

        MusicDetailParam musicDetailParam = new MusicDetailParam(sacRequestHeader, musicDetailSacReq);
        MusicDetail musicDetail = musicService.getMusicDetail(musicDetailParam);
        MusicDetailSacRes musicDetailSacRes = musicDetail.newMusicDetailSacRes();
        if(musicDetailSacRes == null)
            throw new StorePlatformException("SAC_DSP_0009");

        return musicDetailSacRes;
    }
}
