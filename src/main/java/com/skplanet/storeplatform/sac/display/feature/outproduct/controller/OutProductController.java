package com.skplanet.storeplatform.sac.display.feature.outproduct.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.skplanet.storeplatform.sac.client.display.vo.feature.outproduct.list.OutProductListSacReq;
import com.skplanet.storeplatform.sac.client.display.vo.feature.outproduct.list.OutProductListSacRes;
import com.skplanet.storeplatform.sac.common.header.vo.SacRequestHeader;
import com.skplanet.storeplatform.sac.display.feature.outproduct.service.OutProductListService;

/**
 * Class 설명.
 *
 * Updated on : 2015. 2. 9.
 * Updated by : 문동선.
 */
@Controller
@RequestMapping("/display/feature/outproduct")
public class OutProductController {

	private transient Logger logger = LoggerFactory.getLogger(OutProductController.class);

	@Autowired
	private OutProductListService listService;

	/**
	 * <pre>
	 * 타사 홈 상품 목록 조회를 위한 통합 API – GET.
	 * </pre>
	 *
	 * @param requestVO
	 *            UserDefine 파라미터
	 * @param header
	 *            공통헤더
	 * @return OutProductListSacRes 조회 결과
	 */
	@RequestMapping(value = "/list/v1", method = RequestMethod.GET)
	@ResponseBody
	public OutProductListSacRes searchOutProductList(OutProductListSacReq requestVO, SacRequestHeader header) {

		OutProductListSacRes responseVO;
		responseVO = this.listService.searchOutProductList(requestVO, header);
		return responseVO;
	}
}
