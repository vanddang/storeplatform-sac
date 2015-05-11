package com.skplanet.storeplatform.sac.display.shopping.controller;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.skplanet.storeplatform.sac.client.internal.display.localsci.sci.FreePassInfoSCI;
import com.skplanet.storeplatform.sac.client.internal.display.localsci.sci.PaymentInfoSCI;
import com.skplanet.storeplatform.sac.client.internal.display.localsci.sci.ProductInfoSCI;
import com.skplanet.storeplatform.sac.client.internal.display.localsci.sci.UpdateSpecialPriceSoldOutSCI;
import com.skplanet.storeplatform.sac.client.internal.display.localsci.sci.UpdateSpecialPurchaseCountSCI;
import com.skplanet.storeplatform.sac.client.internal.display.localsci.vo.EpisodeInfoReq;
import com.skplanet.storeplatform.sac.client.internal.display.localsci.vo.EpisodeInfoSacRes;
import com.skplanet.storeplatform.sac.client.internal.display.localsci.vo.FreePassBasicInfoSacReq;
import com.skplanet.storeplatform.sac.client.internal.display.localsci.vo.FreePassBasicInfoSacRes;
import com.skplanet.storeplatform.sac.client.internal.display.localsci.vo.FreePassDrmInfo;
import com.skplanet.storeplatform.sac.client.internal.display.localsci.vo.FreePassInfo;
import com.skplanet.storeplatform.sac.client.internal.display.localsci.vo.FreePassInfoSacReq;
import com.skplanet.storeplatform.sac.client.internal.display.localsci.vo.PaymentInfoSacReq;
import com.skplanet.storeplatform.sac.client.internal.display.localsci.vo.PaymentInfoSacRes;
import com.skplanet.storeplatform.sac.client.internal.display.localsci.vo.ProductInfoSacReq;
import com.skplanet.storeplatform.sac.client.internal.display.localsci.vo.ProductInfoSacRes;
import com.skplanet.storeplatform.sac.client.internal.display.localsci.vo.SpecialPriceSoldOutReq;
import com.skplanet.storeplatform.sac.client.internal.display.localsci.vo.SpecialPurchaseCountSacReq;
import com.skplanet.storeplatform.sac.client.internal.display.localsci.vo.SpecialPurchaseCountSacRes;
import com.skplanet.storeplatform.sac.common.header.vo.SacRequestHeader;

@Controller
@RequestMapping("/display/localsci2")
public class LocalSCIController {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private FreePassInfoSCI freePassInfoSCI;

	@Autowired
	private ProductInfoSCI productInfoSCI;

	@Autowired
	private PaymentInfoSCI paymentInfoSCI;

	@Autowired
	private UpdateSpecialPriceSoldOutSCI updateSpecialPriceSoldOutSCI;

	@Autowired
	private UpdateSpecialPurchaseCountSCI updateSpecialPurchaseCountSCI;

	/* 2.3. 에피소드ID을 이용하여 상품 정보 조회 */
	@RequestMapping(value = "/getProductList/v1", method = RequestMethod.GET)
	@ResponseBody
	public ProductInfoSacRes getProductList(SacRequestHeader header, ProductInfoSacReq req) {

		List<String> prodList = new ArrayList<String>();

		String[] arrProdId = req.getDeviceModelNo().split(",");
		for (int kk = 0; kk < arrProdId.length; kk++) {
			prodList.add(arrProdId[kk]);
		}
		req.setDeviceModelNo("SHV-E210S");
		req.setLang("ko");
		req.setTenantId("S01");
		req.setList(prodList);

		return this.productInfoSCI.getProductList(req);
	}

	/* 2.4. 결제 시 상품ID로 상품 정보 조회 */
	@RequestMapping(value = "/searchPaymentInfo/v1", method = RequestMethod.GET)
	@ResponseBody
	public PaymentInfoSacRes searchPaymentInfo(SacRequestHeader header, PaymentInfoSacReq req) {

		List<String> prodIdList = new ArrayList<String>();

		String[] arrProdId = req.getProdId().split(",");
		for (int kk = 0; kk < arrProdId.length; kk++) {
			prodIdList.add(arrProdId[kk]);
		}
		req.setProdIdList(prodIdList);
		req.setTenantId("S01");
		req.setLangCd("ko");
		req.setDeviceModelCd("SM-N900S");

		return this.paymentInfoSCI.searchPaymentInfo(req);
	}

	/* 2.5. 정액제 상품 DRM 정보 조회(V1) */
	@RequestMapping(value = "/searchFreepassDrmInfo/v1", method = RequestMethod.GET)
	@ResponseBody
	public FreePassInfo searchFreepassDrmInfo(SacRequestHeader header, FreePassInfoSacReq req) {

		req.setTenantId("S01");
		req.setLangCd("ko");
		req.setProdId(req.getProdId()); // 정액제, 자유이용권 ID
		req.setEpisodeProdId(req.getEpisodeProdId());

		return this.freePassInfoSCI.searchFreePassDrmInfo(req);
	}

	/* 2.5. 정액제 상품 DRM 정보 조회(V2) */
	@RequestMapping(value = "/searchFreepassDrmInfo/v2", method = RequestMethod.GET)
	@ResponseBody
	public FreePassDrmInfo searchFreepassDrmInfoV2(SacRequestHeader header, FreePassInfoSacReq req) {

		req.setTenantId("S01");
		req.setLangCd("ko");
		req.setProdId(req.getProdId()); // 정액제, 자유이용권 ID
		req.setEpisodeProdId(req.getEpisodeProdId());

		return this.freePassInfoSCI.searchFreePassDrmInfoV2(req);
	}

	/* 2.9. 정액권의 에피소드 상품 목록 조회 */
	@RequestMapping(value = "/searchEpisodeList/v1", method = RequestMethod.GET)
	@ResponseBody
	public EpisodeInfoSacRes searchEpisodeList(SacRequestHeader header, EpisodeInfoReq req) {

		String prodId = req.getProdId();
		String cmpxProdClsfCd = req.getCmpxProdClsfCd();

		this.logger.info("##################### 정액제 ID : " + prodId);
		this.logger.info("##################### 복합 상품 구분 코드 : " + cmpxProdClsfCd);

		req.setTenantId("S01");
		req.setLangCd("ko");
		req.setDeviceModelCd("LG-F320S");

		req.setProdId(prodId); // 정액제, 자유이용권 ID
		req.setCmpxProdClsfCd(cmpxProdClsfCd); // 복합 상품 구분 코드

		return this.freePassInfoSCI.searchEpisodeList(req);
	}

	/* 2.10. 정액권 기본 정보 조회 */
	@RequestMapping(value = "/searchFreepassBasicList/v1", method = RequestMethod.GET)
	@ResponseBody
	public FreePassBasicInfoSacRes searchFreepassBasicList(SacRequestHeader header, FreePassBasicInfoSacReq req) {

		List<String> prodList = new ArrayList<String>();

		String[] arrProdId = req.getTenantId().split(",");
		for (int kk = 0; kk < arrProdId.length; kk++) {
			prodList.add(arrProdId[kk]);
		}

		req.setTenantId("S01");
		req.setLang("ko");
		req.setList(prodList);

		return this.freePassInfoSCI.searchFreepassBasicList(req);
	}

	/* 2.11. 쇼핑 특가 상품에 대한 품절 여부(Sold Out) 등록 */
	@RequestMapping(value = "/updateSpecialPriceSoldOut/v1", method = RequestMethod.GET)
	@ResponseBody
	public void updateSpecialPriceSoldOut(SacRequestHeader header) {

		SpecialPriceSoldOutReq req = new SpecialPriceSoldOutReq();
		req.setTenantId("S01");
		req.setLang("ko");
		req.setProductId(req.getProductId());

		this.updateSpecialPriceSoldOutSCI.updateSpecialPriceSoldOut(req);
	}

	/* 2.4.3. 쇼핑 특가 상품 구매수 업데이트 */
	@RequestMapping(value = "/updateSpecialPurchaseCount/v1", method = RequestMethod.GET)
	@ResponseBody
	public SpecialPurchaseCountSacRes updateSpecialPurchaseCount(SacRequestHeader header, SpecialPurchaseCountSacReq req) {

		req.setTenantId("S01");
		return this.updateSpecialPurchaseCountSCI.updateSpecialPurchaseCount(req);
	}

}
