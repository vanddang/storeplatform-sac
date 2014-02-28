/**
 * 
 */
package com.skplanet.storeplatform.sac.display.webtoon.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.skplanet.storeplatform.sac.client.display.vo.webtoon.WebtoonDetailSacReq;
import com.skplanet.storeplatform.sac.client.display.vo.webtoon.WebtoonDetailSacRes;
import com.skplanet.storeplatform.sac.common.header.vo.SacRequestHeader;
import com.skplanet.storeplatform.sac.display.webtoon.servce.WebtoonService;

/**
 * 웹툰 상품 상세 조회
 * 
 * Updated on : 2014. 2. 19. Updated by : 조준일, nTels.
 */
@Controller
@RequestMapping("/display/webtoon")
public class WebtoonController {

	@Autowired
	private WebtoonService webtoonService;

	/**
	 * <pre>
	 * 웹툰 상품 상세 조회.
	 * </pre>
	 * 
	 * @param req
	 *            UserDefine 파라미터
	 * @param header
	 *            공통헤더
	 * @return WebtoonDetailSacRes
	 */
	@RequestMapping(value = "/detail/v1", method = RequestMethod.GET)
	@ResponseBody
	public WebtoonDetailSacRes searchWebtoonDetail(@Validated WebtoonDetailSacReq req, SacRequestHeader header) {

		WebtoonDetailSacRes responseVO;
		responseVO = this.webtoonService.searchWebtoonDetail(req, header);
		return responseVO;

	}

}
