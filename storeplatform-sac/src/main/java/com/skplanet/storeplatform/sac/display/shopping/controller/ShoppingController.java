package com.skplanet.storeplatform.sac.display.shopping.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.skplanet.storeplatform.sac.client.display.vo.shopping.ShoppingBrandAnotherReq;
import com.skplanet.storeplatform.sac.client.display.vo.shopping.ShoppingBrandReq;
import com.skplanet.storeplatform.sac.client.display.vo.shopping.ShoppingBrandRes;
import com.skplanet.storeplatform.sac.client.display.vo.shopping.ShoppingCategoryAnotherReq;
import com.skplanet.storeplatform.sac.client.display.vo.shopping.ShoppingCategoryBrandRes;
import com.skplanet.storeplatform.sac.client.display.vo.shopping.ShoppingDetailReq;
import com.skplanet.storeplatform.sac.client.display.vo.shopping.ShoppingDetailRes;
import com.skplanet.storeplatform.sac.client.display.vo.shopping.ShoppingFeatureReq;
import com.skplanet.storeplatform.sac.client.display.vo.shopping.ShoppingPlanReq;
import com.skplanet.storeplatform.sac.client.display.vo.shopping.ShoppingReq;
import com.skplanet.storeplatform.sac.client.display.vo.shopping.ShoppingRes;
import com.skplanet.storeplatform.sac.client.display.vo.shopping.ShoppingSpcialSaleRes;
import com.skplanet.storeplatform.sac.client.display.vo.shopping.ShoppingSubReq;
import com.skplanet.storeplatform.sac.client.display.vo.shopping.ShoppingThemeReq;
import com.skplanet.storeplatform.sac.client.display.vo.shopping.ShoppingThemeRes;
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
	 * [I03000162] 2.7.16. 브랜드샵 – 상세조회
	 * </pre>
	 * 
	 * @param header
	 *            header
	 * @param req
	 *            req
	 * @return ShoppingRes
	 */
	@RequestMapping(value = "/brandshop/detail/v1", method = RequestMethod.GET)
	@ResponseBody
	public ShoppingRes getBrandshopDetail(SacRequestHeader header, ShoppingReq req) {
		this.logger.debug("----------------------------------------------------------------");
		this.logger.debug("getBrandshopDetail Controller started!!");
		this.logger.debug("----------------------------------------------------------------");
		return this.shoppingService.getBrandshopDetail(header, req);

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

	/**
	 * <pre>
	 * [I03000149] 2.7.6. 특가 상품 조회 (V2버전).
	 * </pre>
	 * 
	 * @param header
	 *            header
	 * @param req
	 *            req
	 * @return ShoppingRes
	 */
	@RequestMapping(value = "/specialPriceProduct/v2", method = RequestMethod.POST)
	@ResponseBody
	public ShoppingRes getSpecialPriceProductListV2(SacRequestHeader header, @RequestBody @Validated ShoppingReq req) {
		this.logger.debug("----------------------------------------------------------------");
		this.logger.debug("getSubProductListV2 Controller started!!");
		this.logger.debug("----------------------------------------------------------------");
		return this.shoppingService.getSpecialPriceProductListV2(header, req);

	}

	/**
	 * <pre>
	 * [I03000150] 2.7.7.3. 기획전 상품 조회 (V2버전).
	 * </pre>
	 * 
	 * @param header
	 *            header
	 * @param req
	 *            req
	 * @return ShoppingRes
	 */
	@RequestMapping(value = "/specialSales/specialSalesList/v2", method = RequestMethod.GET)
	@ResponseBody
	public ShoppingSpcialSaleRes getSpecialSalesListV2(SacRequestHeader header, ShoppingReq req) {
		this.logger.debug("----------------------------------------------------------------");
		this.logger.debug("getSpecialSalesListV2 Controller started!!");
		this.logger.debug("----------------------------------------------------------------");
		return this.shoppingService.getSpecialSalesListV2(header, req);

	}

	/**
	 * <pre>
	 * [I03000151] 2.7.7.4. 특정 기획전에 대한 상품 리스트 (V2버전).
	 * </pre>
	 * 
	 * @param header
	 *            header
	 * @param req
	 *            req
	 * @return ShoppingRes
	 */
	@RequestMapping(value = "/specialSales/productList/v2", method = RequestMethod.GET)
	@ResponseBody
	public ShoppingThemeRes getSpecialSalesProductListV2(SacRequestHeader header, @Validated ShoppingPlanReq req) {
		this.logger.debug("----------------------------------------------------------------");
		this.logger.debug("getSpecialSalesProductListV2 Controller started!!");
		this.logger.debug("----------------------------------------------------------------");
		return this.shoppingService.getSpecialSalesProductListV2(header, req);

	}

	/**
	 * <pre>
	 * [I03000152] 2.7.8.3. 브랜드샵 – 메인 (V2버전).
	 * </pre>
	 * 
	 * @param header
	 *            header
	 * @param req
	 *            req
	 * @return ShoppingCategoryBrandRes
	 */
	@RequestMapping(value = "/brandshop/mainList/v2", method = RequestMethod.GET)
	@ResponseBody
	public ShoppingCategoryBrandRes getBrandshopMainListV2(SacRequestHeader header, ShoppingReq req) {
		this.logger.debug("----------------------------------------------------------------");
		this.logger.debug("getBrandshopMainListV2 Controller started!!");
		this.logger.debug("----------------------------------------------------------------");
		return this.shoppingService.getBrandshopMainListV2(header, req);

	}
	
	/**
	 * <pre>
	 * [I03000152] 2.7.8.5. 브랜드샵 – 카테고리별 브랜드샵 조회.
	 * </pre>
	 * 
	 * @param header
	 *            header
	 * @param req
	 *            req
	 * @return ShoppingCategoryBrandRes
	 */
	@RequestMapping(value = "/brandshop/categoryList/v1", method = RequestMethod.GET)
	@ResponseBody
	public ShoppingCategoryBrandRes getBrandshopCategoryList(SacRequestHeader header, ShoppingReq req) {
		this.logger.debug("----------------------------------------------------------------");
		this.logger.debug("getBrandshopCategoryList Controller started!!");
		this.logger.debug("----------------------------------------------------------------");
		return this.shoppingService.getBrandshopCategoryList(header, req);

	}	

	/**
	 * <pre>
	 * [I03000153] 2.7.8.4. 특정 브랜드샵 상품 리스트 (V2버전).
	 * </pre>
	 * 
	 * @param header
	 *            header
	 * @param req
	 *            req
	 * @return ShoppingBrandRes
	 */
	@RequestMapping(value = "/brandshop/productList/v2", method = RequestMethod.GET)
	@ResponseBody
	public ShoppingBrandRes getBrandshopProductListV2(SacRequestHeader header, @Validated ShoppingBrandReq req) {
		this.logger.debug("----------------------------------------------------------------");
		this.logger.debug("getBrandshopProductListV2 Controller started!!");
		this.logger.debug("----------------------------------------------------------------");
		return this.shoppingService.getBrandshopProductListV2(header, req);

	}

	/**
	 * <pre>
	 * [I03000154] 2.7.13. 특정 카테고리의 다른 상품 / GET (V2버전).
	 * </pre>
	 * 
	 * @param header
	 *            header
	 * @param req
	 *            req
	 * @return ShoppingRes
	 */
	@RequestMapping(value = "/category/anotherProductList/v2", method = RequestMethod.GET)
	@ResponseBody
	public ShoppingRes getCatagoryAnotherProductListV2(SacRequestHeader header,
			@Validated ShoppingCategoryAnotherReq req) {
		this.logger.debug("----------------------------------------------------------------");
		this.logger.debug("getCatagoryAnotherProductListV2 Controller started!!");
		this.logger.debug("----------------------------------------------------------------");
		return this.shoppingService.getCatagoryAnotherProductListV2(header, req);

	}

	/**
	 * <pre>
	 * [I03000155] 2.7.14. 특정 브랜드의 다른 상품 (V2버전).
	 * </pre>
	 * 
	 * @param header
	 *            header
	 * @param req
	 *            req
	 * @return ShoppingRes
	 */
	@RequestMapping(value = "/brandshop/anotherProductList/v2", method = RequestMethod.GET)
	@ResponseBody
	public ShoppingRes getBrandAnotherProductListV2(SacRequestHeader header, @Validated ShoppingBrandAnotherReq req) {
		this.logger.debug("----------------------------------------------------------------");
		this.logger.debug("getCatagoryAnotherProductListV2 Controller started!!");
		this.logger.debug("----------------------------------------------------------------");
		return this.shoppingService.getBrandAnotherProductListV2(header, req);

	}

	/**
	 * [I03000156] 2.7.15. 쇼핑상세 조회 (V2버전).
	 * 
	 * @param header
	 *            header
	 * @param req
	 *            req
	 * @return ShoppingRes
	 */

	@RequestMapping(value = "/shoppingDetail/v2", method = RequestMethod.POST)
	@ResponseBody
	public ShoppingDetailRes getShoppingDetailV2(SacRequestHeader header, @RequestBody @Validated ShoppingDetailReq req) {
		this.logger.debug("----------------------------------------------------------------");
		this.logger.debug("getShoppingDetailV2 Controller started!!");
		this.logger.debug("----------------------------------------------------------------");
		return this.shoppingService.getShoppingDetailV2(header, req);

	}
}
