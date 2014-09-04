package com.skplanet.storeplatform.sac.display.shopping.controller;

import com.skplanet.storeplatform.sac.client.display.vo.shopping.*;
import com.skplanet.storeplatform.sac.common.header.vo.SacRequestHeader;
import com.skplanet.storeplatform.sac.display.shopping.service.ShoppingService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

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
	 * [I03000044] 2.7.1. 쇼핑 추천/인기 상품 조회
	 * </pre>
	 * 
	 * @param header
	 *            header
	 * @param req
	 *            req
	 * @return ShoppingRes
	 */
	@RequestMapping(value = "/featureProductList/v1", method = RequestMethod.GET)
	@ResponseBody
	public ShoppingRes getFeatureProductList(SacRequestHeader header, @Validated ShoppingFeatureReq req) {
		this.logger.debug("----------------------------------------------------------------");
		this.logger.debug("searchFeatureProductList Controller started!!");
		this.logger.debug("----------------------------------------------------------------");
		return this.shoppingService.getFeatureProductList(header, req);

	}

	/**
	 * <pre>
	 * [I03000045] 2.7.2. 쇼핑 신규 상품 조회
	 * </pre>
	 * 
	 * @param header
	 *            header
	 * @param req
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
	 * [I03000046] 2.7.3. 쇼핑 세부카테고리 상품 조회
	 * </pre>
	 * 
	 * @param header
	 *            header
	 * @param req
	 *            req
	 * @return ShoppingRes
	 */
	@RequestMapping(value = "/category/subProductList/v1", method = RequestMethod.GET)
	@ResponseBody
	public ShoppingRes getSubProductList(SacRequestHeader header, @Validated ShoppingSubReq req) {
		this.logger.debug("----------------------------------------------------------------");
		this.logger.debug("getSubProductList Controller started!!");
		this.logger.debug("----------------------------------------------------------------");
		return this.shoppingService.getSubProductList(header, req);

	}

	/**
	 * <pre>
	 * [I03000047] 2.7.4. 특가 상품 조회
	 * </pre>
	 * 
	 * @param header
	 *            header
	 * @param req
	 *            req
	 * @return ShoppingRes
	 */
	@RequestMapping(value = "/specialPriceProduct/v1", method = RequestMethod.GET)
	@ResponseBody
	public ShoppingRes getSpecialPriceProductList(SacRequestHeader header, ShoppingReq req) {
		this.logger.debug("----------------------------------------------------------------");
		this.logger.debug("getSubProductList Controller started!!");
		this.logger.debug("----------------------------------------------------------------");
		return this.shoppingService.getSpecialPriceProductList(header, req);

	}

	/**
	 * <pre>
	 * [I03000048] 2.7.5.1. 기획전 상품 조회
	 * </pre>
	 * 
	 * @param header
	 *            header
	 * @param req
	 *            req
	 * @return ShoppingRes
	 */
	@RequestMapping(value = "/specialSales/specialSalesList/v1", method = RequestMethod.GET)
	@ResponseBody
	public ShoppingSpcialSaleRes getSpecialSalesList(SacRequestHeader header, ShoppingReq req) {
		this.logger.debug("----------------------------------------------------------------");
		this.logger.debug("getSpecialSalesList Controller started!!");
		this.logger.debug("----------------------------------------------------------------");
		return this.shoppingService.getSpecialSalesList(header, req);

	}

	/**
	 * <pre>
	 * [I03000049] 2.7.5.2. 특정 기획전에 대한 상품 리스트
	 * </pre>
	 * 
	 * @param header
	 *            header
	 * @param req
	 *            req
	 * @return ShoppingRes
	 */
	@RequestMapping(value = "/specialSales/productList/v1", method = RequestMethod.GET)
	@ResponseBody
	public ShoppingThemeRes getSpecialSalesProductList(SacRequestHeader header, @Validated ShoppingPlanReq req) {
		this.logger.debug("----------------------------------------------------------------");
		this.logger.debug("getSpecialSalesProductList Controller started!!");
		this.logger.debug("----------------------------------------------------------------");
		return this.shoppingService.getSpecialSalesProductList(header, req);

	}

	/**
	 * <pre>
	 * [I03000050] 2.7.6.1. 브랜드샵 – 메인
	 * </pre>
	 * 
	 * @param header
	 *            header
	 * @param req
	 *            req
	 * @return ShoppingRes
	 */
	@RequestMapping(value = "/brandshop/mainList/v1", method = RequestMethod.GET)
	@ResponseBody
	public ShoppingRes getBrandshopMainList(SacRequestHeader header, ShoppingReq req) {
		this.logger.debug("----------------------------------------------------------------");
		this.logger.debug("getBrandshopMainList Controller started!!");
		this.logger.debug("----------------------------------------------------------------");
		return this.shoppingService.getBrandshopMainList(header, req);

	}

	/**
	 * <pre>
	 * [I03000051] 2.7.6.2. 특정 브랜드샵 상품 리스트
	 * </pre>
	 * 
	 * @param header
	 *            header
	 * @param req
	 *            req
	 * @return ShoppingRes
	 */
	@RequestMapping(value = "/brandshop/productList/v1", method = RequestMethod.GET)
	@ResponseBody
	public ShoppingBrandRes getBrandshopProductList(SacRequestHeader header, @Validated ShoppingBrandReq req) {
		this.logger.debug("----------------------------------------------------------------");
		this.logger.debug("getBrandshopProductList Controller started!!");
		this.logger.debug("----------------------------------------------------------------");
		return this.shoppingService.getBrandshopProductList(header, req);

	}

	/**
	 * <pre>
	 * 쇼핑 테마 조회– GET.
	 * </pre>
	 * 
	 * @param header
	 *            header
	 * @param req
	 *            req
	 * @return ShoppingRes
	 */
	@RequestMapping(value = "/theme/themeList/v1", method = RequestMethod.GET)
	@ResponseBody
	public ShoppingRes getThemeList(SacRequestHeader header, ShoppingReq req) {
		this.logger.debug("----------------------------------------------------------------");
		this.logger.debug("getThemeList Controller started!!");
		this.logger.debug("----------------------------------------------------------------");
		return this.shoppingService.getThemeList(header, req);

	}

	/**
	 * <pre>
	 * [I03000053] 2.7.7.2. 특정 테마에 대한 상품 리스트
	 * </pre>
	 * 
	 * @param header
	 *            header
	 * @param req
	 *            req
	 * @return ShoppingRes
	 */
	@RequestMapping(value = "/theme/productList/v1", method = RequestMethod.GET)
	@ResponseBody
	public ShoppingBrandRes getThemeProductList(SacRequestHeader header, @Validated ShoppingThemeReq req) {
		this.logger.debug("----------------------------------------------------------------");
		this.logger.debug("getThemeProductList Controller started!!");
		this.logger.debug("----------------------------------------------------------------");
		return this.shoppingService.getThemeProductList(header, req);

	}

	/**
	 * <pre>
	 * [I03000054] 2.7.8. 특정 카테고리의 다른 상품 / GET
	 * </pre>
	 * 
	 * @param header
	 *            header
	 * @param req
	 *            req
	 * @return ShoppingRes
	 */
	@RequestMapping(value = "/category/anotherProductList/v1", method = RequestMethod.GET)
	@ResponseBody
	public ShoppingRes getCatagoryAnotherProductList(SacRequestHeader header, @Validated ShoppingCategoryAnotherReq req) {
		this.logger.debug("----------------------------------------------------------------");
		this.logger.debug("getCatagoryAnotherProductList Controller started!!");
		this.logger.debug("----------------------------------------------------------------");
		return this.shoppingService.getCatagoryAnotherProductList(header, req);

	}

	/**
	 * <pre>
	 * [I03000055] 2.7.9. 특정 브랜드의 다른 상품
	 * </pre>
	 * 
	 * @param header
	 *            header
	 * @param req
	 *            req
	 * @return ShoppingRes
	 */
	@RequestMapping(value = "/brandshop/anotherProductList/v1", method = RequestMethod.GET)
	@ResponseBody
	public ShoppingRes getBrandAnotherProductList(SacRequestHeader header, @Validated ShoppingBrandAnotherReq req) {
		this.logger.debug("----------------------------------------------------------------");
		this.logger.debug("getCatagoryAnotherProductList Controller started!!");
		this.logger.debug("----------------------------------------------------------------");
		return this.shoppingService.getBrandAnotherProductList(header, req);

	}

	/**
	 * [I03000056] 2.7.10. 쇼핑상세 조회
	 * 
	 * @param header
	 *            header
	 * @param req
	 *            req
	 * @return ShoppingRes
	 */

	@RequestMapping(value = "/shoppingDetail/v1", method = RequestMethod.POST)
	@ResponseBody
	public ShoppingDetailRes getShoppingDetail(SacRequestHeader header, @RequestBody @Validated ShoppingDetailReq req) {
		this.logger.debug("----------------------------------------------------------------");
		this.logger.debug("getShoppingDetail Controller started!!");
		this.logger.debug("----------------------------------------------------------------");
		return this.shoppingService.getShoppingDetail(header, req);

	}
}
