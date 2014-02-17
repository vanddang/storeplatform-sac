package com.skplanet.storeplatform.sac.display.feature.best.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.skplanet.storeplatform.sac.client.display.vo.best.BestAppSacReq;
import com.skplanet.storeplatform.sac.client.display.vo.best.BestAppSacRes;
import com.skplanet.storeplatform.sac.client.display.vo.best.BestContentsSacReq;
import com.skplanet.storeplatform.sac.client.display.vo.best.BestContentsSacRes;
import com.skplanet.storeplatform.sac.client.display.vo.best.BestDownloadSacReq;
import com.skplanet.storeplatform.sac.client.display.vo.best.BestDownloadSacRes;
import com.skplanet.storeplatform.sac.common.header.vo.SacRequestHeader;
import com.skplanet.storeplatform.sac.display.feature.best.service.BestAppService;
import com.skplanet.storeplatform.sac.display.feature.best.service.BestContentsService;
import com.skplanet.storeplatform.sac.display.feature.best.service.BestDownloadService;

/**
 * 
 * Calss 설명(BEST 앱, BEST 컨텐츠, BEST 다운로드 관련)
 * 
 * Updated on : 2014. 1. 3. Updated by : 이석희, 인크로스
 */
@Controller
public class BestController {

	private static final Logger LOGGER = LoggerFactory.getLogger(BestController.class);

	@Autowired
	private BestAppService bestAppService;

	@Autowired
	private BestContentsService bestContentsService;

	@Autowired
	private BestDownloadService bestDownloadService;

	/**
	 * 
	 * <pre>
	 * BEST 앱 리스트 조회.
	 * </pre>
	 * 
	 * @param requestheader
	 *            requestheader
	 * @param bestAppReq
	 *            bestAppReq
	 * @return BestAppRes
	 */
	@RequestMapping(value = "/display/feature/best/app/list/v1", method = RequestMethod.GET)
	@ResponseBody
	public BestAppSacRes bestAppList(SacRequestHeader requestheader, BestAppSacReq bestAppReq) {
		return this.bestAppService.searchBestAppList(requestheader, bestAppReq);
	}

	/**
	 * 
	 * <pre>
	 * BEST 컨텐츠 리스트 조회.
	 * </pre>
	 * 
	 * @param requestheader
	 *            requestheader
	 * @param bestContentsReq
	 *            bestContentsReq
	 * @return BestContentsRes
	 */
	@RequestMapping(value = "/display/feature/best/content/list/v1", method = RequestMethod.GET)
	@ResponseBody
	public BestContentsSacRes bestContentsList(SacRequestHeader requestheader, BestContentsSacReq bestContentsReq) {
		return this.bestContentsService.searchBestContentsList(requestheader, bestContentsReq);
	}

	/**
	 * 
	 * <pre>
	 * BEST 다운로드 리스트 조회.
	 * </pre>
	 * 
	 * @param requestheader
	 *            requestheader
	 * @param BestDownloadSacReq
	 *            bestDownloadReq
	 * @return BestDownloadSacRes
	 */
	@RequestMapping(value = "/display/feature/best/download/list/v1", method = RequestMethod.GET)
	@ResponseBody
	public BestDownloadSacRes bestDownloadList(SacRequestHeader requestheader, BestDownloadSacReq bestDownloadReq) {
		return this.bestDownloadService.searchBestDownloadList(requestheader, bestDownloadReq);
	}

}
