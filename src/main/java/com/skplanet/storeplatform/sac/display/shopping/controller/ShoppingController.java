package com.skplanet.storeplatform.sac.display.shopping.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.skplanet.storeplatform.sac.client.display.vo.shopping.ShoppingReq;
import com.skplanet.storeplatform.sac.client.display.vo.shopping.ShoppingRes;
import com.skplanet.storeplatform.sac.common.header.vo.SacRequestHeader;
import com.skplanet.storeplatform.sac.display.shopping.service.ShoppingService;

/**
 * 쇼핑 컨트롤러
 * 
 * Updated on : 2014-01-20 Updated by : 김형식, SK플래닛.
 */

@Controller
@RequestMapping("/display/shopping")
public class ShoppingController {
	private transient Logger logger = LoggerFactory.getLogger(ShoppingController.class);

	@Autowired
	private ShoppingService shoppingService;

	/**
	 * <pre>
	 * 쇼핑 추천/인기 상품 리스트 조회– GET.
	 * </pre>
	 * 
	 * @param ShoppingReq
	 *            req
	 * @return ShoppingRes
	 */
	@RequestMapping(value = "/featureProductList/v1", method = RequestMethod.GET)
	@ResponseBody
	public ShoppingRes getFeatureProductList(SacRequestHeader header, ShoppingReq req) {
		this.logger.debug("----------------------------------------------------------------");
		this.logger.debug("searchFeatureProductList Controller started!!");
		this.logger.debug("----------------------------------------------------------------");
		return this.shoppingService.getFeatureProductList(header, req);

	}

	/**
	 * <pre>
	 * 쇼핑 신규 상품 조회– GET.
	 * </pre>
	 * 
	 * @param ShoppingReq
	 *            req
	 * @return ShoppingRes
	 */
	@RequestMapping(value = "/category/newProductList/v1", method = RequestMethod.GET)
	@ResponseBody
	public ShoppingRes getNewProductList(SacRequestHeader header, ShoppingReq req) {
		this.logger.debug("----------------------------------------------------------------");
		this.logger.debug("getNewProductList Controller started!!");
		this.logger.debug("----------------------------------------------------------------");
		return this.shoppingService.getNewProductList(header, req);

	}

	/**
	 * <pre>
	 * 쇼핑 세부카테고리  상품 조회– GET.
	 * </pre>
	 * 
	 * @param ShoppingReq
	 *            req
	 * @return ShoppingRes
	 */
	@RequestMapping(value = "/category/subProductList/v1", method = RequestMethod.GET)
	@ResponseBody
	public ShoppingRes getSubProductList(SacRequestHeader header, ShoppingReq req) {
		this.logger.debug("----------------------------------------------------------------");
		this.logger.debug("getSubProductList Controller started!!");
		this.logger.debug("----------------------------------------------------------------");
		return this.shoppingService.getSubProductList(header, req);

	}

}
