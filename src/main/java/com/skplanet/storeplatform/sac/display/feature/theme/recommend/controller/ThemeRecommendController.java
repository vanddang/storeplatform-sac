/**
 * 테마 추천(상황별) 컨트롤러.
 */

package com.skplanet.storeplatform.sac.display.feature.theme.recommend.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.skplanet.storeplatform.sac.client.display.vo.feature.recommend.ThemeRecommendReq;
import com.skplanet.storeplatform.sac.client.display.vo.feature.recommend.ThemeRecommendRes;
import com.skplanet.storeplatform.sac.common.header.vo.SacRequestHeader;
import com.skplanet.storeplatform.sac.display.feature.theme.recommend.service.ThemeRecommendService;

/**
 * 테마 추천(상황별) 컨트롤러.
 * 
 * Updated on : 2014-02-05 Updated by : 윤주영, GTSOFT.
 */
@Controller
@RequestMapping("/display/feature/theme/recommend")
public class ThemeRecommendController {
	private final Logger log = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private ThemeRecommendService themeRecommendService;

	/**
	 * <pre>
	 * 테마 추천(상황별) 컨트롤러.
	 * </pre>
	 * 
	 * @param requestVO
	 *            UserDefine 파라미터
	 * @param header
	 *            공통헤더
	 * @return RecommendAdminRes 조회 결과
	 */
	@RequestMapping(value = "/list/v1", method = RequestMethod.POST)
	@ResponseBody
	public ThemeRecommendRes searchThemeRecommendList(@RequestBody ThemeRecommendReq requestVO, SacRequestHeader header) {

		this.log.debug("searchThemeRecommendList start !!");
		this.log.debug("request {}", requestVO);
		if ("dummy".equals(requestVO.getFilteredBy()))
			return this.themeRecommendService.searchThemeRecommendList(requestVO, header);
		else
			return this.themeRecommendService.searchThemeRecommendList(requestVO, header);
	}
}
