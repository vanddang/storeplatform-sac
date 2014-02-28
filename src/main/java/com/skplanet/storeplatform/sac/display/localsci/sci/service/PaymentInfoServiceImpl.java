package com.skplanet.storeplatform.sac.display.localsci.sci.service;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.skplanet.storeplatform.framework.core.exception.StorePlatformException;
import com.skplanet.storeplatform.framework.core.persistence.dao.CommonDAO;
import com.skplanet.storeplatform.sac.client.internal.display.localsci.vo.PaymentInfo;
import com.skplanet.storeplatform.sac.client.internal.display.localsci.vo.PaymentInfoSacReq;
import com.skplanet.storeplatform.sac.client.internal.display.localsci.vo.PaymentInfoSacRes;
import com.skplanet.storeplatform.sac.display.common.constant.DisplayConstants;
import com.skplanet.storeplatform.sac.display.shopping.service.ShoppingService;

/**
 * 
 * 
 * 결제 시 필요한 상품 메타 정보 조회 서비스 구현체.
 * 
 * Updated on : 2014. 2. 27. Updated by : 홍지호, 엔텔스
 */
@Service
public class PaymentInfoServiceImpl implements PaymentInfoService {
	private final Logger log = LoggerFactory.getLogger(this.getClass());

	@Autowired
	@Qualifier("sac")
	private CommonDAO commonDAO;

	@Autowired
	ShoppingService shoppingService;

	/**
	 * <pre>
	 * 결제 시 필요한 상품 메타 정보 조회.
	 * </pre>
	 * 
	 * @param req
	 *            파라미터
	 * @return PaymentInfoSacRes 상품 메타 정보 리스트
	 */
	@Override
	public PaymentInfoSacRes searchPaymentInfo(PaymentInfoSacReq req) {
		PaymentInfoSacRes res = new PaymentInfoSacRes();
		List<PaymentInfo> paymentInfoList = new ArrayList<PaymentInfo>();
		List<String> prodIdList = req.getProdIdList();

		this.log.debug("##### prodIdList size : {}", prodIdList.size());

		if (prodIdList.size() == 0) {
			throw new StorePlatformException("SAC_DSP_0002", "prodIdList", prodIdList.toString());
		}
		if (prodIdList.size() > DisplayConstants.DP_CATEGORY_SPECIFIC_PRODUCT_PARAMETER_LIMIT) {
			throw new StorePlatformException("SAC_DSP_0004", "prodIdList",
					DisplayConstants.DP_CATEGORY_SPECIFIC_PRODUCT_PARAMETER_LIMIT);
		}

		// 상품 군 조회
		PaymentInfo paymentProdType = this.commonDAO.queryForObject("PaymentInfo.searchProdType", prodIdList.get(0),
				PaymentInfo.class);

		if (paymentProdType != null) {
			this.log.debug("##### searchProdType result : {}, {}", paymentProdType.getTopMenuId(),
					paymentProdType.getSvcGrpCd());

			if (DisplayConstants.DP_TSTORE_SHOPPING_PROD_SVC_GRP_CD.equals(paymentProdType.getSvcGrpCd())) { // 쇼핑 상품
				paymentInfoList = this.shoppingService.getShoppingforPayment(req);
			} else {
				for (int i = 0; i < prodIdList.size(); i++) {
					req.setProdId(prodIdList.get(i)); // prodIdList 에 있는 상품ID 1개씩 setting
					PaymentInfo paymentInfo = this.commonDAO.queryForObject("PaymentInfo.searchPaymentInfo", req,
							PaymentInfo.class);

					if (paymentInfo != null) {
						// 상품분류코드는 SVC_GRP_CD||TOP_MENU_ID 로 제공
						paymentInfo.setTenantProdGrpCd(paymentProdType.getSvcGrpCd() + paymentProdType.getTopMenuId());
						paymentInfoList.add(paymentInfo);
					}
				}
			}
		}

		res.setPaymentInfoList(paymentInfoList);
		return res;
	}
}
