/**
 * 카네고리 메인(앱) 컨트롤러.
 */

package com.skplanet.storeplatform.sac.display.feature.recommend.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.skplanet.storeplatform.sac.client.display.vo.feature.recommend.RecommendAdminSacReq;
import com.skplanet.storeplatform.sac.client.display.vo.feature.recommend.RecommendAdminSacRes;
import com.skplanet.storeplatform.sac.client.display.vo.feature.recommend.RecommendTodaySacReq;
import com.skplanet.storeplatform.sac.client.display.vo.feature.recommend.RecommendTodaySacRes;
import com.skplanet.storeplatform.sac.client.display.vo.feature.recommend.RecommendWebtoonSacReq;
import com.skplanet.storeplatform.sac.client.display.vo.feature.recommend.RecommendWebtoonSacRes;
import com.skplanet.storeplatform.sac.common.header.vo.SacRequestHeader;
import com.skplanet.storeplatform.sac.display.feature.recommend.service.RecommendAdminService;
import com.skplanet.storeplatform.sac.display.feature.recommend.service.RecommendTodayService;
import com.skplanet.storeplatform.sac.display.feature.recommend.service.RecommendWebtoonService;

/**
 * 카네고리 메인(앱) 컨트롤러.
 * 
 * Updated on : 2014-01-24 Updated by : 서영배, GTSOFT.
 */
@Controller
@RequestMapping("/display/feature/recommend")
public class RecommendController {
	private transient Logger logger = LoggerFactory.getLogger(RecommendController.class);

	@Autowired
	private RecommendWebtoonService recommendWebtoonService;
	@Autowired
	private RecommendAdminService recommendAdminService;
	@Autowired
	private RecommendTodayService recommendTodayService;

	/**
	 * <pre>
	 * 웹툰리스트 조회 – GET.
	 * </pre>
	 * 
	 * @param req
	 *            UserDefine 파라미터
	 * @param header
	 *            공통헤더
	 * @return RecommendAdminRes 조회 결과
	 */
	@RequestMapping(value = "/webtoonList/v1", method = RequestMethod.GET)
	@ResponseBody
	public RecommendWebtoonSacRes searchWebtoonList(SacRequestHeader header, RecommendWebtoonSacReq req) {
		this.logger.debug("----------------------------------------------------------------");
		this.logger.debug("searchAdminWebtoonList Controller started!!");
		this.logger.debug("----------------------------------------------------------------");
		return this.recommendWebtoonService.searchWebtoonList(header, req);

	}

	/**
	 * <pre>
	 * 카테고리 메인(앱) 조회 – GET.
	 * </pre>
	 * 
	 * @param requestVO
	 *            UserDefine 파라미터
	 * @param header
	 *            공통헤더
	 * @return RecommendAdminRes 조회 결과
	 */
	@RequestMapping(value = "/admin/list/v1", method = RequestMethod.GET)
	@ResponseBody
	public RecommendAdminSacRes searchAdminList(RecommendAdminSacReq requestVO, SacRequestHeader header) {

		RecommendAdminSacRes responseVO;
		responseVO = this.recommendAdminService.searchAdminList(requestVO, header);
		return responseVO;
	}

	/**
	 * <pre>
	 * TODAY 상품 조회 – GET.
	 * </pre>
	 * 
	 * @param requestVO
	 *            UserDefine 파라미터
	 * @param header
	 *            공통헤더
	 * @return RecommendTodaySacRes 조회 결과
	 */
	@RequestMapping(value = "/today/list/v1", method = RequestMethod.GET)
	@ResponseBody
	public RecommendTodaySacRes searchTodayList(RecommendTodaySacReq requestVO, SacRequestHeader header) {

		RecommendTodaySacRes responseVO;
		responseVO = this.recommendTodayService.searchTodayList(requestVO, header);
		return responseVO;
	}
}
